package pe.edu.utp.BibMpch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

	@Column(name = "auto_nombre")
	private String name;

	@Column(name = "auto_apellido_paterno")
	private String pLastName;

	@Column(name = "auto_apellido_materno")
	private String mLastName;
}
