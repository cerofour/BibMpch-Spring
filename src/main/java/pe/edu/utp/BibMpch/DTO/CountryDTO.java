package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Country;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {
    private Short id;
    private String countryName;

    public CountryDTO(Country co) {
        this.id = co.getId();
        this.countryName = co.getCountryName();
    }
}