package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidad que representa un préstamo en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_prestamo</code> en la base de datos,
 * donde se almacenan los datos relacionados con los préstamos.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del préstamo.</li>
 *   <li><code>customer</code>: Cliente asociado al préstamo.</li>
 *   <li><code>codeTextualResource</code>: Recurso textual asociado al préstamo.</li>
 *   <li><code>idTypeLoan</code>: Identificador del tipo de préstamo.</li>
 *   <li><code>idStatusLoan</code>: Identificador del estado del préstamo.</li>
 *   <li><code>initialDate</code>: Fecha de inicio del préstamo.</li>
 *   <li><code>endDate</code>: Fecha de finalización del préstamo.</li>
 *   <li><code>scheduledDate</code>: Fecha programada para la devolución del préstamo.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_prestamo")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>id</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@ManyToOne</code>: Define relaciones muchos a uno con las entidades <code>Customer</code> y <code>CodeTextualResource</code>.</li>
 *   <li><code>@JoinColumn(name = "...")</code>: Especifica las columnas que relacionan esta entidad con otras tablas.</li>
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
 * Esta clase encapsula la información básica de un préstamo, incluyendo los datos del cliente,
 * recursos textuales, fechas y estados relacionados, facilitando su manejo en el sistema.
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@Table(name = "tb_prestamo")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pres_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pres_cliente_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "pres_recurso_textual_codigo_id")
    private CodeTextualResource codeTextualResource;

    @Column(name = "pres_tipo_prestamo_id")
    private Short idTypeLoan;

    @Column(name = "pres_estado_prestamo_id")
    private Short idStatusLoan;

    @Column(name = "pres_fec_inicial")
    private LocalDate initialDate;

    @Column(name = "pres_fec_final")
    private LocalDate endDate;

    @Column(name = "pres_fec_programada")
    private LocalDate scheduledDate;

}
