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
    private Long address;
    private String email;
    private Long carnet;
    private Short education;

    public CustomerDTO(Customer cu) {
        this.id = cu.getId();
        this.user = cu.getUser().getUserId();
        this.address = cu.getAddress().getId();
        this.email = cu.getEmail();
        this.carnet = cu.getCarnet().getId();
        this.education = cu.getEducation().getId();
    }
}