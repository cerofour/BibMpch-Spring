package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * Entidad que representa una provincia en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_provincia</code> en la base de datos,
 * donde se almacenan los datos relacionados con las provincias.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único de la provincia.</li>
 *   <li><code>region</code>: Región asociada a la provincia.</li>
 *   <li><code>provinceName</code>: Nombre de la provincia.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_provincia")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>id</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@ManyToOne</code>: Define una relación muchos a uno con la entidad <code>Region</code>.</li>
 *   <li><code>@JoinColumn(name = "prov_region_id")</code>: Especifica la columna que relaciona esta entidad con la tabla de regiones.</li>
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
 * Esta clase encapsula la información básica de una provincia, incluyendo su relación con una región,
 * facilitando el manejo de estos datos en el sistema.
 *
 * @author Vasquez
 * @version 1.0
 * @since 27/10/2024
 */
@Table(name = "tb_provincia")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prov_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prov_region_id")
    private Region region;

    @Column(name = "prov_nombre")
    private String provinceName;
}
