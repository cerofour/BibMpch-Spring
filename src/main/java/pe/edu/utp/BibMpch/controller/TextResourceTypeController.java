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

/**
 * Controlador para gestionar tipos de recursos textuales.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los tipos de recursos textuales, incluyendo la
 * obtención de todos los tipos y la búsqueda por nombre.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/text_types/</code>: Lista todos los tipos de recursos textuales.</li>
 *   <li><code>/api/v1/text_types/{typename}</code>: Obtiene un tipo de recurso textual por su nombre.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los tipos de recursos textuales registrados.</li>
 *   <li>Obtener un tipo de recurso textual específico por su nombre.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>TextResourceTypeRepository</code>: Repositorio para interactuar con la base de datos.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 22/10/2024
 */
@RestController
@RequestMapping(value = "/api/v1/text_types")
public class TextResourceTypeController {

	@Autowired
	TextResourceTypeRepository repo;

	/**
	 * Obtiene una lista de todos los tipos de recursos textuales.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/</code></p>
	 *
	 * @return Una lista con todos los tipos de recursos textuales registrados.
	 */
	@GetMapping(value = "/")
	public ResponseEntity<List<TextResourceType>> getTypes() {
		List<TextResourceType> types = new ArrayList<>();
		repo.findAll().forEach(types::add);
		return ResponseEntity.ok(types);
	}

	/**
	 * Obtiene un tipo de recurso textual por su nombre.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/{typename}</code></p>
	 *
	 * @param typename Nombre del tipo de recurso textual a obtener.
	 * @return El tipo de recurso textual solicitado o un error si no se encuentra.
	 */
	@GetMapping(value = "/{typename}")
	public ResponseEntity<TextResourceType> getType(@PathVariable("typename") String typename) {
		Optional<TextResourceType> x = repo.findByTypename(typename);

		return x.map(ResponseEntity::ok)
				.orElse(ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.build());
	}
}
