package pe.edu.utp.BibMpch.DTO;

import lombok.*;

@Data
@Builder
@ToString
public class LoginUserDTO {
	private String document;
	private String psk;
}
