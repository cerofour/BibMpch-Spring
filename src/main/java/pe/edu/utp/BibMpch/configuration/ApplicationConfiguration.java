package pe.edu.utp.BibMpch.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.utp.BibMpch.repository.UserRepository;

/**
 * Configuración principal de la aplicación.
 *
 * Esta clase define los beans necesarios para la autenticación y gestión
 * de usuarios en la aplicación. Proporciona configuraciones específicas
 * para el proveedor de autenticación, el servicio de detalles de usuario
 * y el codificador de contraseñas.
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Configurar el gestor de autenticación.</li>
 *   <li>Definir un proveedor de autenticación basado en DAO.</li>
 *   <li>Proveer un servicio para cargar detalles de usuario desde el repositorio.</li>
 *   <li>Proporcionar un codificador de contraseñas seguro.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

	private final UserRepository userRepository;

	/**
	 * Proporciona el gestor de autenticación.
	 *
	 * Este bean permite gestionar los procesos de autenticación en la
	 * aplicación utilizando la configuración predeterminada de Spring Security.
	 *
	 * @param authConfiguration Configuración de autenticación proporcionada por Spring.
	 * @return Una instancia del <code>AuthenticationManager</code>.
	 * @throws Exception Si ocurre un error al inicializar el gestor.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration)
		throws Exception {
		return authConfiguration.getAuthenticationManager();
	}

	/**
	 * Define un proveedor de autenticación basado en DAO.
	 *
	 * Este método configura un proveedor que utiliza el servicio de detalles
	 * de usuario y el codificador de contraseñas para validar credenciales.
	 *
	 * @return Un proveedor de autenticación configurado.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}

	/**
	 * Servicio para cargar detalles de usuario desde el repositorio.
	 *
	 * Este método utiliza el <code>UserRepository</code> para buscar usuarios
	 * por su documento y proporciona los detalles necesarios para la autenticación.
	 *
	 * @return Un servicio para cargar detalles de usuario.
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		/**
		 * May not be clear at first.
		 * UserDetails is just an interface that implements findByUsername(String username)
		 * so just return a lambda function that queries the UserRepository
		 * for the desired user.
		 */
		return (username) -> userRepository.findByDocument(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}

	/**
	 * Proporciona un codificador de contraseñas seguro.
	 *
	 * Este método retorna un codificador basado en el algoritmo BCrypt
	 * para garantizar la seguridad de las contraseñas almacenadas.
	 *
	 * @return Un codificador de contraseñas BCrypt.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
