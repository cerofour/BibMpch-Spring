package pe.edu.utp.BibMpch.DTO;

import lombok.*;

/**
 * Data Transfer Object (DTO) para representar códigos de recursos textuales.
 *
 * Este DTO se utiliza para transferir datos relacionados con los códigos
 * de recursos textuales entre las distintas capas de la aplicación, como
 * controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del código de recurso textual.</li>
 *   <li><code>baseCode</code>: Código base asociado al recurso textual.</li>
 *   <li><code>exemplaryCode</code>: Código ejemplar del recurso textual.</li>
 *   <li><code>available</code>: Indica si el recurso está disponible.</li>
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
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CodeTextualResourceDTO {
    private Long id;
    private TextDTO baseCode;
    private Integer exemplaryCode;
    private Boolean available;
}