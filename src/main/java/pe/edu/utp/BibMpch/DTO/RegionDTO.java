package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.model.Region;
import java.util.List;
import java.util.stream.Collectors;

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

    public RegionDTO(Region region) {
        this.id = region.getId();
        this.regionName = region.getRegionName();
        this.countryId = region.getCountry().getId();
        this.countryName = region.getCountry().getCountryName();
        this.displayName = region.getRegionName() + ", " + region.getCountry().getCountryName();
    }
    public Region toEntity(Country country) {
        return Region.builder()
                .id(this.id)
                .regionName(this.regionName)
                .country(country)
                .build();
    }
    public static List<RegionDTO> fromEntityList(List<Region> regions) {
        return regions.stream()
                .map(RegionDTO::new)
                .collect(Collectors.toList());
    }
}
