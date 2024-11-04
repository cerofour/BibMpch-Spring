package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_editorial")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Editorial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "edit_id")
	private Long id;

	@Column(name = "edit_nombre")
	private String name;
}
