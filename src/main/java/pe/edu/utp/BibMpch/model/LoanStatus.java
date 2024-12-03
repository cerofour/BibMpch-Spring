package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa el estado de un préstamo en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_estado_prestamo</code> en la base de datos,
 * donde se almacenan los diferentes estados asociados a los préstamos.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del estado del préstamo.</li>
 *   <li><code>name</code>: Nombre descriptivo del estado del préstamo.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_estado_prestamo")</code>: Especifica la tabla de la base de datos asociada.</li>
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
 * Esta clase encapsula la información básica de un estado de préstamo, incluyendo su identificador y nombre,
 * facilitando el manejo de estos datos en el sistema.
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@Table(name = "tb_estado_prestamo")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "espr_id")
    private Short id;

    @Column(name = "espr_nombre")
    private String name;

}
