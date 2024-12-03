package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * Entidad que representa una región en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_region</code> en la base de datos,
 * donde se almacenan los datos relacionados con las regiones.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único de la región.</li>
 *   <li><code>country</code>: País asociado a la región.</li>
 *   <li><code>regionName</code>: Nombre de la región.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_region")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>id</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@ManyToOne</code>: Define una relación muchos a uno con la entidad <code>Country</code>.</li>
 *   <li><code>@JoinColumn(name = "regi_pais_id")</code>: Especifica la columna que relaciona esta entidad con la tabla de países.</li>
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
 * Esta clase encapsula la información básica de una región, incluyendo su relación con un país,
 * facilitando el manejo de estos datos en el sistema.
 *
 * @author Vasquez
 * @version 1.0
 * @since 27/10/2024
 */
@Table(name = "tb_region")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regi_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "regi_pais_id")
    private Country country;

    @Column(name = "regi_nombre")
    private String regionName;
}
