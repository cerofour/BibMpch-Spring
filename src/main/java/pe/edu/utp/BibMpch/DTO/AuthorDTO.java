package pe.edu.utp.BibMpch.DTO;

import lombok.*;
import pe.edu.utp.BibMpch.model.Author;

@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class AuthorDTO {
	private String pseudoname;

	private String name;

	private String plastname;

	private String mlastname;

	public AuthorDTO(Author a) {
		this.pseudoname = a.getPseudoname();
		this.name = a.getName();
		this.plastname = a.getPLastName();
		this.mlastname = a.getMLastName();
	}
}
