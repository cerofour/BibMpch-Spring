package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Education;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representar niveles de educación.
 *
 * Este DTO se utiliza para transferir datos relacionados con niveles de educación
 * entre las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del nivel educativo.</li>
 *   <li><code>educationName</code>: Nombre del nivel educativo.</li>
 * </ul>
 *
 * <p><strong>Métodos adicionales:</strong></p>
 * <ul>
 *   <li><code>EducationDTO(Education education)</code>: Constructor que convierte una entidad <code>Education</code> en un DTO.</li>
 *   <li><code>toEntity()</code>: Convierte este DTO en una entidad <code>Education</code>.</li>
 *   <li><code>fromEntityList(List&lt;Education&gt; educations)</code>: Convierte una lista de entidades <code>Education</code> en una lista de DTOs.</li>
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
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationDTO {
    private Short id;
    private String educationName;

    /**
     * Constructor que inicializa un DTO basado en una entidad <code>Education</code>.
     *
     * @param education Entidad <code>Education</code> para convertir en DTO.
     */
    public EducationDTO(Education education) {
        this.id = education.getId();
        this.educationName = education.getEducationName();
    }

    /**
     * Convierte este DTO en una entidad <code>Education</code>.
     *
     * @return Una instancia de <code>Education</code> basada en los datos del DTO.
     */
    public Education toEntity() {
        Education education = new Education();
        education.setId(this.id);
        education.setEducationName(this.educationName);
        return education;
    }

    /**
     * Convierte una lista de entidades <code>Education</code> en una lista de DTOs.
     *
     * @param educations Lista de entidades <code>Education</code> a convertir.
     * @return Lista de DTOs generados a partir de las entidades.
     */
    public static List<EducationDTO> fromEntityList(List<Education> educations) {
        return educations.stream()
                .map(EducationDTO::new)
                .collect(Collectors.toList());
    }
}
