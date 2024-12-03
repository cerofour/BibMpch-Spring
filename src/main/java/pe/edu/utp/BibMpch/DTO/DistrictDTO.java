package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.District;
import pe.edu.utp.BibMpch.model.Province;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representar distritos.
 *
 * Este DTO se utiliza para transferir datos relacionados con distritos
 * entre las distintas capas de la aplicación, como controladores y servicios.
 * También incluye información relacionada con provincias, regiones y países
 * asociados al distrito.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del distrito.</li>
 *   <li><code>districtName</code>: Nombre del distrito.</li>
 *   <li><code>provinceId</code>: Identificador único de la provincia asociada.</li>
 *   <li><code>provinceName</code>: Nombre de la provincia asociada.</li>
 *   <li><code>regionId</code>: Identificador único de la región asociada.</li>
 *   <li><code>regionName</code>: Nombre de la región asociada.</li>
 *   <li><code>countryId</code>: Identificador único del país asociado.</li>
 *   <li><code>countryName</code>: Nombre del país asociado.</li>
 *   <li><code>displayName</code>: Nombre completo del distrito con información jerárquica.</li>
 * </ul>
 *
 * <p><strong>Métodos adicionales:</strong></p>
 * <ul>
 *   <li><code>DistrictDTO(District district)</code>: Constructor que convierte una entidad <code>District</code> en un DTO.</li>
 *   <li><code>toEntity(Province province)</code>: Convierte este DTO en una entidad <code>District</code>.</li>
 *   <li><code>fromEntityList(List&lt;District&gt; districts)</code>: Convierte una lista de entidades <code>District</code> en una lista de DTOs.</li>
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
public class DistrictDTO {
    private Long id;
    private String districtName;
    private Long provinceId;
    private String provinceName;
    private Long regionId;
    private String regionName;
    private Short countryId;
    private String countryName;
    private String displayName;

    /**
     * Constructor que inicializa un DTO basado en una entidad <code>District</code>.
     *
     * @param district Entidad <code>District</code> para convertir en DTO.
     */
    public DistrictDTO(District district) {
        this.id = district.getId();
        this.districtName = district.getDistrictName();
        this.provinceId = district.getProvince().getId();
        this.provinceName = district.getProvince().getProvinceName();
        this.regionId = district.getProvince().getRegion().getId();
        this.regionName = district.getProvince().getRegion().getRegionName();
        this.countryId = district.getProvince().getRegion().getCountry().getId();
        this.countryName = district.getProvince().getRegion().getCountry().getCountryName();
        this.displayName = district.getDistrictName() + ", " + this.provinceName + ", " + this.regionName + ", " + this.countryName;
    }

    /**
     * Convierte este DTO en una entidad <code>District</code>.
     *
     * @param province Entidad <code>Province</code> asociada al distrito.
     * @return Una instancia de <code>District</code> basada en los datos del DTO.
     */
    public District toEntity(Province province) {
        return District.builder()
                .id(this.id)
                .districtName(this.districtName)
                .province(province)
                .build();
    }

    /**
     * Convierte una lista de entidades <code>District</code> en una lista de DTOs.
     *
     * @param districts Lista de entidades <code>District</code> a convertir.
     * @return Lista de DTOs generados a partir de las entidades.
     */
    public static List<DistrictDTO> fromEntityList(List<District> districts) {
        return districts.stream()
                .map(DistrictDTO::new)
                .collect(Collectors.toList());
    }
}
