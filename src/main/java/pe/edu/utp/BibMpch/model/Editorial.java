package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una editorial en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_editorial</code> en la base de datos,
 * donde se almacenan los datos relacionados con las editoriales.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único de la editorial.</li>
 *   <li><code>name</code>: Nombre de la editorial.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_editorial")</code>: Especifica la tabla de la base de datos asociada.</li>
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
 * Esta clase encapsula la información básica de una editorial, incluyendo su identificador y nombre,
 * facilitando el manejo de estos datos en el sistema.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 22/10/2024
 */
@Entity
@Table(name = "tb_editorial")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Editorial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "edit_id")
	private Long id;

	@Column(name = "edit_nombre")
	private String name;
}
