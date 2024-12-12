package pe.edu.utp.BibMpch.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserToClientDTO {
	Long id;
	String email;
	AddressDTO addressDTO;
	Short educationLevel;
}
