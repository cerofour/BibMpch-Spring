package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.AuthorDTO;
import pe.edu.utp.BibMpch.model.Author;
import pe.edu.utp.BibMpch.service.AuthorService;

import java.util.List;

/**
 * Controlador para gestionar autores.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los autores, como listar todos los autores, obtener un
 * autor específico, crear nuevos autores y eliminar autores existentes.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/authors/</code>: Lista todos los autores.</li>
 *   <li><code>/api/v1/authors/get</code>: Obtiene un autor por su ID.</li>
 *   <li><code>/api/v1/authors/</code>: Crea un nuevo autor.</li>
 *   <li><code>/api/v1/authors/delete</code>: Elimina un autor por su ID.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los autores registrados.</li>
 *   <li>Obtener un autor específico por su ID.</li>
 *   <li>Crear nuevos autores.</li>
 *   <li>Eliminar autores existentes.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>AuthorService</code>: Servicio para la lógica de negocio relacionada con autores.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 26/10/2024
 */
@RestController
@RequestMapping(value = "/api/v1/authors")
@AllArgsConstructor
public class AuthorController {

	private final AuthorService authorService;

	/**
	 * Obtiene una lista de todos los autores.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/</code></p>
	 *
	 * @return Una lista con todos los autores registrados.
	 */
	@GetMapping(value = "/")
	@SuppressWarnings("unused")
	public ResponseEntity<List<Author>> getAllAuthors() {
		return ResponseEntity.ok(authorService.getAllAuthors());
	}

	/**
	 * Obtiene un autor por su ID.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/get</code></p>
	 *
	 * @param id ID del autor a obtener.
	 * @return El autor solicitado o un error si no se encuentra.
	 */
	@GetMapping(value = "/get")
	@SuppressWarnings("unused")
	public ResponseEntity<Author> getAuthor(
		@RequestParam(name = "id") Long id) {

		Author author = authorService.getById(id);

		return (author != null) ? ResponseEntity.ok(author)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	/**
	 * Crea un nuevo autor.
	 *
	 * <p><strong>Tipo de solicitud:</strong> POST</p>
	 * <p><strong>Ruta:</strong> <code>/</code></p>
	 *
	 * @param authorDTO Objeto con los datos del autor a crear.
	 * @return El autor creado.
	 */
	@PostMapping(value = "/")
	@SuppressWarnings("unused")
	public ResponseEntity<Author> newAuthor(@RequestBody AuthorDTO authorDTO) {
		return ResponseEntity.ok(authorService.create(authorDTO));
	}

	/**
	 * Elimina un autor por su ID.
	 *
	 * <p><strong>Tipo de solicitud:</strong> DELETE</p>
	 * <p><strong>Ruta:</strong> <code>/delete</code></p>
	 *
	 * @param id ID del autor a eliminar.
	 * @return Un estado HTTP que indica el resultado de la operación.
	 */
	@DeleteMapping(value = "/delete")
	@SuppressWarnings("unused")
	public ResponseEntity<Void> deleteAuthor(@RequestParam (name = "id") Long id) {
		return ResponseEntity.status(authorService.deleteById(id))
				.build();
	}
}
