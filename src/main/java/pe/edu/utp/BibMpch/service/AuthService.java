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

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;
	private final PasswordEncoder passwordEncoder;
	private final GenderRepository genderRepository;

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

	public LoginResponse signup(UserDTO registerUserDTO) {

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

		userRepository.save(user);

		return LoginResponse.builder()
				.token(jwtService.getToken(user))
				.build();
	}
}
