package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.LoginUserDTO;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.service.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/users")
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping(value = "/")
	@SuppressWarnings("unused")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.allUsers());
	}

	@SuppressWarnings("unused")
	@GetMapping(value = "/me")
	public ResponseEntity<User> getCurrentUser() {
		User thisUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(thisUser);
	}

	@SuppressWarnings("unused")
	@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
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

	@SuppressWarnings("unused")
	@PostMapping(value = "/update")
	@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
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

	@SuppressWarnings("unused")
	@PostMapping(value = "/update_psk")
	public ResponseEntity<Void> updatePsk(@RequestBody LoginUserDTO newPsk) {
		return ResponseEntity.status(userService.updatePsk(newPsk.getDocument(), newPsk.getPsk()))
				.build();
	}

	@DeleteMapping(value = "/delete")
	@SuppressWarnings("unused")
	@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
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
