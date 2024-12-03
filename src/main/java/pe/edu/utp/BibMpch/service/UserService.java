package pe.edu.utp.BibMpch.service;

import lombok.AllArgsConstructor;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.repository.UserRepository;

import pe.edu.utp.BibMpch.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link User}.
 *
 * <p>Proporciona métodos para manejar usuarios registrados en el sistema, incluyendo creación,
 * actualización, eliminación y recuperación de usuarios, así como la actualización de contraseñas.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link UserRepository}: Repositorio para gestionar las operaciones de persistencia de usuarios.</li>
 *   <li>{@link PasswordEncoder}: Proveedor de codificación para manejar contraseñas de forma segura.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #allUsers()}: Recupera todos los usuarios registrados en el sistema.</li>
 *   <li>{@link #getUser(Long)}: Obtiene un usuario específico por su identificador.</li>
 *   <li>{@link #getUser(String)}: Obtiene un usuario específico por su documento.</li>
 *   <li>{@link #deleteUser(Long)}: Elimina un usuario por su identificador.</li>
 *   <li>{@link #deleteUser(String)}: Elimina un usuario por su documento.</li>
 *   <li>{@link #updateUser(Long, UserDTO)}: Actualiza un usuario existente por su identificador.</li>
 *   <li>{@link #updateUser(String, UserDTO)}: Actualiza un usuario existente por su documento.</li>
 *   <li>{@link #updatePsk(String, String)}: Actualiza la contraseña de un usuario.</li>
 *   <li>{@link #findAndMap(Optional, Function)}: Encuentra un usuario y aplica una función si existe.</li>
 *   <li>{@link #delete(User)}: Elimina un usuario del repositorio.</li>
 *   <li>{@link #update(User, UserDTO)}: Actualiza un usuario con los datos proporcionados.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor para inicializar los atributos finales.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 22/10/2024
 */
@AllArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * Recupera todos los usuarios registrados en el sistema.
	 *
	 * @return Una lista de entidades {@link User}.
	 */
	public List<User> allUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	/**
	 * Elimina un usuario del repositorio.
	 *
	 * @param u La entidad {@link User} a eliminar.
	 * @return {@link HttpStatus#OK} si el usuario fue eliminado correctamente.
	 */
	private HttpStatusCode delete(User u) {
		userRepository.delete(u);
		return HttpStatus.OK;
	}

	/**
	 * Elimina un usuario por su documento.
	 *
	 * @param document El documento del usuario a eliminar.
	 * @return {@link HttpStatus#OK} si el usuario fue eliminado; {@link HttpStatus#NOT_FOUND} si no se encontró.
	 */
	public HttpStatusCode deleteUser(String document) {
		return findAndMap(userRepository.findByDocument(document), this::delete);
	}

	/**
	 * Elimina un usuario por su identificador.
	 *
	 * @param id El identificador del usuario a eliminar.
	 * @return {@link HttpStatus#OK} si el usuario fue eliminado; {@link HttpStatus#NOT_FOUND} si no se encontró.
	 */
	public HttpStatusCode deleteUser(Long id) {
		return findAndMap(userRepository.findById(id), this::delete);
	}

	/**
	 * Actualiza un usuario por su documento.
	 *
	 * @param document El documento del usuario a actualizar.
	 * @param userDTO  Los nuevos datos del usuario.
	 * @return {@link HttpStatus#OK} si el usuario fue actualizado; {@link HttpStatus#NOT_FOUND} si no se encontró.
	 */
	public HttpStatusCode updateUser(String document, UserDTO userDTO) {
		return findAndMap(userRepository.findByDocument(document),
				(user) -> update(user, userDTO));
	}

	/**
	 * Actualiza un usuario por su identificador.
	 *
	 * @param id      El identificador del usuario a actualizar.
	 * @param userDTO Los nuevos datos del usuario.
	 * @return {@link HttpStatus#OK} si el usuario fue actualizado; {@link HttpStatus#NOT_FOUND} si no se encontró.
	 */
	public HttpStatusCode updateUser(Long id, UserDTO userDTO) {
		return findAndMap(userRepository.findById(id),
				(user) -> update(user, userDTO));
	}

	/**
	 * Encuentra un usuario y aplica una función si se encuentra.
	 *
	 * @param user Un {@link Optional} que contiene el usuario a mapear.
	 * @param fun  La función a aplicar si el usuario existe.
	 * @return {@link HttpStatus#OK} si la operación fue exitosa, {@link HttpStatus#NOT_FOUND} si no se encontró el usuario.
	 */
	private HttpStatusCode findAndMap(Optional<User> user, Function<User, HttpStatusCode> fun) {
		return user.map(fun)
				.orElse(HttpStatus.NOT_FOUND);
	}

	/**
	 * Actualiza un usuario con los datos proporcionados en el DTO.
	 *
	 * @param user    La entidad {@link User} a actualizar.
	 * @param updated El {@link UserDTO} que contiene los nuevos datos del usuario.
	 * @return {@link HttpStatus#OK} tras la actualización exitosa.
	 */
	private HttpStatusCode update(User user, UserDTO updated) {
		user.setPsk(updated.getPsk());
		user.setRoleId(updated.getRoleId());

		return HttpStatus.OK;
	}

	/**
	 * Actualiza la contraseña de un usuario.
	 *
	 * @param document    El documento del usuario cuya contraseña se actualizará.
	 * @param newPassword La nueva contraseña del usuario.
	 * @return {@link HttpStatus#OK} si la contraseña fue actualizada; {@link HttpStatus#NOT_FOUND} si no se encontró el usuario.
	 */
	public HttpStatusCode updatePsk(String document, String newPassword) {
		Optional<User> user = userRepository.findByDocument(document);
		return user.map((u) -> {
			u.setPsk(passwordEncoder.encode(newPassword));
			userRepository.save(u);
			return HttpStatus.OK;
		}).orElse(HttpStatus.NOT_FOUND);
	}

	/**
	 * Obtiene un usuario por su identificador.
	 *
	 * @param id El identificador del usuario.
	 * @return La entidad {@link User} si existe; de lo contrario, <code>null</code>.
	 */
	public User getUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	/**
	 * Obtiene un usuario por su documento.
	 *
	 * @param document El documento del usuario.
	 * @return La entidad {@link User} si existe; de lo contrario, <code>null</code>.
	 */
	public User getUser(String document) {
		return userRepository.findByDocument(document).orElse(null);
	}

}
