package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import jakarta.persistence.Convert;
import lombok.*;

/**
 * Entidad que representa un cliente en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_cliente</code> en la base de datos,
 * donde se almacenan los datos relacionados con los clientes.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del cliente.</li>
 *   <li><code>user</code>: Usuario asociado al cliente.</li>
 *   <li><code>address</code>: Dirección asociada al cliente.</li>
 *   <li><code>email</code>: Correo electrónico del cliente.</li>
 *   <li><code>carnet</code>: Carnet asociado al cliente.</li>
 *   <li><code>education</code>: Nivel educativo asociado al cliente.</li>
 *   <li><code>imageUrl</code>: URL de la imagen del cliente (atributo transitorio).</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_cliente")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>id</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@OneToOne</code>: Define relaciones uno a uno con las entidades <code>User</code>, <code>Address</code> y <code>Carnet</code>.</li>
 *   <li><code>@ManyToOne</code>: Define una relación muchos a uno con la entidad <code>Education</code>.</li>
 *   <li><code>@JoinColumn(name = "...")</code>: Especifica las columnas que relacionan esta entidad con otras tablas.</li>
 *   <li><code>@Transient</code>: Indica que <code>imageUrl</code> no se persiste en la base de datos.</li>
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
 * Esta clase encapsula la información básica de un cliente, incluyendo sus datos
 * personales, dirección, nivel educativo y carnet asociado, facilitando su manejo
 * en el sistema.
 *
 * @author Vasquez, Huanca
 * @version 1.0
 * @since 27/10/2024
 */
@Table(name = "tb_cliente")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clie_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "clie_usuario_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "clie_direccion_id")
    private Address address;

    @Column(name = "clie_correo")
    private String email;

    @OneToOne
    @JoinColumn(name = "clie_carnet_id")
    private Carnet carnet;

    @ManyToOne
    @JoinColumn(name = "clie_nivel_educativo_id")
    private Education education;

    @Transient
    private String imageUrl;
}