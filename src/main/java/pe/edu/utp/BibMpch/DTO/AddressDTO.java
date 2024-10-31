package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Address;
import pe.edu.utp.BibMpch.model.District;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private Long id;
    private String address;
    private Long districtId;
    private String districtName;
    private Long provinceId;
    private String provinceName;
    private Long regionId;
    private String regionName;
    private Short countryId;
    private String countryName;
    private String displayName;

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.districtId = address.getDistrict().getId();
        this.districtName = address.getDistrict().getDistrictName();
        this.provinceId = address.getDistrict().getProvince().getId();
        this.provinceName = address.getDistrict().getProvince().getProvinceName();
        this.regionId = address.getDistrict().getProvince().getRegion().getId();
        this.regionName = address.getDistrict().getProvince().getRegion().getRegionName();
        this.countryId = address.getDistrict().getProvince().getRegion().getCountry().getId();
        this.countryName = address.getDistrict().getProvince().getRegion().getCountry().getCountryName();
        this.displayName = address.getAddress() + ", " + this.districtName + ", " + this.provinceName + ", " + this.regionName + ", " + this.countryName;
    }
    public Address toEntity(District district) {
        return Address.builder()
                .id(this.id)
                .address(this.address)
                .district(district)
                .build();
    }
    public static List<AddressDTO> fromEntityList(List<Address> addresses) {
        return addresses.stream()
                .map(AddressDTO::new)
                .collect(Collectors.toList());
    }
}
