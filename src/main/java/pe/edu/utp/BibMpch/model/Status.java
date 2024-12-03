package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un estado en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_tipo_estado</code> en la base de datos,
 * donde se almacenan los diferentes estados utilizados en el sistema.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del estado.</li>
 *   <li><code>statusName</code>: Nombre descriptivo del estado.</li>
 *   <li><code>isActive</code>: Indica si el estado está activo.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_tipo_estado")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>id</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@Column(name = "...")</code>: Mapea atributos de la clase a columnas específicas de la tabla.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@NoArgsConstructor</code>: Genera un constructor sin argumentos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los argumentos.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Esta clase encapsula la información básica de un estado, incluyendo su identificador,
 * nombre y estado activo, facilitando el manejo de estos datos en el sistema.
 *
 * @author Vasquez
 * @version 1.0
 * @since 27/10/2024
 */
@Table(name = "tb_tipo_estado")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ties_id")
    private Short id;

    @Column(name = "ties_tipo")
    private String statusName;

    @Column(name = "ties_activo")
    private boolean isActive;
}
