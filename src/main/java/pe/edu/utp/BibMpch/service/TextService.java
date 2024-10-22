package pe.edu.utp.BibMpch.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.TextDTO;
import pe.edu.utp.BibMpch.model.Editorial;
import pe.edu.utp.BibMpch.model.TextResource;
import pe.edu.utp.BibMpch.model.TextResourceType;
import pe.edu.utp.BibMpch.repository.EditorialRepository;
import pe.edu.utp.BibMpch.repository.TextRepository;
import pe.edu.utp.BibMpch.repository.TextResourceTypeRepository;
import pe.edu.utp.BibMpch.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TextService {

	private final TextRepository textRepository;
	private final EditorialRepository editorialRepository;
	private final TextResourceTypeRepository textResourceTypeRepository;

	public List<TextResource> allTexts() {
		List<TextResource> textResources = new ArrayList<>();
		textRepository.findAll().forEach(textResources::add);
		return textResources;
	}

	public ResponseEntity<TextResource> newText(TextDTO textDTO) throws ResourceNotFoundException {

		System.out.println(textDTO);

		Optional<Editorial> editorial = editorialRepository.findByName(textDTO.getEditorialName());
		Optional<TextResourceType> type = textResourceTypeRepository.findByTypename(textDTO.getTextType());

		if (editorial.isEmpty())
			throw new ResourceNotFoundException(
					String.format("Editorial '%s' no encontrada.", textDTO.getEditorialName()));

		if (type.isEmpty())
			throw new ResourceNotFoundException(
					String.format("Tipo de texto '%s' no encontrado.", textDTO.getTextType()));

		TextResource text = TextResource.builder()
				.title(textDTO.getTitle())
				.edition(textDTO.getEdition())
				.volume(textDTO.getVolume())
				.type(type.get())
				.editorial(editorial.get())
				.pages(textDTO.getNumPages())
				.publicationDate(textDTO.getPublicationDate())
				.build();
		textRepository.save(text);
		return ResponseEntity.ok(text);
	}
}
