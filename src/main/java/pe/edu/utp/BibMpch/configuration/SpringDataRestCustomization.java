package pe.edu.utp.BibMpch.configuration;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Personalización de configuraciones para Spring Data REST.
 *
 * Esta clase define políticas específicas de CORS (Cross-Origin Resource Sharing)
 * para las APIs REST generadas automáticamente por Spring Data REST. Permite
 * solicitudes desde cualquier origen con métodos HTTP configurados.
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Configurar políticas de CORS para las APIs REST.</li>
 *   <li>Establecer tiempos de expiración y permisos para solicitudes externas.</li>
 * </ul>
 *
 * <p>Configuración actual:</p>
 * <ul>
 *   <li>Permite solicitudes desde cualquier origen (<code>*</code>).</li>
 *   <li>Métodos HTTP permitidos: <code>HEAD, GET, POST, PUT, DELETE, PATCH, OPTIONS</code>.</li>
 *   <li>No permite el envío de credenciales.</li>
 *   <li>Tiempo de expiración de la configuración: 3600 segundos.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 20/10/2024
 */
@Component
public class SpringDataRestCustomization implements RepositoryRestConfigurer {

	/**
	 * Configura las políticas de CORS para las APIs REST.
	 *
	 * Este método define las políticas de intercambio de recursos entre
	 * orígenes diferentes para las APIs REST generadas automáticamente
	 * por Spring Data REST.
	 *
	 * <p>Configuraciones clave:</p>
	 * <ul>
	 *   <li>Permitir solicitudes desde cualquier origen (<code>*</code>).</li>
	 *   <li>Métodos HTTP permitidos: <code>HEAD, GET, POST, PUT, DELETE, PATCH, OPTIONS</code>.</li>
	 *   <li>No permite el envío de credenciales.</li>
	 *   <li>Tiempo de expiración: 3600 segundos.</li>
	 * </ul>
	 *
	 * @param config Configuración del repositorio REST.
	 * @param cors Registro de configuraciones CORS.
	 */
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

		cors.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
				.allowCredentials(false).maxAge(3600);
	}
}
