package pe.edu.utp.BibMpch.DTO;

import lombok.*;

/**
 * Data Transfer Object (DTO) para representar el estado de los préstamos.
 *
 * Este DTO se utiliza para transferir datos relacionados con los estados de
 * los préstamos entre las distintas capas de la aplicación, como controladores
 * y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del estado del préstamo.</li>
 *   <li><code>nombre</code>: Nombre descriptivo del estado del préstamo.</li>
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
 * <p><strong>Utilidad:</strong></p>
 * Este DTO encapsula la información del estado de los préstamos, facilitando su transferencia
 * y manipulación en la aplicación.
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LoanStatusDTO {
    private Short id;
    private String nombre;
}
