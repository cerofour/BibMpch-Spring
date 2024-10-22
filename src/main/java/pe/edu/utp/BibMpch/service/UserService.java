package pe.edu.utp.BibMpch.service;

import lombok.AllArgsConstructor;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.repository.UserRepository;

import pe.edu.utp.BibMpch.model.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public List<User> allUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	public HttpStatusCode deleteUser(String document) {
		Optional<User> user = userRepository.findByDocument(document);

		return user.map((u) -> {
			userRepository.delete(u);
			return HttpStatus.OK;
		}).orElse(HttpStatus.NOT_FOUND);
	}

	public HttpStatusCode updatePsk(String document, String newPassword) {
		Optional<User> user = userRepository.findByDocument(document);
		return user.map((u) -> {
			u.setPsk(passwordEncoder.encode(newPassword));
			userRepository.save(u);
			return HttpStatus.OK;
		}).orElse(HttpStatus.NOT_FOUND);
	}
}
