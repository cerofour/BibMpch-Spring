package pe.edu.utp.BibMpch.exceptions;

import lombok.ToString;

/**
 * Excepción personalizada lanzada cuando un recurso no es encontrado en el sistema.
 *
 * <p>Esta clase extiende {@link Exception} y se utiliza para señalar situaciones en las que
 * el recurso solicitado no está disponible o no existe en la base de datos.</p>
 *
 * <p>La excepción incluye el mensaje de error que describe el recurso que no se pudo encontrar.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link lombok.ToString}: Genera automáticamente el método {@code toString} para la clase.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 22/10/2024
 */
@ToString
public class ResourceNotFoundException extends Exception {
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
