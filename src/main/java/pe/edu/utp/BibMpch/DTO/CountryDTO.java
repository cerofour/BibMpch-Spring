package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Country;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDTO {
    private Short id;
    private String countryName;

    public CountryDTO(Country country) {
        this.id = country.getId();
        this.countryName = country.getCountryName();
    }
    public Country toEntity() {
        Country country = new Country();
        country.setId(this.id);
        country.setCountryName(this.countryName);
        return country;
    }
    public static List<CountryDTO> fromEntityList(List<Country> countries) {
        return countries.stream()
                .map(CountryDTO::new)
                .collect(Collectors.toList());
    }
}
