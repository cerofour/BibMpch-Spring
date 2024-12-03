package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un recurso textual con códigos en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_recurso_textual_codigo</code> en la base de datos,
 * donde se almacenan los códigos únicos asociados a los recursos textuales.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del recurso textual.</li>
 *   <li><code>baseCode</code>: Código base único del recurso textual.</li>
 *   <li><code>exemplaryCode</code>: Código ejemplar del recurso textual.</li>
 *   <li><code>available</code>: Indica si el recurso está disponible.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_recurso_textual_codigo")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>id</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@Column(name = "...")</code>: Mapea atributos de la clase a columnas específicas de la tabla.</li>
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
 * Esta clase encapsula la información de los recursos textuales, incluyendo códigos únicos y
 * su disponibilidad, facilitando su manejo en el sistema.
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@Table(name = "tb_recurso_textual_codigo")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeTextualResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reco_id")
    private Long id;

    @Column(name = "reco_rete_codigo_base")
    private String baseCode;

    @Column(name = "reco_codigo_ejemplar")
    private Integer exemplaryCode;

    @Column(name = "reco_disponible")
    private Boolean available;

}
