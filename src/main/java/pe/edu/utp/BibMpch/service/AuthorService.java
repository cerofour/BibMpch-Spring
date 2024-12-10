package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.AuthorDTO;
import pe.edu.utp.BibMpch.exceptions.ResourceNotFoundException;
import pe.edu.utp.BibMpch.model.Author;
import pe.edu.utp.BibMpch.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
	private final AuthorRepository authorRepository;
	private final RegisterActionsService registerActionsService;

	public List<Author> getAllAuthors() {
		List<Author> result = new ArrayList<>();

		authorRepository.findAll().forEach(result::add);

		return result;
	}

	public Author getById(Long id) {
		return authorRepository.findById(id)
				.orElse(null);
	}

	public Author create(AuthorDTO authorDTO) {
		Author author = Author.builder()
				.name(authorDTO.getName())
				.pLastName(authorDTO.getPlastname())
				.mLastName(authorDTO.getMlastname())
				.build();

		Author saveAuthor =  authorRepository.save(author);

		registerActionsService.newRegisterAction(
                "Creó un nuevo autor - ID: %d".formatted(saveAuthor.getId())
		);

		return saveAuthor;
	}

	public Author update(Long id, AuthorDTO authorDTO) throws ResourceNotFoundException {

		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

		author.setName(authorDTO.getName());
		author.setPLastName(authorDTO.getPlastname());
		author.setMLastName(authorDTO.getMlastname());

		Author saveAuthor = authorRepository.save(author);

		registerActionsService.newRegisterAction(
				"Actualizó un autor - ID: %d".formatted(saveAuthor.getId())
		);

		return saveAuthor;
	}

	public HttpStatusCode deleteById(Long id) {
		Optional<Author> author = authorRepository.findById(id);
		return author.map((a) -> {
			authorRepository.delete(a);
			return HttpStatus.OK;
		}).orElse(HttpStatus.NOT_FOUND);
	}
}
