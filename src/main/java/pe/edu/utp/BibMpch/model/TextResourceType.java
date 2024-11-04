package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_tipo_texto")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TextResourceType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tite_id")
	private Long typeId;

	@Column(name = "tite_tipo")
	private String typename;
}
