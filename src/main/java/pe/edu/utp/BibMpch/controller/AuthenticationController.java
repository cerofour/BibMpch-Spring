package pe.edu.utp.BibMpch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.DTO.LoginResponse;
import pe.edu.utp.BibMpch.DTO.LoginUserDTO;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.service.AuthService;
import pe.edu.utp.BibMpch.service.RegisterActionsService;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthService authService;
	private final RegisterActionsService registerActionsService;


	@PostMapping("login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
		LoginResponse loginResponse = authService.authenticate(loginUserDTO);
		if(loginResponse != null)
			registerActionsService.newRegisterAction("Inició sesión", loginUserDTO.getDocument());

		return ResponseEntity.ok(loginResponse);
	}

	@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
	@PostMapping("signup")
	public ResponseEntity<User> signup(@RequestBody UserDTO registerUserDTO) {
		User user = authService.signup(registerUserDTO);
		if(user != null)
			registerActionsService.newRegisterAction(
                    "Se registró un nuevo usuario - Documento: %s"
							.formatted(registerUserDTO.getDocument())
			);
		return ResponseEntity.ok(user);
	}
}
