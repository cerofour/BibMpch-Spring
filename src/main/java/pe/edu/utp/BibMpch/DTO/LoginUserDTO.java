package pe.edu.utp.BibMpch.DTO;

import lombok.*;

/**
 * Data Transfer Object (DTO) para representar las credenciales de inicio de sesión del usuario.
 *
 * Este DTO se utiliza para transferir los datos necesarios para la autenticación
 * de un usuario en el sistema, como su documento de identidad y su contraseña.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>document</code>: Documento de identidad del usuario.</li>
 *   <li><code>psk</code>: Contraseña del usuario.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 *   <li><code>@ToString</code>: Genera un método <code>toString</code> personalizado.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este DTO encapsula las credenciales de inicio de sesión requeridas para autenticar
 * a un usuario en el sistema.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@Data
@Builder
@ToString
public class LoginUserDTO {
	private String document;
	private String psk;
}
