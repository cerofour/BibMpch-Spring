package pe.edu.utp.BibMpch.authorization;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tb_rol_usuario")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rolu_id", nullable = false)
	private Short id;

	@Column(name = "rolu_nombre", unique = true, nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRoleEnum name;
}
