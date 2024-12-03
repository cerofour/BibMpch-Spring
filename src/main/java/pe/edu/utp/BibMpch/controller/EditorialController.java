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

/**
 * Controlador para gestionar editoriales.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con las editoriales, incluyendo la obtención, creación
 * y eliminación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/editorial/</code>: Lista todas las editoriales.</li>
 *   <li><code>/api/v1/editorial/{name}</code>: Obtiene una editorial por su nombre.</li>
 *   <li><code>/api/v1/editorial/new</code>: Crea una nueva editorial.</li>
 *   <li><code>/api/v1/editorial/delete/{name}</code>: Elimina una editorial por su nombre.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todas las editoriales registradas.</li>
 *   <li>Obtener una editorial específica por su nombre.</li>
 *   <li>Crear nuevas editoriales.</li>
 *   <li>Eliminar editoriales existentes por nombre.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>EditorialRepository</code>: Repositorio para la interacción con la base de datos.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 22/10/2024
 */
@RestController
@RequestMapping("/api/v1/editorial")
@AllArgsConstructor
public class EditorialController {

	private final EditorialRepository editorialRepository;

	/**
	 * Obtiene una lista de todas las editoriales.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/</code></p>
	 *
	 * @return Una lista con todas las editoriales registradas.
	 */
	@SuppressWarnings("unused")
	@GetMapping(value = "/")
	public ResponseEntity<List<Editorial>> getAllEditorials() {

		List<Editorial> result = new ArrayList<>();
		editorialRepository.findAll().forEach(result::add);

		return ResponseEntity.ok(result);
	}

	/**
	 * Obtiene una editorial por su nombre.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/{name}</code></p>
	 *
	 * @param editName Nombre de la editorial a obtener.
	 * @return La editorial solicitada o un error si no se encuentra.
	 */
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

	/**
	 * Crea una nueva editorial.
	 *
	 * <p><strong>Tipo de solicitud:</strong> POST</p>
	 * <p><strong>Ruta:</strong> <code>/new</code></p>
	 *
	 * @param editorialDTO Objeto con los datos de la editorial a crear.
	 * @return La editorial creada.
	 */
	@SuppressWarnings("unused")
	@PostMapping(value = "/new")
	public ResponseEntity<Editorial> newEditorial(@RequestBody EditorialDTO editorialDTO) {
			return ResponseEntity.ok(
					editorialRepository.save(Editorial.builder()
						.name(editorialDTO.name())
						.build()));
	}

	/**
	 * Elimina una editorial por su nombre.
	 *
	 * <p><strong>Tipo de solicitud:</strong> DELETE</p>
	 * <p><strong>Ruta:</strong> <code>/delete/{name}</code></p>
	 *
	 * @param editorialName Nombre de la editorial a eliminar.
	 * @return Una respuesta vacía si la operación es exitosa, o un error si no se encuentra.
	 */
	@SuppressWarnings("unused")
	@DeleteMapping(value = "/delete/{name}")
	public ResponseEntity<Void> deleteEditorial(@PathVariable (name = "name") String editorialName) {
		Optional<Editorial> edi = editorialRepository.findByName(editorialName);
		if (edi.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		return ResponseEntity.ok().build();
	}
}
