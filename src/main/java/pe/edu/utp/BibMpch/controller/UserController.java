package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getUsers() {
		List<User> result = new ArrayList<>();
		userRepository.findAll().forEach(result::add);
		return ResponseEntity.ok(result);
	}
}
