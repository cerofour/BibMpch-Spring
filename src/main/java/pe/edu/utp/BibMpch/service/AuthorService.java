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

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Author}.
 *
 * <p>Proporciona métodos para obtener, crear y eliminar autores, manejando la lógica de negocio
 * y la interacción con la base de datos a través del repositorio correspondiente.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link AuthorRepository}: Repositorio para gestionar las operaciones de persistencia de autores.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #getAllAuthors()}: Obtiene todos los autores registrados en el sistema.</li>
 *   <li>{@link #getById(Long)}: Obtiene un autor por su identificador.</li>
 *   <li>{@link #create(AuthorDTO)}: Crea un nuevo autor a partir de un DTO.</li>
 *   <li>{@link #deleteById(Long)}: Elimina un autor por su identificador.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@RequiredArgsConstructor</code>: Genera un constructor para inicializar los atributos finales.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 26/10/2024
 */
@Service
@RequiredArgsConstructor
public class AuthorService {
	private final AuthorRepository authorRepository;

	/**
	 * Obtiene todos los autores registrados en el sistema.
	 *
	 * @return Una lista de entidades {@link Author}.
	 */
	public List<Author> getAllAuthors() {
		List<Author> result = new ArrayList<>();

		authorRepository.findAll().forEach(result::add);

		return result;
	}

	/**
	 * Obtiene un autor por su identificador.
	 *
	 * @param id El identificador del autor.
	 * @return La entidad {@link Author} si existe, o <code>null</code> si no se encuentra.
	 */
	public Author getById(Long id) {
		return authorRepository.findById(id)
				.orElse(null);
	}

	/**
	 * Crea un nuevo autor a partir de un DTO.
	 *
	 * @param authorDTO El DTO que contiene los datos del autor.
	 * @return La entidad {@link Author} que se creó y guardó.
	 */
	public Author create(AuthorDTO authorDTO) {
		Author author = Author.builder()
				.name(authorDTO.getName())
				.pLastName(authorDTO.getPlastname())
				.mLastName(authorDTO.getMlastname())
				.build();

		return authorRepository.save(author);
	}

	/**
	 * Elimina un autor por su identificador.
	 *
	 * @param id El identificador del autor a eliminar.
	 * @return {@link HttpStatus#OK} si se eliminó correctamente, o {@link HttpStatus#NOT_FOUND} si no se encuentra.
	 */
	public HttpStatusCode deleteById(Long id) {
		Optional<Author> author = authorRepository.findById(id);
		return author.map((a) -> {
			authorRepository.delete(a);
			return HttpStatus.OK;
		}).orElse(HttpStatus.NOT_FOUND);
	}
}
