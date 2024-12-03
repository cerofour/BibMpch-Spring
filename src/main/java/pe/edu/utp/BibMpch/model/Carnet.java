package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidad que representa un carnet en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_carnet</code> en la base de datos,
 * donde se almacenan los datos relacionados con los carnets de los clientes.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del carnet.</li>
 *   <li><code>status</code>: Estado actual del carnet.</li>
 *   <li><code>code</code>: Código único del carnet.</li>
 *   <li><code>carnetIssuanceDate</code>: Fecha de emisión del carnet.</li>
 *   <li><code>carnetExpirationDate</code>: Fecha de vencimiento del carnet.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_carnet")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>id</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@ManyToOne</code>: Define una relación de muchos a uno con la entidad <code>Status</code>.</li>
 *   <li><code>@JoinColumn(name = "carn_tipo_estado_id")</code>: Especifica la columna que relaciona esta entidad con el estado del carnet.</li>
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
 * Esta clase encapsula la información básica de un carnet, incluyendo su estado,
 * código y fechas relevantes, facilitando el manejo de estos datos en el sistema.
 *
 * @author Vasquez
 * @version 1.0
 * @since 27/10/2024
 */
@Table(name = "tb_carnet")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carnet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carn_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carn_tipo_estado_id")
    private Status status;

    @Column(name = "carn_codigo")
    private String code;

    @Column(name = "carn_fec_emision")
    private LocalDate carnetIssuanceDate;

    @Column(name = "carn_fec_vencimiento")
    private LocalDate carnetExpirationDate;
}