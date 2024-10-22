package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.LoginUserDTO;
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
	@PostMapping(value = "/update_psk")
	public ResponseEntity<Void> updatePsk(@RequestBody LoginUserDTO newPsk) {
		return ResponseEntity.status(userService.updatePsk(newPsk.getDocument(), newPsk.getPsk()))
				.build();
	}

	@DeleteMapping(value = "/delete/{document}")
	@SuppressWarnings("unused")
	public ResponseEntity<Void> deleteUser(@PathVariable(name = "document") String document) {
		return ResponseEntity.status(userService.deleteUser(document))
				.build();
	}
}
