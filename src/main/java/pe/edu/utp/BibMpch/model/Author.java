package pe.edu.utp.BibMpch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Entidad que representa un autor en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_autor</code> en la base de datos,
 * donde se almacenan los datos de los autores.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del autor.</li>
 *   <li><code>name</code>: Nombre del autor.</li>
 *   <li><code>pLastName</code>: Apellido paterno del autor.</li>
 *   <li><code>mLastName</code>: Apellido materno del autor.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_autor")</code>: Especifica la tabla de la base de datos asociada.</li>
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
 *   <li><code>@ToString</code>: Genera un método <code>toString</code> personalizado.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Esta clase encapsula la información básica de un autor, incluyendo sus nombres y apellidos,
 * facilitando el manejo de los datos en el sistema.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since [Fecha de creación]
 */
@Table(name = "tb_autor")
@Entity
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auto_id")
	private Long id;

	@Column(name = "auto_nombre")
	private String name;

	@Column(name = "auto_apellido_paterno")
	private String pLastName;

	@Column(name = "auto_apellido_materno")
	private String mLastName;
}
