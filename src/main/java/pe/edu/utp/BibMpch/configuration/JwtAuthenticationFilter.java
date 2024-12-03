package pe.edu.utp.BibMpch.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pe.edu.utp.BibMpch.service.JwtService;

import java.io.IOException;

/**
 * Filtro de autenticación basado en tokens JWT.
 *
 * Este filtro se ejecuta una vez por solicitud y es responsable de
 * validar el token JWT presente en los encabezados de la solicitud.
 * Si el token es válido, autentica al usuario en el contexto de seguridad.
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Extraer el token JWT de los encabezados de la solicitud.</li>
 *   <li>Validar el token utilizando el servicio <code>JwtService</code>.</li>
 *   <li>Autenticar al usuario en el <code>SecurityContextHolder</code>.</li>
 * </ul>
 *
 * <p>Dependencias clave:</p>
 * <ul>
 *   <li><code>JwtService</code>: Valida y procesa los tokens JWT.</li>
 *   <li><code>UserDetailsService</code>: Carga los detalles del usuario autenticado.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;

	/**
	 * Método principal para filtrar solicitudes y autenticar usuarios.
	 *
	 * Este método se ejecuta por cada solicitud entrante, verifica si
	 * existe un token JWT válido en los encabezados y, si es válido,
	 * autentica al usuario.
	 *
	 * @param request  Solicitud HTTP entrante.
	 * @param response Respuesta HTTP saliente.
	 * @param filterChain Cadena de filtros de la aplicación.
	 * @throws ServletException Si ocurre un error durante el filtrado.
	 * @throws IOException Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		final String token = getTokenFromRequest(request);

		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		String username  = jwtService.getUsernameFromToken(token);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtService.isTokenValid(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
				);

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Extrae el token JWT del encabezado de autorización.
	 *
	 * Este método busca en el encabezado <code>Authorization</code> un token JWT
	 * que comience con la palabra clave <code>Bearer</code>.
	 *
	 * @param re Solicitud HTTP entrante.
	 * @return El token JWT, o <code>null</code> si no está presente.
	 */
	private String getTokenFromRequest(HttpServletRequest re) {
		final String header = re.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null || !header.startsWith("Bearer"))
			return null;

		return header.substring(7);
	}
}
