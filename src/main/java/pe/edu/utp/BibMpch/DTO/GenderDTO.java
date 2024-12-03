package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Gender;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representar géneros.
 *
 * Este DTO se utiliza para transferir datos relacionados con géneros entre
 * las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del género.</li>
 *   <li><code>genderName</code>: Nombre del género.</li>
 * </ul>
 *
 * <p><strong>Métodos adicionales:</strong></p>
 * <ul>
 *   <li><code>GenderDTO(Gender gender)</code>: Constructor que convierte una entidad <code>Gender</code> en un DTO.</li>
 *   <li><code>toEntity()</code>: Convierte este DTO en una entidad <code>Gender</code>.</li>
 *   <li><code>fromEntityList(List&lt;Gender&gt; genders)</code>: Convierte una lista de entidades <code>Gender</code> en una lista de DTOs.</li>
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
 * Este DTO permite abstraer la información de los géneros y evitar la exposición directa de las entidades del modelo.
 *
 * @author Vasquez, Llacsahuanga
 * @version 1.0
 * @since 28/10/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenderDTO {
    private Short id;
    private String genderName;

    /**
     * Constructor que inicializa un DTO basado en una entidad <code>Gender</code>.
     *
     * @param gender Entidad <code>Gender</code> para convertir en DTO.
     */
    public GenderDTO(Gender gender) {
        this.id = gender.getId();
        this.genderName = gender.getGenderName();
    }

    /**
     * Convierte este DTO en una entidad <code>Gender</code>.
     *
     * @return Una instancia de <code>Gender</code> basada en los datos del DTO.
     */
    public Gender toEntity() {
        Gender gender = new Gender();
        gender.setId(this.id);
        gender.setGenderName(this.genderName);
        return gender;
    }

    /**
     * Convierte una lista de entidades <code>Gender</code> en una lista de DTOs.
     *
     * @param genders Lista de entidades <code>Gender</code> a convertir.
     * @return Lista de DTOs generados a partir de las entidades.
     */
    public static List<GenderDTO> fromEntityList(List<Gender> genders) {
        return genders.stream()
                .map(GenderDTO::new)
                .collect(Collectors.toList());
    }
}
