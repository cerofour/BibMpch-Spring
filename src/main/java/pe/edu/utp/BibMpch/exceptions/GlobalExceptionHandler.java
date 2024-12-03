package pe.edu.utp.BibMpch.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase de manejo global de excepciones para la aplicación.
 *
 * <p>Esta clase intercepta las excepciones lanzadas por el controlador de Spring y maneja diferentes tipos
 * de excepciones para devolver respuestas apropiadas con detalles de los errores.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link BadCredentialsException}: Maneja excepciones relacionadas con credenciales incorrectas.</li>
 *   <li>{@link AccountStatusException}: Maneja excepciones relacionadas con el estado de la cuenta.</li>
 *   <li>{@link AccessDeniedException}: Maneja excepciones cuando el acceso es denegado.</li>
 *   <li>{@link SignatureException}: Maneja excepciones relacionadas con firmas JWT inválidas.</li>
 *   <li>{@link ExpiredJwtException}: Maneja excepciones cuando un token JWT ha expirado.</li>
 *   <li>{@link ResourceNotFoundException}: Maneja excepciones cuando un recurso no se encuentra.</li>
 * </ul>
 *
 * @author Llacsahuenga
 * @version 1.0
 * @since 21/10/2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ProblemDetail handleSecurityException(Exception exception) {
		ProblemDetail errorDetail = null;

		// TODO send this stack trace to an observability tool
		//exception.printStackTrace();

		if (exception instanceof BadCredentialsException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
			errorDetail.setProperty("description", "El documento o contraseña son incorrectos");

			return errorDetail;
		}

		if (exception instanceof AccountStatusException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "Cuenta bloqueada");
		}

		if (exception instanceof AccessDeniedException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "No está autorizado para acceder a este recurso");
		}

		if (exception instanceof SignatureException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "La firma JWT es inválida.");
		}

		if (exception instanceof ExpiredJwtException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "El token JWT ha expirado.");
		}

		if (exception instanceof ResourceNotFoundException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
			errorDetail.setProperty("description", "Recurso no encontrado.");
		}

		if (errorDetail == null) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
			errorDetail.setProperty("description", "Error desconocido.");
		}

		return errorDetail;
	}
}