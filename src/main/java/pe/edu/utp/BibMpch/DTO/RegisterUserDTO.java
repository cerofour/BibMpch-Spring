package pe.edu.utp.BibMpch.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDTO {
	private String username; // user-id
	private String psk;
}