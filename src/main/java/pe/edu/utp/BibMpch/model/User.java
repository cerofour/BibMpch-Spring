package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usua_id")
	private Long userId;

	@Column(name = "usua_rol_usuario_id")
	private Short roleId;

	@Column(name = "usua_tipo_documento_id")
	private Short documentTypeId;

	@Column(name = "usua_documento")
	private String document;

	@Column(name = "usua_psk")
	private String psk;

	@Column(name = "usua_nombre")
	private String name;

	@Column(name = "usua_apellido_paterno")
	private String pLastName;

	@Column(name = "usua_apellido_materno")
	private String mLastName;

	@Column(columnDefinition = "BPCHAR(9)", name = "usua_telefono")
	private String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "usua_genero_id")
	private Gender gender;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return psk;
	}

	@Override
	public String getUsername() {
		return getDocument();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return true; // UserDetails.super.isEnabled();
	}
}
