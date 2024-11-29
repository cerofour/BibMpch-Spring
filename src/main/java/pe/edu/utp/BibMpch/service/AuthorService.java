package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.AuthorDTO;
import pe.edu.utp.BibMpch.model.Author;
import pe.edu.utp.BibMpch.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
	private final AuthorRepository authorRepository;

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

		return authorRepository.save(author);
	}

	public HttpStatusCode deleteById(Long id) {
		Optional<Author> author = authorRepository.findById(id);
		return author.map((a) -> {
			authorRepository.delete(a);
			return HttpStatus.OK;
		}).orElse(HttpStatus.NOT_FOUND);
	}
}
