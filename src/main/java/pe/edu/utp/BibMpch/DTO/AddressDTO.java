package pe.edu.utp.BibMpch.DTO;

import lombok.*;
import pe.edu.utp.BibMpch.model.Address;

/**
 * Data Transfer Object (DTO) para representar direcciones.
 *
 * Este DTO se utiliza para transferir datos relacionados con direcciones
 * entre las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>district</code>: Identificador del distrito asociado a la dirección.</li>
 *   <li><code>address</code>: Detalles específicos de la dirección (calle, número, etc.).</li>
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
 * @author Vasquez
 * @version 1.0
 * @since 28/10/2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long district;
    private String address;
}