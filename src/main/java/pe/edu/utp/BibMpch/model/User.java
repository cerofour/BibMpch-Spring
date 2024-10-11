package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "id_usuario")
	private Long id;

	@Column(nullable = false, name = "psk")
	private String psk;

	@Column(name = "username")
	private String username;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return psk;
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
