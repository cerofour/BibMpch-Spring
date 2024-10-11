package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.LoginResponse;
import pe.edu.utp.BibMpch.DTO.LoginUserDTO;
import pe.edu.utp.BibMpch.DTO.RegisterUserDTO;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;
	private final PasswordEncoder passwordEncoder;

	public LoginResponse authenticate(LoginUserDTO loginUserDTO) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginUserDTO.getUsername(),
						loginUserDTO.getPsk()));

		UserDetails userDetails = userRepository
				.findByUsername(loginUserDTO.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Cannot authenticate, username not found"));

		return LoginResponse.builder()
				.token(jwtService.getToken(userDetails))
				.expiresIn(jwtService.getExpirationTime())
				.build();
	}

	public LoginResponse signup(RegisterUserDTO registerUserDTO) {
		User user = User.builder()
				.username(registerUserDTO.getUsername())
				.psk(passwordEncoder.encode(registerUserDTO.getPsk()))
				.build();

		userRepository.save(user);

		return LoginResponse.builder()
				.token(jwtService.getToken(user))
				.build();
	}
}
