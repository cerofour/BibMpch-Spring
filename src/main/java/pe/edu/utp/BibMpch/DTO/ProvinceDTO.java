package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Province;
import pe.edu.utp.BibMpch.model.Region;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representar provincias.
 *
 * Este DTO se utiliza para transferir datos relacionados con provincias
 * entre las distintas capas de la aplicación, como controladores y servicios.
 * También incluye información relacionada con regiones y países asociados.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único de la provincia.</li>
 *   <li><code>provinceName</code>: Nombre de la provincia.</li>
 *   <li><code>regionId</code>: Identificador único de la región asociada.</li>
 *   <li><code>regionName</code>: Nombre de la región asociada.</li>
 *   <li><code>countryId</code>: Identificador único del país asociado.</li>
 *   <li><code>countryName</code>: Nombre del país asociado.</li>
 *   <li><code>displayName</code>: Nombre completo de la provincia con información jerárquica.</li>
 * </ul>
 *
 * <p><strong>Métodos adicionales:</strong></p>
 * <ul>
 *   <li><code>ProvinceDTO(Province province)</code>: Constructor que convierte una entidad <code>Province</code> en un DTO.</li>
 *   <li><code>toEntity(Region region)</code>: Convierte este DTO en una entidad <code>Province</code>.</li>
 *   <li><code>fromEntityList(List&lt;Province&gt; provinces)</code>: Convierte una lista de entidades <code>Province</code> en una lista de DTOs.</li>
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
 * Este DTO encapsula la información de las provincias y sus relaciones jerárquicas,
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
public class ProvinceDTO {
    private Long id;
    private String provinceName;
    private Long regionId;
    private String regionName;
    private Short countryId;
    private String countryName;
    private String displayName;

    /**
     * Constructor que inicializa un DTO basado en una entidad <code>Province</code>.
     *
     * @param province Entidad <code>Province</code> para convertir en DTO.
     */
    public ProvinceDTO(Province province) {
        this.id = province.getId();
        this.provinceName = province.getProvinceName();
        this.regionId = province.getRegion().getId();
        this.regionName = province.getRegion().getRegionName();
        this.countryId = province.getRegion().getCountry().getId();
        this.countryName = province.getRegion().getCountry().getCountryName();
        this.displayName = province.getProvinceName() + ", " + province.getRegion().getRegionName() + ", " + province.getRegion().getCountry().getCountryName();
    }

    /**
     * Convierte este DTO en una entidad <code>Province</code>.
     *
     * @param region Entidad <code>Region</code> asociada a la provincia.
     * @return Una instancia de <code>Province</code> basada en los datos del DTO.
     */
    public Province toEntity(Region region) {
        return Province.builder()
                .id(this.id)
                .provinceName(this.provinceName)
                .region(region)
                .build();
    }

    /**
     * Convierte una lista de entidades <code>Province</code> en una lista de DTOs.
     *
     * @param provinces Lista de entidades <code>Province</code> a convertir.
     * @return Lista de DTOs generados a partir de las entidades.
     */
    public static List<ProvinceDTO> fromEntityList(List<Province> provinces) {
        return provinces.stream()
                .map(ProvinceDTO::new)
                .collect(Collectors.toList());
    }
}
