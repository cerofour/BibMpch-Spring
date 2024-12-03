package pe.edu.utp.BibMpch.DTO;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para representar usuarios.
 *
 * Este DTO se utiliza para transferir datos relacionados con usuarios
 * entre las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>document</code>: Documento de identidad del usuario.</li>
 *   <li><code>documentTypeId</code>: Identificador del tipo de documento del usuario.</li>
 *   <li><code>roleId</code>: Identificador del rol asociado al usuario.</li>
 *   <li><code>psk</code>: Contraseña del usuario.</li>
 *   <li><code>name</code>: Nombre del usuario.</li>
 *   <li><code>plastname</code>: Apellido paterno del usuario.</li>
 *   <li><code>mlastname</code>: Apellido materno del usuario.</li>
 *   <li><code>phoneNumber</code>: Número de teléfono del usuario.</li>
 *   <li><code>genderId</code>: Identificador del género del usuario.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este DTO encapsula la información necesaria para crear y manipular usuarios
 * dentro del sistema, incluyendo credenciales, datos personales y de contacto.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@Data
@Builder
public class UserDTO {
	private String document;
	private Short documentTypeId;
	private Short roleId;
	private String psk;

	private String name;
	private String plastname;
	private String mlastname;
	private String phoneNumber;
	private Short genderId;
}
