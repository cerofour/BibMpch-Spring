package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) para representar la respuesta de inicio de sesión.
 *
 * Este DTO se utiliza para transferir datos relacionados con la autenticación
 * del usuario, específicamente el token de acceso y su tiempo de expiración.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>token</code>: Token de acceso generado tras la autenticación.</li>
 *   <li><code>expiresIn</code>: Tiempo en segundos hasta la expiración del token.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 *   <li><code>@NoArgsConstructor</code>: Genera un constructor sin argumentos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los argumentos.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este DTO encapsula la información básica que un cliente necesita después de
 * autenticarse correctamente en el sistema.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String token;
	private Long expiresIn;
}
