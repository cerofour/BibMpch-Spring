package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.TextDTO;
import pe.edu.utp.BibMpch.model.Author;
import pe.edu.utp.BibMpch.model.Text;
import pe.edu.utp.BibMpch.service.TextService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/texts")
@AllArgsConstructor
public class TextController {

	private final TextService textService;

	//region GET Methods
	@GetMapping("/")
	@SuppressWarnings("unused")
	public ResponseEntity<List<Text>> getAllTexts() {
		return ResponseEntity.ok(textService.allTexts());
	}

	@GetMapping("/get")
	@SuppressWarnings("unused")
	public ResponseEntity<Text> getText(@RequestParam(name = "id") Long id) {
		Optional<Text> text = textService.getById(id);
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
	@PostMapping("/")
	@SuppressWarnings("unused")
	public ResponseEntity<Text> newText(@RequestBody TextDTO textDTO) throws Exception {
		return textService.newText(textDTO);
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
}
