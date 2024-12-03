package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Country;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representar países.
 *
 * Este DTO se utiliza para transferir datos relacionados con países
 * entre las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del país.</li>
 *   <li><code>countryName</code>: Nombre del país.</li>
 * </ul>
 *
 * <p><strong>Métodos adicionales:</strong></p>
 * <ul>
 *   <li><code>CountryDTO(Country country)</code>: Constructor que convierte una entidad <code>Country</code> en un DTO.</li>
 *   <li><code>toEntity()</code>: Convierte este DTO en una entidad <code>Country</code>.</li>
 *   <li><code>fromEntityList(List&lt;Country&gt; countries)</code>: Convierte una lista de entidades <code>Country</code> en una lista de DTOs.</li>
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
 * @author Llacsahuanga, Vasquez
 * @version 1.0
 * @since 28/10/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDTO {
    private Short id;
    private String countryName;

    /**
     * Constructor que inicializa un DTO basado en una entidad <code>Country</code>.
     *
     * @param country Entidad <code>Country</code> para convertir en DTO.
     */
    public CountryDTO(Country country) {
        this.id = country.getId();
        this.countryName = country.getCountryName();
    }

    /**
     * Convierte este DTO en una entidad <code>Country</code>.
     *
     * @return Una instancia de <code>Country</code> basada en los datos del DTO.
     */
    public Country toEntity() {
        Country country = new Country();
        country.setId(this.id);
        country.setCountryName(this.countryName);
        return country;
    }

    /**
     * Convierte una lista de entidades <code>Country</code> en una lista de DTOs.
     *
     * @param countries Lista de entidades <code>Country</code> a convertir.
     * @return Lista de DTOs generados a partir de las entidades.
     */
    public static List<CountryDTO> fromEntityList(List<Country> countries) {
        return countries.stream()
                .map(CountryDTO::new)
                .collect(Collectors.toList());
    }
}
