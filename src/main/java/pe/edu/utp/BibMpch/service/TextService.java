package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.BibMpch.DTO.TextDTO;
import pe.edu.utp.BibMpch.configuration.ImageConfiguration;
import pe.edu.utp.BibMpch.model.CodeTextualResource;
import pe.edu.utp.BibMpch.model.Editorial;
import pe.edu.utp.BibMpch.model.Text;
import pe.edu.utp.BibMpch.model.TextResourceType;
import pe.edu.utp.BibMpch.repository.*;
import pe.edu.utp.BibMpch.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Text}.
 *
 * <p>Proporciona métodos para manejar textos registrados en el sistema, incluyendo creación,
 * actualización, eliminación y manejo de inventarios asociados.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link AuthorRepository}: Repositorio para gestionar los autores asociados a los textos.</li>
 *   <li>{@link TextRepository}: Repositorio para gestionar las operaciones de persistencia de textos.</li>
 *   <li>{@link EditorialRepository}: Repositorio para gestionar las editoriales asociadas.</li>
 *   <li>{@link TextResourceTypeRepository}: Repositorio para manejar los tipos de recursos textuales.</li>
 *   <li>{@link CodeTextualResourceRepository}: Repositorio para manejar los recursos textuales específicos.</li>
 *   <li>{@link LoanRepository}: Repositorio para gestionar préstamos asociados a los recursos textuales.</li>
 *   <li>{@link ImageConfiguration}: Configuración para manejar URLs de imágenes asociadas a los textos.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #allTexts()}: Recupera todos los textos registrados en el sistema.</li>
 *   <li>{@link #getById(Long)}: Obtiene un texto específico por su identificador.</li>
 *   <li>{@link #newText(TextDTO)}: Crea un nuevo texto basado en un DTO.</li>
 *   <li>{@link #updateText(Long, TextDTO)}: Actualiza un texto existente basado en un DTO.</li>
 *   <li>{@link #delete(Text)}: Elimina un texto específico del sistema.</li>
 *   <li>{@link #handleStockReduction(List, int)}: Maneja la reducción de stock.</li>
 *   <li>{@link #handleStockIncrease(Text, List, int)}: Maneja el aumento de stock.</li>
 *   <li>{@link #getStockText(String)}: Calcula el stock actual de un texto.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor para inicializar las dependencias finales.</li>
 * </ul>
 *
 * @author Huanca, Llacsahuanga
 * @version 1.0
 * @since 22/10/2024
 */
@Service
@AllArgsConstructor
public class TextService {
	private static final Logger log = LoggerFactory.getLogger(TextService.class);
	private final AuthorRepository authorRepository;
	private final TextRepository textRepository;
	private final EditorialRepository editorialRepository;
	private final TextResourceTypeRepository textResourceTypeRepository;
	private final CodeTextualResourceRepository codeTextualResourceRepository;
	private final LoanRepository loanRepository;

	private final ImageConfiguration imageConfiguration;

	/**
	 * Recupera todos los textos registrados en el sistema.
	 *
	 * @return Una lista de entidades {@link Text} con su información actualizada.
	 */
	public List<Text> allTexts() {
		List<Text> textResources = new ArrayList<>();
		textRepository.findAll().forEach(
				element -> {
					element.setImageUrl(
							"%s/%d".formatted(imageConfiguration.getEndpointText(), element.getId()));
					element.setStock(getStockText(element.getBaseCode()));
					textResources.add(element);
				});
		return textResources;
	}

	/**
	 * Obtiene un texto específico por su identificador.
	 *
	 * @param id El identificador del texto.
	 * @return Un objeto {@link Optional<Text>} con la información del texto, si existe.
	 */
	public Optional<Text> getById(Long id) {
		Optional<Text> textById = textRepository.findById(id);
        textById.ifPresent(text -> text.setStock(getStockText(text.getBaseCode())));
		return textById;
	}

	/**
	 * Crea un nuevo texto basado en un DTO.
	 *
	 * @param textDTO El DTO que contiene la información del texto a crear.
	 * @return Una entidad {@link ResponseEntity<Text>} que representa el texto creado.
	 * @throws ResourceNotFoundException Si no se encuentran la editorial, tipo de texto o autores especificados.
	 */
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Text> newText(TextDTO textDTO) throws ResourceNotFoundException {

		System.out.println(textDTO);

		Optional<Editorial> editorial = editorialRepository.findById(textDTO.getEditorialId());
		Optional<TextResourceType> type = textResourceTypeRepository.findById(textDTO.getTypeId());

		if (editorial.isEmpty())
			throw new ResourceNotFoundException(
					String.format("Editorial con id '%d' no encontrada.", textDTO.getEditorialId()));

		if (type.isEmpty())
			throw new ResourceNotFoundException(
					String.format("Tipo de texto con id '%d' no encontrado.", textDTO.getTypeId()));

		Text text = Text.builder()
				.title(textDTO.getTitle())
				.edition(textDTO.getEdition())
				.volume(textDTO.getVolume())
				.type(type.get())
				.editorial(editorial.get())
				.pages(textDTO.getNumPages())
				.baseCode(textDTO.getBaseCode())
				.available(textDTO.getAvailable())
				.publicationDate(textDTO.getPublicationDate())
				.stock((textDTO.getStock() <= 0) ? 1 : textDTO.getStock())
				.authors(textDTO.getAuthors()
						.stream()
						.map((id) -> authorRepository
									.findById(id)
									.orElseThrow(() -> new EntityNotFoundException(
											String.format("Autor con id %d no encontrado", id))))
						.collect(Collectors.toSet()))
				.build();
		textRepository.save(text);

		try{
			for (int i = 1; i <= textDTO.getStock(); i++){
				int finalI = i;
				codeTextualResourceRepository
						.findByBaseCodeAndExemplaryCode(textDTO.getBaseCode(), i)
						.ifPresentOrElse(
								_ -> {},
								() -> {
							codeTextualResourceRepository.save(CodeTextualResource.builder()
									.baseCode(textDTO.getBaseCode())
									.exemplaryCode(finalI)
									.available(true)
									.build());
						});
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to process resources", e);
		}

		return ResponseEntity.ok(text);
	}

	/**
	 * Actualiza un texto existente basado en un DTO.
	 *
	 * @param id      El identificador del texto a actualizar.
	 * @param textDTO El DTO que contiene la nueva información del texto.
	 * @return Una entidad {@link ResponseEntity<Text>} que representa el texto actualizado.
	 * @throws ResourceNotFoundException Si no se encuentran los recursos asociados al texto.
	 */
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Text> updateText(Long id, TextDTO textDTO) throws ResourceNotFoundException {

		Text existingText = textRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Texto no encontrado"));

		Optional<Editorial> editorial = editorialRepository.findById(textDTO.getEditorialId());
		Optional<TextResourceType> type = textResourceTypeRepository.findById(textDTO.getTypeId());

		if (editorial.isEmpty())
			throw new ResourceNotFoundException(
					String.format("Editorial con id '%d' no encontrada.", textDTO.getEditorialId()));

		if (type.isEmpty())
			throw new ResourceNotFoundException(
					String.format("Tipo de texto con id '%d' no encontrado.", textDTO.getTypeId()));

		if(textDTO.getStock() <= 0)
			throw new IllegalArgumentException("El stock no puede ser 0");

		existingText.setTitle(textDTO.getTitle());
		existingText.setEdition(textDTO.getEdition());
		existingText.setVolume(textDTO.getVolume());
		existingText.setType(type.get());
		existingText.setEditorial(editorial.get());
		existingText.setPages(textDTO.getNumPages());
		existingText.setAvailable(textDTO.getAvailable());
		existingText.setPublicationDate(textDTO.getPublicationDate());

		existingText.setAuthors(textDTO.getAuthors()
				.stream()
				.map((authorId) -> authorRepository
						.findById(authorId)
						.orElseThrow(() -> new EntityNotFoundException(
								String.format("Autor con id %d no encontrado", authorId))))
				.collect(Collectors.toSet()));

		if (textDTO.getStock() != getStockText(existingText.getBaseCode())) {
			List<CodeTextualResource> existingResources = codeTextualResourceRepository
					.findByBaseCode(existingText.getBaseCode());

			if (textDTO.getStock() < existingResources.size()) {
				handleStockReduction(existingResources, textDTO.getStock());
			} else {
				handleStockIncrease(existingText, existingResources, textDTO.getStock());
			}

			existingText.setStock(getStockText(textDTO.getBaseCode()));
		}

		textRepository.save(existingText);

		return ResponseEntity.ok(existingText);
	}

	/*
	private List<Long> findDeletableResourceIds(List<CodeTextualResource> resources) {
		return resources.stream()
				.filter(resource -> loanRepository.countByCodeTextualResource(resource) == 0)
				.map(CodeTextualResource::getId)
				.collect(Collectors.toList());
	}
	 */

	/**
	 * Maneja la reducción de stock para los recursos textuales existentes.
	 *
	 * <p>Elimina recursos no prestados y disponibles si el nuevo stock es menor al actual.
	 * En caso de conflictos, prioriza recursos prestados.</p>
	 *
	 * @param existingResources Lista de recursos textuales existentes asociados al texto.
	 * @param newStock          El nuevo valor de stock requerido.
	 */
	private void handleStockReduction(List<CodeTextualResource> existingResources, int newStock) {
		AtomicBoolean borrowedAndAvailableResource = new AtomicBoolean(false);

		List<Long> resourceIdsToDelete = existingResources
				.stream()
				.filter(resource -> {

					if(loanRepository.countByCodeTextualResource(resource) == 0)
						return true;
					else if (resource.getAvailable())
						borrowedAndAvailableResource.set(true);

					return false;

				})
				.map(CodeTextualResource::getId)
				.collect(Collectors.toList());

		if(!borrowedAndAvailableResource.get() && !resourceIdsToDelete.isEmpty())
			resourceIdsToDelete.removeFirst();

		int loanedResourceCount = existingResources.size() - resourceIdsToDelete.size();

		if (loanedResourceCount <= newStock) {
			int deletionCount = Math.min(resourceIdsToDelete.size(),
					resourceIdsToDelete.size() - (newStock - loanedResourceCount));
			if (deletionCount > 0) {
				codeTextualResourceRepository.deleteAllById(
						resourceIdsToDelete.subList(
								resourceIdsToDelete.size() - deletionCount,
								resourceIdsToDelete.size()
						)
				);
			}
		}
	}

	/**
	 * Maneja el aumento de stock para los recursos textuales existentes.
	 *
	 * <p>Crea nuevos recursos hasta alcanzar el stock requerido, evitando duplicados.</p>
	 *
	 * @param existingText      El texto asociado a los recursos.
	 * @param existingResources Lista de recursos textuales existentes.
	 * @param newStock          El nuevo valor de stock requerido.
	 */
	private void handleStockIncrease(Text existingText,
									 List<CodeTextualResource> existingResources,
									 int newStock) {
		int resourcesNeeded = newStock - existingResources.size();

		for (int exemplaryCode = 1; resourcesNeeded > 0; exemplaryCode++) {
			Optional<CodeTextualResource> existingResource = codeTextualResourceRepository
					.findByBaseCodeAndExemplaryCode(existingText.getBaseCode(), exemplaryCode);

			if (existingResource.isEmpty()) {
				codeTextualResourceRepository.save(
						CodeTextualResource.builder()
								.baseCode(existingText.getBaseCode())
								.exemplaryCode(exemplaryCode)
								.available(true)
								.build()
				);
				resourcesNeeded--;
			}
		}
	}

	/**
	 * Calcula el stock actual de un texto basado en su código base.
	 *
	 * @param baseCode El código base del texto.
	 * @return El stock actual como un valor {@link Short}.
	 */
	private Short getStockText(String baseCode) {
		return (short) codeTextualResourceRepository.findByBaseCode(baseCode).size();
	}

	/**
	 * Elimina un texto del sistema.
	 *
	 * <p>Realiza una operación de eliminación permanente en el repositorio de textos.</p>
	 *
	 * @param textResource El texto a eliminar.
	 */
	public void delete(Text textResource) {
		textRepository.delete(textResource);
	}

}
