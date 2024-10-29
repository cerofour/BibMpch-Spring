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
    private String user;
    private String name;
    private String pLastName;
    private String mLastName;
    private String gender;
    private String address;
    private String phoneNumber;
    private String email;
    private String card;

    public CustomerDTO(Customer cu) {
        this.id = cu.getId();
        this.user = cu.getUser().getDocument();
        this.name = cu.getName();
        this.pLastName = cu.getPLastName();
        this.mLastName = cu.getMLastName();
        this.gender = cu.getGender().getGenderName();
        this.address = cu.getAddress().getAddress();
        this.phoneNumber = cu.getPhoneNumber();
        this.email = cu.getEmail();
        this.card = cu.getCard().getCode();
    }
}