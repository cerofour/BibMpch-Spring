package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.model.Region;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representar regiones.
 *
 * Este DTO se utiliza para transferir datos relacionados con regiones
 * entre las distintas capas de la aplicación, como controladores y servicios.
 * También incluye información relacionada con el país asociado a la región.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único de la región.</li>
 *   <li><code>regionName</code>: Nombre de la región.</li>
 *   <li><code>countryId</code>: Identificador único del país asociado.</li>
 *   <li><code>countryName</code>: Nombre del país asociado.</li>
 *   <li><code>displayName</code>: Nombre completo de la región con información jerárquica.</li>
 * </ul>
 *
 * <p><strong>Métodos adicionales:</strong></p>
 * <ul>
 *   <li><code>RegionDTO(Region region)</code>: Constructor que convierte una entidad <code>Region</code> en un DTO.</li>
 *   <li><code>toEntity(Country country)</code>: Convierte este DTO en una entidad <code>Region</code>.</li>
 *   <li><code>fromEntityList(List&lt;Region&gt; regions)</code>: Convierte una lista de entidades <code>Region</code> en una lista de DTOs.</li>
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
 * Este DTO encapsula la información de las regiones y sus relaciones jerárquicas,
 * facilitando su transferencia y manipulación en la aplicación.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionDTO {
    private Long id;
    private String regionName;
    private Short countryId;
    private String countryName;
    private String displayName;

    /**
     * Constructor que inicializa un DTO basado en una entidad <code>Region</code>.
     *
     * @param region Entidad <code>Region</code> para convertir en DTO.
     */
    public RegionDTO(Region region) {
        this.id = region.getId();
        this.regionName = region.getRegionName();
        this.countryId = region.getCountry().getId();
        this.countryName = region.getCountry().getCountryName();
        this.displayName = region.getRegionName() + ", " + region.getCountry().getCountryName();
    }

    /**
     * Convierte este DTO en una entidad <code>Region</code>.
     *
     * @param country Entidad <code>Country</code> asociada a la región.
     * @return Una instancia de <code>Region</code> basada en los datos del DTO.
     */
    public Region toEntity(Country country) {
        return Region.builder()
                .id(this.id)
                .regionName(this.regionName)
                .country(country)
                .build();
    }

    /**
     * Convierte una lista de entidades <code>Region</code> en una lista de DTOs.
     *
     * @param regions Lista de entidades <code>Region</code> a convertir.
     * @return Lista de DTOs generados a partir de las entidades.
     */
    public static List<RegionDTO> fromEntityList(List<Region> regions) {
        return regions.stream()
                .map(RegionDTO::new)
                .collect(Collectors.toList());
    }
}
