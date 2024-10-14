package pe.edu.utp.BibMpch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.DTO.LoginResponse;
import pe.edu.utp.BibMpch.DTO.LoginUserDTO;
import pe.edu.utp.BibMpch.DTO.RegisterUserDTO;
import pe.edu.utp.BibMpch.service.AuthService;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthService authService;

	@PostMapping("login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
		return ResponseEntity.ok(authService.authenticate(loginUserDTO));
	}

	@PostMapping("signup")
	public ResponseEntity<LoginResponse> signup(@RequestBody RegisterUserDTO registerUserDTO) {
		return ResponseEntity.ok(authService.signup(registerUserDTO));
	}
}
