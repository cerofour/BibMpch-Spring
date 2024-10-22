package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.TextDTO;
import pe.edu.utp.BibMpch.model.TextResource;
import pe.edu.utp.BibMpch.service.TextService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/texts")
@AllArgsConstructor
public class TextController {

	private final TextService textService;

	@GetMapping("/")
	@SuppressWarnings("unused")
	public ResponseEntity<List<TextResource>> allTexts() {
		return ResponseEntity.ok(textService.allTexts());
	}

	@PostMapping("/new")
	@SuppressWarnings("unused")
	public ResponseEntity<TextResource> newText(@RequestBody TextDTO textDTO) throws Exception{
		return textService.newText(textDTO);
	}
}
