package pe.edu.utp.BibMpch.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para gestionar la generación, validación y extracción de información de tokens JWT.
 *
 * <p>Proporciona métodos para generar tokens JWT, validar su autenticidad y extraer datos como
 * el nombre de usuario asociado o la fecha de expiración.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>@Value("${security.jwt.secret-key}")</code>: Clave secreta utilizada para firmar los tokens.</li>
 *   <li><code>@Value("${security.jwt.expiration-time}")</code>: Tiempo de expiración de los tokens en milisegundos.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #getToken(UserDetails)}: Genera un token JWT para un usuario.</li>
 *   <li>{@link #getUsernameFromToken(String)}: Obtiene el nombre de usuario asociado a un token JWT.</li>
 *   <li>{@link #isTokenValid(String, UserDetails)}: Valida si un token JWT es válido para un usuario.</li>
 * </ul>
 *
 * <p><strong>Métodos privados:</strong></p>
 * <ul>
 *   <li>{@link #getToken(Map, UserDetails, Long)}: Genera un token JWT con datos personalizados.</li>
 *   <li>{@link #getExpiration(String)}: Obtiene la fecha de expiración de un token JWT.</li>
 *   <li>{@link #getAllClaims(String)}: Obtiene todos los claims de un token JWT.</li>
 *   <li>{@link #getClaim(String, Function)}: Extrae un claim específico de un token JWT.</li>
 *   <li>{@link #isTokenExpired(String)}: Comprueba si un token JWT ha expirado.</li>
 *   <li>{@link #getKey()}: Obtiene la clave de firma utilizada para los tokens JWT.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 * </ul>
 *
 * @author Llacsahuenga
 * @version 1.0
 * @since 11/10/2024
 */
@Service
public class JwtService {
	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	/**
	 * Genera un token JWT para un usuario.
	 *
	 * @param user Detalles del usuario para los cuales se generará el token.
	 * @return Un token JWT firmado.
	 */
	public String getToken(UserDetails user) {
		return getToken(new HashMap<>(), user, jwtExpiration);
	}

	/**
	 * Genera un token JWT con datos personalizados, un usuario y una fecha de expiración.
	 *
	 * @param claims     Datos adicionales que se incluirán en el token.
	 * @param user       Usuario para el cual se genera el token.
	 * @param expiration Tiempo de expiración del token en milisegundos.
	 * @return El token JWT generado como una cadena.
	 */
	private String getToken(Map<String, Object> claims, UserDetails user, Long expiration) {
		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * Obtiene el tiempo de expiración configurado para los tokens JWT.
	 *
	 * @return El tiempo de expiración en milisegundos.
	 */
	public long getExpirationTime() {
		return jwtExpiration;
	}

	/**
	 * Obtiene la clave utilizada para firmar los tokens JWT.
	 *
	 * @return Una instancia de {@link Key} generada a partir de la clave secreta.
	 */
	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Obtiene el nombre de usuario asociado a un token JWT.
	 *
	 * @param token El token JWT del cual extraer el nombre de usuario.
	 * @return El nombre de usuario contenido en el token.
	 */
	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	/**
	 * Obtiene la fecha de expiración de un token JWT.
	 *
	 * @param token El token JWT del cual extraer la fecha de expiración.
	 * @return La fecha de expiración del token.
	 */
	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}

	/**
	 * Valida si un token JWT es válido para un usuario.
	 *
	 * @param token       El token JWT a validar.
	 * @param userDetails Detalles del usuario contra los cuales validar el token.
	 * @return <code>true</code> si el token es válido, de lo contrario <code>false</code>.
	 */
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * Obtiene todos los claims de un token JWT.
	 *
	 * @param token El token JWT del cual extraer los claims.
	 * @return Un objeto {@link Claims} que contiene los datos del token.
	 */
	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * Extrae un claim específico de un token JWT utilizando un resolvedor.
	 *
	 * @param <T>            Tipo del claim a extraer.
	 * @param token          El token JWT del cual extraer el claim.
	 * @param claimsResolver Función para resolver y extraer el claim específico.
	 * @return El valor del claim extraído.
	 */
	private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Comprueba si un token JWT ha expirado.
	 *
	 * @param token El token JWT a comprobar.
	 * @return <code>true</code> si el token ha expirado, de lo contrario <code>false</code>.
	 */
	private boolean isTokenExpired(String token) {
		boolean x = getExpiration(token).before(new Date());
		if (x)
			System.out.println("token expired");
		return x;
	}
}
