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

@RestController
@RequestMapping("/api/v1/texts")
@AllArgsConstructor
public class TextController {

	private final TextService textService;

	private final ImageConfiguration imageConfiguration;

	//region GET Methods
	@GetMapping("/")
	public ResponseEntity<List<Text>> getAllTexts() {
		return ResponseEntity.ok(textService.allTexts());
	}

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
	 * Retrieve all authors of the text especified by id.
	 * @param id
	 * @return
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
	 * Create a new text
	 * @param textDTO
	 * @return Status 200 if ok, otherwise code indicating the error.
	 * @throws Exception
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

	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toLowerCase();
	}
}
