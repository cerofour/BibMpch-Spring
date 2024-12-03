package pe.edu.utp.BibMpch.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración para la seguridad de la aplicación.
 *
 * Esta clase define las configuraciones de seguridad utilizando
 * Spring Security. Configura el acceso a los recursos, la autenticación
 * y las políticas de sesión para la aplicación.
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Configurar políticas de acceso a los endpoints.</li>
 *   <li>Gestionar el proveedor de autenticación personalizado.</li>
 *   <li>Integrar el filtro de autenticación JWT.</li>
 * </ul>
 *
 * <p>Dependencias clave:</p>
 * <ul>
 *   <li><code>JwtAuthenticationFilter</code>: Filtro para manejar tokens JWT.</li>
 *   <li><code>AuthenticationProvider</code>: Proveedor personalizado de autenticación.</li>
 *   <li><code>HttpSecurity</code>: Configura la seguridad HTTP de la aplicación.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;

	/**
	 * Configuración principal de seguridad HTTP.
	 *
	 * Este método define las reglas de seguridad para la aplicación,
	 * incluyendo políticas de acceso a los endpoints, autenticación
	 * y manejo de sesiones.
	 *
	 * <p>Configuraciones clave:</p>
	 * <ul>
	 *   <li>Permitir solicitudes OPTIONS para todos los endpoints.</li>
	 *   <li>Permitir acceso sin autenticación al endpoint <code>/auth/login</code>.</li>
	 *   <li>Requerir autenticación para cualquier otra solicitud.</li>
	 *   <li>Establecer una política de sesión sin estado.</li>
	 * </ul>
	 *
	 * @param http Objeto <code>HttpSecurity</code> proporcionado por Spring Security.
	 * @return Una configuración completamente definida de seguridad HTTP.
	 * @throws Exception Si ocurre algún error durante la configuración.
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authRequest) -> authRequest
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Allow OPTIONS requests
						.requestMatchers("/auth/login").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
