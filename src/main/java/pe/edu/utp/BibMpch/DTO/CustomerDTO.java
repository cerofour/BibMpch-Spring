package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Customer;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;
    private String userName;
    private String address;
    private String districtName;
    private String provinceName;
    private String regionName;
    private String countryName;
    private String email;
    private String code;
    private LocalDate carnetIssuanceDate;
    private LocalDate carnetExpirationDate;
    private String educationName;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.userName = customer.getUser().getName();
        this.address = customer.getAddress().getAddress();
        this.districtName = customer.getAddress().getDistrict().getDistrictName();
        this.provinceName = customer.getAddress().getDistrict().getProvince().getProvinceName();
        this.regionName = customer.getAddress().getDistrict().getProvince().getRegion().getRegionName();
        this.countryName = customer.getAddress().getDistrict().getProvince().getRegion().getCountry().getCountryName();
        this.email = customer.getEmail();
        this.code = customer.getCarnet().getCode();
        this.carnetIssuanceDate = customer.getCarnet().getCarnetIssuanceDate();
        this.carnetExpirationDate = customer.getCarnet().getCarnetExpirationDate();
        this.educationName = customer.getEducation().getEducationName();
    }
    public Customer toEntity() {
        return Customer.builder()
                .id(this.id)
                .email(this.email)
                .build();
    }
    public static List<CustomerDTO> fromEntityList(List<Customer> customers) {
        return customers.stream()
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
    }
}
