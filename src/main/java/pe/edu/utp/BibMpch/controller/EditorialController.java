package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.model.Editorial;
import pe.edu.utp.BibMpch.repository.EditorialRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/editorial")
@AllArgsConstructor
public class EditorialController {

	private final EditorialRepository editorialRepository;

	@SuppressWarnings("unused")
	@GetMapping(value = "/")
	public ResponseEntity<List<Editorial>> getAllEditorials() {

		List<Editorial> result = new ArrayList<>();
		editorialRepository.findAll().forEach(result::add);

		return ResponseEntity.ok(result);
	}

	@SuppressWarnings("unused")
	@GetMapping(value = "/{name}")
	public ResponseEntity<Editorial> getByName(@PathVariable(name = "name") String editName) {
		Optional<Editorial> edit = editorialRepository.findByName(editName);
		return edit.map(ResponseEntity::ok)
				.orElse(ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.build());
	}

	public record EditorialDTO(String name) {}

	@SuppressWarnings("unused")
	@PostMapping(value = "/new")
	public ResponseEntity<Editorial> newEditorial(@RequestBody EditorialDTO editorialDTO) {
			return ResponseEntity.ok(
					editorialRepository.save(Editorial.builder()
						.name(editorialDTO.name())
						.build()));
	}

	@SuppressWarnings("unused")
	@DeleteMapping(value = "/delete/{name}")
	public ResponseEntity<Void> deleteEditorial(@PathVariable (name = "name") String editorialName) {
		Optional<Editorial> edi = editorialRepository.findByName(editorialName);
		if (edi.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		return ResponseEntity.ok().build();
	}
}
