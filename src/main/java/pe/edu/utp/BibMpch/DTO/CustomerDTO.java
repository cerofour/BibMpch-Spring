package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Customer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private Long user;
    private String name;
    private String pLastName;
    private String mLastName;
    private Long gender;
    private Long address;
    private String phoneNumber;
    private String email;
    private Long carnet;

    public CustomerDTO(Customer cu) {
        this.id = cu.getId();
        this.user = cu.getUser().getUserId();
        this.name = cu.getName();
        this.pLastName = cu.getPLastName();
        this.mLastName = cu.getMLastName();
        this.gender = cu.getGender().getId();
        this.address = cu.getAddress().getId();
        this.phoneNumber = cu.getPhoneNumber();
        this.email = cu.getEmail();
        this.carnet = cu.getCarnet().getId();
    }
}