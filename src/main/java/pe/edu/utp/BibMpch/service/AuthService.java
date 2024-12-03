package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.LoginResponse;
import pe.edu.utp.BibMpch.DTO.LoginUserDTO;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.model.Gender;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.repository.GenderRepository;
import pe.edu.utp.BibMpch.repository.UserRepository;

/**
 * Servicio para gestionar la autenticación y el registro de usuarios en el sistema.
 *
 * <p>Proporciona métodos para autenticar usuarios existentes y registrar nuevos usuarios,
 * manejando la lógica de negocio relacionada con la seguridad y las credenciales.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link UserRepository}: Repositorio para gestionar las operaciones de persistencia de usuarios.</li>
 *   <li>{@link JwtService}: Servicio para generar y gestionar tokens JWT.</li>
 *   <li>{@link AuthenticationManager}: Gestor de autenticación de Spring Security.</li>
 *   <li>{@link PasswordEncoder}: Codificador de contraseñas.</li>
 *   <li>{@link GenderRepository}: Repositorio para gestionar las operaciones de persistencia de géneros.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #authenticate(LoginUserDTO)}: Autentica a un usuario utilizando sus credenciales.</li>
 *   <li>{@link #signup(UserDTO)}: Registra un nuevo usuario en el sistema.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@RequiredArgsConstructor</code>: Genera un constructor para inicializar los atributos finales.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;
	private final PasswordEncoder passwordEncoder;
	private final GenderRepository genderRepository;

	/**
	 * Autentica a un usuario utilizando sus credenciales.
	 *
	 * @param loginUserDTO DTO que contiene el documento y contraseña del usuario.
	 * @return Un {@link LoginResponse} que contiene el token de autenticación y su tiempo de expiración.
	 * @throws UsernameNotFoundException Si el usuario no se encuentra en el sistema.
	 */
	public LoginResponse authenticate(LoginUserDTO loginUserDTO) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginUserDTO.getDocument(),
						loginUserDTO.getPsk()));

		UserDetails userDetails = userRepository
				.findByDocument(loginUserDTO.getDocument())
				.orElseThrow(() -> new UsernameNotFoundException("Cannot authenticate, username not found"));

		return LoginResponse.builder()
				.token(jwtService.getToken(userDetails))
				.expiresIn(jwtService.getExpirationTime())
				.build();
	}

	/**
	 * Registra un nuevo usuario en el sistema.
	 *
	 * @param registerUserDTO DTO que contiene los datos del usuario a registrar.
	 * @return La entidad {@link User} que se creó y guardó.
	 * @throws EntityNotFoundException Si el género proporcionado no existe.
	 */
	public User signup(UserDTO registerUserDTO) throws EntityNotFoundException {

		Gender gender = genderRepository.findById(registerUserDTO.getGenderId())
				.orElseThrow(() -> new EntityNotFoundException("Género no encontrado"));

		User user = User.builder()
				.document(registerUserDTO.getDocument())
				.documentTypeId(registerUserDTO.getDocumentTypeId())
				.psk(passwordEncoder.encode(registerUserDTO.getPsk()))
				.name(registerUserDTO.getName())
				.pLastName(registerUserDTO.getPlastname())
				.mLastName(registerUserDTO.getMlastname())
				.phoneNumber(registerUserDTO.getPhoneNumber())
				.roleId(registerUserDTO.getRoleId())
				.gender(gender)
				.build();

		return userRepository.save(user);
	}
}
