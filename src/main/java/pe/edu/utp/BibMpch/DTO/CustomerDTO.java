package pe.edu.utp.BibMpch.DTO;

import lombok.*;
import pe.edu.utp.BibMpch.model.Customer;

/**
 * Data Transfer Object (DTO) para representar clientes.
 *
 * Este DTO se utiliza para transferir datos relacionados con clientes entre
 * las distintas capas de la aplicación, como controladores y servicios. Además,
 * al crear un cliente, también se crea un usuario y un carnet. Este DTO contiene
 * toda la información necesaria para crear estas entidades relacionadas.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>userData</code>: Información del usuario asociado al cliente.</li>
 *   <li><code>addressData</code>: Información de la dirección asociada al cliente.</li>
 *   <li><code>educationLevelId</code>: Identificador del nivel educativo del cliente.</li>
 *   <li><code>email</code>: Correo electrónico del cliente.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 *   <li><code>@NoArgsConstructor</code>: Genera un constructor sin argumentos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los argumentos.</li>
 *   <li><code>@ToString</code>: Genera un método <code>toString</code> personalizado.</li>
 * </ul>
 *
 * <p><strong>Relaciones:</strong></p>
 * <ul>
 *   <li>Este DTO se relaciona con las entidades <code>User</code>, <code>Address</code> y <code>Carnet</code>.</li>
 * </ul>
 *
 * <p><strong>Nota:</strong> Este DTO asegura que todas las dependencias necesarias para
 * crear un cliente se transfieran juntas.</p>
 *
 * @author Llacsahuanga, Vasquez
 * @version 1.0
 * @since 28/10/2024
 */
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CustomerDTO {
    private UserDTO userData;
    private AddressDTO addressData;
    private Short educationLevelId;
    private String email;
}