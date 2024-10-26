package pe.edu.utp.BibMpch.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
	private String document;
	private Short documentTypeId;
	private Short roleId;
	private String psk;
}
