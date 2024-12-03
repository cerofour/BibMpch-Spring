package pe.edu.utp.BibMpch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.DTO.LoginResponse;
import pe.edu.utp.BibMpch.DTO.LoginUserDTO;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.service.AuthService;

/**
 * Controlador para la autenticación de usuarios.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * de autenticación, como iniciar sesión y registrarse en la aplicación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/auth/login</code>: Inicia sesión en la aplicación.</li>
 *   <li><code>/auth/signup</code>: Registra un nuevo usuario.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Autenticar usuarios y generar tokens de acceso.</li>
 *   <li>Registrar nuevos usuarios en el sistema.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>AuthService</code>: Servicio que contiene la lógica para la autenticación y registro.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthService authService;

	/**
	 * Autentica a un usuario existente.
	 *
	 * <p><strong>Tipo de solicitud:</strong> POST</p>
	 * <p><strong>Ruta:</strong> <code>/auth/login</code></p>
	 *
	 * @param loginUserDTO Objeto que contiene las credenciales del usuario.
	 * @return Una respuesta con los datos de autenticación, incluyendo el token de acceso.
	 */
	@PostMapping("login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
		return ResponseEntity.ok(authService.authenticate(loginUserDTO));
	}

	/**
	 * Registra un nuevo usuario en la aplicación.
	 *
	 * <p><strong>Tipo de solicitud:</strong> POST</p>
	 * <p><strong>Ruta:</strong> <code>/auth/signup</code></p>
	 *
	 * @param registerUserDTO Objeto que contiene los datos del usuario a registrar.
	 * @return Una respuesta con los datos del usuario registrado.
	 */
	@PostMapping("signup")
	public ResponseEntity<User> signup(@RequestBody UserDTO registerUserDTO) {
		return ResponseEntity.ok(authService.signup(registerUserDTO));
	}
}
