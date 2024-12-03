package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Status;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representar estados.
 *
 * Este DTO se utiliza para transferir datos relacionados con estados
 * entre las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del estado.</li>
 *   <li><code>statusName</code>: Nombre descriptivo del estado.</li>
 *   <li><code>isActive</code>: Indica si el estado está activo.</li>
 * </ul>
 *
 * <p><strong>Métodos adicionales:</strong></p>
 * <ul>
 *   <li><code>StatusDTO(Status status)</code>: Constructor que convierte una entidad <code>Status</code> en un DTO.</li>
 *   <li><code>toEntity()</code>: Convierte este DTO en una entidad <code>Status</code>.</li>
 *   <li><code>fromEntityList(List&lt;Status&gt; statuses)</code>: Convierte una lista de entidades <code>Status</code> en una lista de DTOs.</li>
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
 * Este DTO encapsula la información de los estados, facilitando su transferencia
 * y manipulación en la aplicación.
 *
 * @see pe.edu.utp.BibMpch.model.Status
 * @see java.util.List
 * @see java.util.stream.Collectors
 *
 * @author Vasquez, Llacsahuanga
 * @version 1.0
 * @since 28/10/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusDTO {
    private Short id;
    //NotNull y Size
    private String statusName;
    private boolean isActive;

    /**
     * Constructor que inicializa un DTO basado en una entidad <code>Status</code>.
     *
     * @param status Entidad <code>Status</code> para convertir en DTO.
     */
    public StatusDTO(Status status) {
        this.id = status.getId();
        this.statusName = status.getStatusName();
        this.isActive = status.isActive();
    }

    /**
     * Convierte este DTO en una entidad <code>Status</code>.
     *
     * @return Una instancia de <code>Status</code> basada en los datos del DTO.
     */
    public Status toEntity() {
        Status status = new Status();
        status.setId(this.id);
        status.setStatusName(this.statusName);
        status.setActive(this.isActive);
        return status;
    }

    /**
     * Convierte una lista de entidades <code>Status</code> en una lista de DTOs.
     *
     * @param statuses Lista de entidades <code>Status</code> a convertir.
     * @return Lista de DTOs generados a partir de las entidades.
     */
    public static List<StatusDTO> fromEntityList(List<Status> statuses) {
        return statuses.stream()
                .map(StatusDTO::new)
                .collect(Collectors.toList());
    }
}