package pe.edu.utp.BibMpch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de políticas de CORS (Cross-Origin Resource Sharing).
 *
 * Esta clase define las reglas para el intercambio de recursos entre
 * orígenes diferentes, permitiendo solicitudes desde otros dominios.
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Definir las rutas y métodos HTTP permitidos.</li>
 *   <li>Permitir o restringir accesos desde dominios específicos.</li>
 * </ul>
 *
 * <p>Configuración actual:</p>
 * <ul>
 *   <li>Permite solicitudes desde cualquier origen (<code>*</code>).</li>
 *   <li>Métodos permitidos: <code>HEAD, GET, POST, PUT, DELETE, PATCH, OPTIONS</code>.</li>
 *   <li>Aplica la configuración a todas las rutas (<code>/**</code>).</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 14/10/2024
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

	/**
	 * Define las configuraciones de CORS para la aplicación.
	 *
	 * Este método crea un bean que permite solicitudes CORS desde
	 * cualquier origen a todas las rutas y para los métodos HTTP
	 * especificados.
	 *
	 * @return Un configurador de CORS (<code>WebMvcConfigurer</code>).
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			/**
			 * Configura las políticas de CORS.
			 *
			 * Este método permite solicitudes desde cualquier origen
			 * (<code></code>), para todas las rutas (<code>/*</code>) y
			 * para los métodos HTTP especificados.
			 *
			 * @param registry Registro de configuraciones CORS.
			 */
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
						.allowedOrigins("*");
			}
		};
	}
}
