package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.AuthorDTO;
import pe.edu.utp.BibMpch.model.Author;
import pe.edu.utp.BibMpch.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/authors")
@AllArgsConstructor
public class AuthorController {

	private final AuthorService authorService;

	@GetMapping(value = "/")
	@SuppressWarnings("unused")
	public ResponseEntity<List<Author>> getAllAuthors() {
		return ResponseEntity.ok(authorService.getAllAuthors());
	}

	@GetMapping(value = "/get")
	@SuppressWarnings("unused")
	public ResponseEntity<Author> getAuthor(
		@RequestParam(name = "id") Long id) {

		Author author = authorService.getById(id);

		return (author != null) ? ResponseEntity.ok(author)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PostMapping(value = "/")
	@SuppressWarnings("unused")
	public ResponseEntity<Author> newAuthor(@RequestBody AuthorDTO authorDTO) {
		return ResponseEntity.ok(authorService.create(authorDTO));
	}

	@DeleteMapping(value = "/delete")
	@SuppressWarnings("unused")
	public ResponseEntity<Void> deleteAuthor(@RequestParam (name = "id") Long id) {
		return ResponseEntity.status(authorService.deleteById(id))
				.build();
	}
}
