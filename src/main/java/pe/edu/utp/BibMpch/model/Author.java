package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_autor")
@Entity
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auto_id")
	private Long id;

	@Column(name = "auto_seudonimo")
	private String pseudoname;

	@Column(name = "auto_nombre")
	private String name;

	@Column(name = "auto_apellido_paterno")
	private String pLastName;

	@Column(name = "auto_apellido_materno")
	private String mLastName;
}
