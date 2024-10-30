package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.District;
import pe.edu.utp.BibMpch.model.Province;

import java.util.List;
import java.util.stream.Collectors;

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
    public District toEntity(Province province) {
        return District.builder()
                .id(this.id)
                .districtName(this.districtName)
                .province(province)
                .build();
    }
    public static List<DistrictDTO> fromEntityList(List<District> districts) {
        return districts.stream()
                .map(DistrictDTO::new)
                .collect(Collectors.toList());
    }
}
