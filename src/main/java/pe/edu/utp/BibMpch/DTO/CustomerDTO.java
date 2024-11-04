package pe.edu.utp.BibMpch.DTO;

import lombok.*;
import pe.edu.utp.BibMpch.model.Customer;

/**
 * The creation of a customer also creates an User and a Carnet. There is no other way
 * of creating a Carnet other than that, so this DTO should receive all the necessary data
 * to create, the User, the Client and the Carnet.
 */
@Data
@Builder
@AllArgsConstructor
@ToString
public class CustomerDTO {
    private UserDTO userData;
    private AddressDTO addressData;
    private Short educationLevelId;
    private String email;
}