package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa una dirección en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_direccion_cliente</code> en la base de datos,
 * donde se almacenan las direcciones asociadas a los clientes.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único de la dirección.</li>
 *   <li><code>district</code>: Distrito asociado a la dirección.</li>
 *   <li><code>address</code>: Descripción específica de la dirección.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_direccion_cliente")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>id</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@Column(name = "...")</code>: Mapea atributos de la clase a columnas específicas de la tabla.</li>
 *   <li><code>@ManyToOne</code>: Define una relación de muchos a uno con la entidad <code>District</code>.</li>
 *   <li><code>@JoinColumn(name = "dicl_distrito_id")</code>: Especifica la columna que relaciona esta entidad con la tabla de distritos.</li>
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
 * Esta clase encapsula la información de las direcciones y su relación con los distritos,
 * facilitando el manejo de datos relacionados en el sistema.
 *
 * @author Vasquez
 * @version 1.0
 * @since 27/10/2024
 */
@Table(name = "tb_direccion_cliente")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dicl_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dicl_distrito_id")
    private District district;

    @Column(name = "dicl_direccion")
    private String address;
}