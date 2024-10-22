package pe.edu.utp.BibMpch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.TextResourceType;
import pe.edu.utp.BibMpch.repository.TextResourceTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/text_types")
public class TextResourceTypeController {

	@Autowired
	TextResourceTypeRepository repo;

	@GetMapping(value = "/")
	public ResponseEntity<List<TextResourceType>> getTypes() {
		List<TextResourceType> types = new ArrayList<>();
		repo.findAll().forEach(types::add);
		return ResponseEntity.ok(types);
	}

	@GetMapping(value = "/{typename}")
	public ResponseEntity<TextResourceType> getType(@PathVariable("typename") String typename) {
		Optional<TextResourceType> x = repo.findByTypename(typename);

		return x.map(ResponseEntity::ok)
				.orElse(ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.build());
	}
}
