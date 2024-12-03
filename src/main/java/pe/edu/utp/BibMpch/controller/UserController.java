package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.LoginUserDTO;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.service.UserService;

import java.util.List;

/**
 * Controlador para gestionar usuarios.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los usuarios, como listar usuarios, obtener el usuario
 * actual, actualizar información y eliminar usuarios.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/users/</code>: Lista todos los usuarios.</li>
 *   <li><code>/api/v1/users/me</code>: Obtiene el usuario autenticado actual.</li>
 *   <li><code>/api/v1/users/get</code>: Obtiene un usuario por ID o documento.</li>
 *   <li><code>/api/v1/users/update</code>: Actualiza información de un usuario.</li>
 *   <li><code>/api/v1/users/update_psk</code>: Actualiza la contraseña de un usuario.</li>
 *   <li><code>/api/v1/users/delete</code>: Elimina un usuario por ID o documento.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los usuarios registrados.</li>
 *   <li>Obtener detalles de usuarios por ID o documento.</li>
 *   <li>Actualizar datos y contraseñas de usuarios.</li>
 *   <li>Eliminar usuarios registrados.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>UserService</code>: Servicio para la lógica de negocio relacionada con los usuarios.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/users")
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	/**
	 * Obtiene una lista de todos los usuarios.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/</code></p>
	 *
	 * @return Una lista con todos los usuarios registrados.
	 */
	@GetMapping(value = "/")
	@SuppressWarnings("unused")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.allUsers());
	}

	/**
	 * Obtiene el usuario autenticado actual.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/me</code></p>
	 *
	 * @return Los detalles del usuario autenticado actual.
	 */
	@SuppressWarnings("unused")
	@GetMapping(value = "/me")
	public ResponseEntity<User> getCurrentUser() {
		User thisUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(thisUser);
	}

	/**
	 * Obtiene un usuario por ID o documento.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/get</code></p>
	 *
	 * @param document Documento opcional del usuario.
	 * @param id ID opcional del usuario.
	 * @return Los detalles del usuario solicitado o un error si no se encuentra.
	 */
	@SuppressWarnings("unused")
	@GetMapping(value = "/get")
	public ResponseEntity<User> getUser(
			@RequestParam(name = "document", required = false) Optional<String> document,
			@RequestParam(name = "id", required = false) Optional<Long> id) {

		if (document.isEmpty() && id.isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(null);

		// try to index by id first, then by document
		User u = id.map(userService::getUser)
				.orElse(document.map(userService::getUser)
						.orElse(null));

		return (u == null) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
				: ResponseEntity.ok(u);
	}

	/**
	 * Actualiza la información de un usuario.
	 *
	 * <p><strong>Tipo de solicitud:</strong> POST</p>
	 * <p><strong>Ruta:</strong> <code>/update</code></p>
	 *
	 * @param document Documento opcional del usuario.
	 * @param id ID opcional del usuario.
	 * @param userDTO Objeto con los datos actualizados del usuario.
	 * @return Un estado HTTP que indica el resultado de la operación.
	 */
	@SuppressWarnings("unused")
	@PostMapping(value = "/update")
	public ResponseEntity<Void> update(
			@RequestParam(name = "document", required = false) Optional<String> document,
			@RequestParam(name = "id", required = false) Optional<Long> id,
			@RequestBody UserDTO userDTO
			) {

		// try to index by id first, then by document
		HttpStatusCode status = id.map((i) -> userService.updateUser(i, userDTO))
				.orElse(document.map((doc) -> userService.updateUser(doc, userDTO))
						.orElse(HttpStatus.BAD_REQUEST));

		return ResponseEntity.status(status).build();
	}

	/**
	 * Actualiza la contraseña de un usuario.
	 *
	 * <p><strong>Tipo de solicitud:</strong> POST</p>
	 * <p><strong>Ruta:</strong> <code>/update_psk</code></p>
	 *
	 * @param newPsk Objeto con el documento del usuario y la nueva contraseña.
	 * @return Un estado HTTP que indica el resultado de la operación.
	 */
	@SuppressWarnings("unused")
	@PostMapping(value = "/update_psk")
	public ResponseEntity<Void> updatePsk(@RequestBody LoginUserDTO newPsk) {
		return ResponseEntity.status(userService.updatePsk(newPsk.getDocument(), newPsk.getPsk()))
				.build();
	}

	/**
	 * Elimina un usuario por ID o documento.
	 *
	 * <p><strong>Tipo de solicitud:</strong> DELETE</p>
	 * <p><strong>Ruta:</strong> <code>/delete</code></p>
	 *
	 * @param document Documento opcional del usuario.
	 * @param id ID opcional del usuario.
	 * @return Un estado HTTP que indica el resultado de la operación.
	 */
	@DeleteMapping(value = "/delete")
	@SuppressWarnings("unused")
	public ResponseEntity<Void> deleteUser(
			@RequestParam(name = "document", required = false) Optional<String> document,
			@RequestParam(name = "id", required = false) Optional<Long> id) {

		// try to index by id first, then by document
		HttpStatusCode status = id.map(userService::deleteUser)
				.orElse(document.map(userService::deleteUser)
						.orElse(HttpStatus.BAD_REQUEST));

		return ResponseEntity.status(status).build();
	}
}
