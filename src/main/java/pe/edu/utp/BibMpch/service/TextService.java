package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.TextDTO;
import pe.edu.utp.BibMpch.model.Editorial;
import pe.edu.utp.BibMpch.model.Text;
import pe.edu.utp.BibMpch.model.TextResourceType;
import pe.edu.utp.BibMpch.repository.AuthorRepository;
import pe.edu.utp.BibMpch.repository.EditorialRepository;
import pe.edu.utp.BibMpch.repository.TextRepository;
import pe.edu.utp.BibMpch.repository.TextResourceTypeRepository;
import pe.edu.utp.BibMpch.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TextService {
	private final AuthorRepository authorRepository;
	private final TextRepository textRepository;
	private final EditorialRepository editorialRepository;
	private final TextResourceTypeRepository textResourceTypeRepository;

	public List<Text> allTexts() {
		List<Text> textResources = new ArrayList<>();
		textRepository.findAll().forEach(textResources::add);
		return textResources;
	}

	public Optional<Text> getById(Long id) {
		return textRepository.findById(id);
	}

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
				.publicationDate(textDTO.getPublicationDate())
				.authors(textDTO.getAuthors()
						.stream()
						.map((id) -> authorRepository
									.findById(id)
									.orElseThrow(() -> new EntityNotFoundException(
											String.format("Autor con id %d no encontrado", id))))
						.collect(Collectors.toSet()))
				.build();
		textRepository.save(text);
		return ResponseEntity.ok(text);
	}

	public void delete(Text textResource) {
		textRepository.delete(textResource);
	}
}
