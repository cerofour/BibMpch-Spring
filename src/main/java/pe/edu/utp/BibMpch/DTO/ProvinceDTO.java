package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Province;
import pe.edu.utp.BibMpch.model.Region;
import java.util.List;
import java.util.stream.Collectors;

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

    public ProvinceDTO(Province province) {
        this.id = province.getId();
        this.provinceName = province.getProvinceName();
        this.regionId = province.getRegion().getId();
        this.regionName = province.getRegion().getRegionName();
        this.countryId = province.getRegion().getCountry().getId();
        this.countryName = province.getRegion().getCountry().getCountryName();
        this.displayName = province.getProvinceName() + ", " + province.getRegion().getRegionName() + ", " + province.getRegion().getCountry().getCountryName();
    }
    public Province toEntity(Region region) {
        return Province.builder()
                .id(this.id)
                .provinceName(this.provinceName)
                .region(region)
                .build();
    }
    public static List<ProvinceDTO> fromEntityList(List<Province> provinces) {
        return provinces.stream()
                .map(ProvinceDTO::new)
                .collect(Collectors.toList());
    }
}
