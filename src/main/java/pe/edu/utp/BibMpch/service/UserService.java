package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.authorization.UserRole;
import pe.edu.utp.BibMpch.authorization.UserRoleRepository;
import pe.edu.utp.BibMpch.repository.UserRepository;

import pe.edu.utp.BibMpch.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;
	private final RegisterActionsService registerActionsService;

	public List<User> allUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	private HttpStatusCode delete(User u) {
		userRepository.delete(u);
		return HttpStatus.OK;
	}

	public HttpStatusCode deleteUser(String document) {
		return findAndMap(userRepository.findByDocument(document), this::delete);
	}

	public HttpStatusCode deleteUser(Long id) {
		return findAndMap(userRepository.findById(id), this::delete);
	}

	public HttpStatusCode updateUser(String document, UserDTO userDTO) {
		return findAndMap(userRepository.findByDocument(document),
				(user) -> update(user, userDTO));
	}

	public HttpStatusCode updateUser(Long id, UserDTO userDTO) {
		return findAndMap(userRepository.findById(id),
				(user) -> update(user, userDTO));
	}

	private HttpStatusCode findAndMap(Optional<User> user, Function<User, HttpStatusCode> fun) {
		return user.map(fun)
				.orElse(HttpStatus.NOT_FOUND);
	}

	private HttpStatusCode update(User user, UserDTO updated) {

		UserRole role = userRoleRepository.findById(updated.getRoleId())
				.orElseThrow(() -> new EntityNotFoundException("Rol no encontrado."));

		user.setPsk(updated.getPsk());
		user.setRole(role);

		registerActionsService.newRegisterAction(
				"Actualizó un usuario - ID: %d - ID Rol: %s".formatted(
						user.getUserId(),
						updated.getRoleId())
		);

		return HttpStatus.OK;
	}

	public HttpStatusCode updatePsk(String document, String newPassword) {
		Optional<User> user = userRepository.findByDocument(document);
		return user.map((u) -> {
			u.setPsk(passwordEncoder.encode(newPassword));
			userRepository.save(u);
			registerActionsService.newRegisterAction(
					"Actualizó la contraseña del usuario - ID: %s".formatted(document)
			);
			return HttpStatus.OK;
		}).orElse(HttpStatus.NOT_FOUND);
	}

	public User getUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public User getUser(String document) {
		return userRepository.findByDocument(document).orElse(null);
	}

}
