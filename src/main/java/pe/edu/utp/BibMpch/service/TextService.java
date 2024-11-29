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

	public Optional<Text> getById(Long id) {
		Optional<Text> textById = textRepository.findById(id);
        textById.ifPresent(text -> text.setStock(getStockText(text.getBaseCode())));
		return textById;
	}

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

	private Short getStockText(String baseCode) {
		return (short) codeTextualResourceRepository.findByBaseCode(baseCode).size();
	}

	public void delete(Text textResource) {
		textRepository.delete(textResource);
	}

}
