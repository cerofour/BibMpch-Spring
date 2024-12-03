package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un tipo de recurso textual en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_tipo_texto</code> en la base de datos,
 * donde se almacenan los diferentes tipos de recursos textuales.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>typeId</code>: Identificador único del tipo de recurso textual.</li>
 *   <li><code>typename</code>: Nombre descriptivo del tipo de recurso textual.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_tipo_texto")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>typeId</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@Column(name = "...")</code>: Mapea atributos de la clase a columnas específicas de la tabla.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 *   <li><code>@ToString</code>: Genera un método <code>toString</code> personalizado.</li>
 *   <li><code>@NoArgsConstructor</code>: Genera un constructor sin argumentos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los argumentos.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Esta clase encapsula la información básica de un tipo de recurso textual,
 * facilitando el manejo de estos datos en el sistema.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 22/10/2024
 */
@Entity
@Table(name = "tb_tipo_texto")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TextResourceType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tite_id")
	private Long typeId;

	@Column(name = "tite_tipo")
	private String typename;
}
