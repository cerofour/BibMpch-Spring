package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.utp.BibMpch.DTO.LoanDTO;
import pe.edu.utp.BibMpch.DTO.TextDTO;
import pe.edu.utp.BibMpch.configuration.ImageConfiguration;
import pe.edu.utp.BibMpch.exceptions.ResourceNotFoundException;
import pe.edu.utp.BibMpch.model.Author;
import pe.edu.utp.BibMpch.model.Loan;
import pe.edu.utp.BibMpch.model.Text;
import pe.edu.utp.BibMpch.service.TextService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controlador para gestionar textos.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los textos, como listar todos los textos, obtener detalles
 * de un texto, crear nuevos textos con imágenes, actualizar y eliminar textos.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/texts/</code>: Lista todos los textos.</li>
 *   <li><code>/api/v1/texts/get</code>: Obtiene un texto por su ID.</li>
 *   <li><code>/api/v1/texts/getAuthors</code>: Obtiene los autores de un texto por ID.</li>
 *   <li><code>/api/v1/texts/</code>: Crea un nuevo texto con imagen.</li>
 *   <li><code>/api/v1/texts/{id}</code>: Actualiza un texto existente con o sin imagen.</li>
 *   <li><code>/api/v1/texts/delete</code>: Elimina un texto por su ID.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los textos registrados.</li>
 *   <li>Obtener detalles de un texto específico por su ID.</li>
 *   <li>Obtener los autores asociados a un texto.</li>
 *   <li>Crear nuevos textos junto con sus imágenes asociadas.</li>
 *   <li>Actualizar textos existentes con posibilidad de cambiar la imagen.</li>
 *   <li>Eliminar textos del sistema.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>TextService</code>: Servicio para la lógica de negocio relacionada con textos.</li>
 *   <li><code>ImageConfiguration</code>: Configuración para la gestión de imágenes asociadas a textos.</li>
 * </ul>
 *
 * @author Llacsahuanga, Huanca
 * @version 1.0
 * @since 22/10/2024
 */
@RestController
@RequestMapping("/api/v1/texts")
@AllArgsConstructor
public class TextController {

	private final TextService textService;

	private final ImageConfiguration imageConfiguration;

	//region GET Methods
	/**
	 * Obtiene una lista de todos los textos.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/</code></p>
	 *
	 * @return Una lista con todos los textos registrados.
	 */
	@GetMapping("/")
	public ResponseEntity<List<Text>> getAllTexts() {
		return ResponseEntity.ok(textService.allTexts());
	}

	/**
	 * Obtiene un texto por su ID.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/get</code></p>
	 *
	 * @param id ID del texto a obtener.
	 * @return El texto solicitado o un error si no se encuentra.
	 */
	@SuppressWarnings("OptionalGetWithoutIsPresent")
    @GetMapping("/get")
	public ResponseEntity<Text> getText(@RequestParam(name = "id") Long id) {
		Optional<Text> text = textService.getById(id);

		text.get().setImageUrl(
				"%s/%d".formatted(imageConfiguration.getEndpointText(), id));

		return text.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}

	/**
	 * Obtiene todos los autores asociados a un texto.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/getAuthors</code></p>
	 *
	 * @param id ID del texto cuyos autores se desean obtener.
	 * @return Una lista con los autores del texto o un error si no se encuentra el texto.
	 */
	@GetMapping("/getAuthors")
	@SuppressWarnings("unused")
	public ResponseEntity<List<Author>> getAuthors(@RequestParam(name = "id") Long id) {
		Optional<Text> text = textService.getById(id);
		return text.map((t) -> ResponseEntity.ok(List.copyOf(t.getAuthors())))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	//endregion

	// region CREATE methods (POST)
	/**
	 * Crea un nuevo texto junto con su imagen.
	 *
	 * <p><strong>Tipo de solicitud:</strong> POST</p>
	 * <p><strong>Ruta:</strong> <code>/</code></p>
	 *
	 * @param textDTO Datos del texto a crear.
	 * @param imageFile Archivo de imagen asociado al texto.
	 * @return El texto creado o un error si ocurre algún problema.
	 * @throws Exception Si ocurre un error durante la creación del texto o la carga de la imagen.
	 */
	@PostMapping(value = "/", consumes = "multipart/form-data")
	@SuppressWarnings("unused")
	public ResponseEntity<Text> newText(
			@RequestPart("text") TextDTO textDTO,
			@RequestPart("image") MultipartFile imageFile) throws Exception {

		Path directoryPath = Paths.get(imageConfiguration.getUploadDirText());

		String fileExtension = getFileExtension(
				Objects.requireNonNull(imageFile.getOriginalFilename())
		);

		ResponseEntity<Text> response = textService.newText(textDTO);


		if(!fileExtension.equals(imageConfiguration.getAllowedExtension()))
			return ResponseEntity.internalServerError().body(null);

		if (!Files.exists(directoryPath))
			Files.createDirectories(directoryPath);

		String fileName = "%d.%s".formatted(Objects.requireNonNull(
                response.getBody()).getId(), imageConfiguration.getAllowedExtension());

		Path filePath = directoryPath.resolve(fileName);

		Files.write(filePath, imageFile.getBytes());

		response.getBody().setImageUrl(
				"%s/%d".formatted(imageConfiguration.getEndpointText(), response.getBody().getId()));

		return response;
	}
	// endregion

	// region DELETE methods
	/**
	 * Elimina un texto por su ID.
	 *
	 * <p><strong>Tipo de solicitud:</strong> DELETE</p>
	 * <p><strong>Ruta:</strong> <code>/delete</code></p>
	 *
	 * @param id ID del texto a eliminar.
	 * @return Un estado HTTP que indica el resultado de la operación.
	 */
	@DeleteMapping("/delete")
	@SuppressWarnings("unused")
	public ResponseEntity<Void> delete(@RequestParam(name = "id") Long id ) {
		Optional<Text> text = textService.getById(id);

		if (text.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.build();

		textService.delete(text.get());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	// endregion

	/**
	 * Actualiza un texto existente, incluyendo su imagen opcionalmente.
	 *
	 * <p><strong>Tipo de solicitud:</strong> PUT</p>
	 * <p><strong>Ruta:</strong> <code>/{id}</code></p>
	 *
	 * @param id ID del texto a actualizar.
	 * @param textDTO Datos actualizados del texto.
	 * @param imageFile Archivo de imagen opcional para actualizar el texto.
	 * @return El texto actualizado o un error si no se encuentra.
	 * @throws Exception Si ocurre un error durante la actualización del texto o la imagen.
	 */
	@PutMapping(value = "/{id}", consumes = "multipart/form-data")
	@SuppressWarnings("unused")
	public ResponseEntity<Text> updateText(
			@PathVariable("id") Long id,
			@RequestPart("text") TextDTO textDTO,
			@RequestPart(value = "image", required = false) MultipartFile imageFile) throws Exception {

		Optional<Text> existingText = textService.getById(id);

		if (existingText.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		ResponseEntity<Text> updatedText = textService.updateText(id, textDTO);

		Objects.requireNonNull(updatedText.getBody()).setImageUrl(
				"%s/%d".formatted(imageConfiguration.getEndpointText(), updatedText.getBody().getId())); //check this

		if (imageFile == null)
			return updatedText;

		String fileExtension = getFileExtension(
				Objects.requireNonNull(imageFile.getOriginalFilename())
		);

		if (!fileExtension.equals(imageConfiguration.getAllowedExtension())) {
			return ResponseEntity.internalServerError().body(null);
		}

		Path directoryPath = Paths.get(imageConfiguration.getUploadDirText());
		if (!Files.exists(directoryPath)) {
			Files.createDirectories(directoryPath);
		}

		String fileName = "%d.%s".formatted(existingText.get().getId(), imageConfiguration.getAllowedExtension());
		Path filePath = directoryPath.resolve(fileName);

		Files.write(filePath, imageFile.getBytes());

		return updatedText;
	}

	/**
	 * Obtiene la extensión de un archivo dado.
	 *
	 * Este método privado toma el nombre de un archivo, encuentra su extensión y
	 * la devuelve en minúsculas. Si no hay extensión, devuelve una cadena vacía.
	 *
	 * @param fileName Nombre del archivo del cual se desea obtener la extensión.
	 * @return La extensión del archivo en minúsculas o una cadena vacía si no tiene extensión.
	 */
	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toLowerCase();
	}
}
