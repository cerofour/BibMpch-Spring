package pe.edu.utp.BibMpch.DTO;

import lombok.*;
import pe.edu.utp.BibMpch.model.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;
    private Long district;
    private String address;

    public AddressDTO(Address ad) {
        this.id = ad.getId();
        this.district = ad.getDistrict().getId();
        this.address = ad.getAddress();
    }
}