package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa un usuario en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_usuario</code> en la base de datos,
 * donde se almacenan los datos relacionados con los usuarios.
 * También implementa la interfaz <code>UserDetails</code> para la integración con
 * Spring Security.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>userId</code>: Identificador único del usuario.</li>
 *   <li><code>roleId</code>: Identificador del rol asignado al usuario.</li>
 *   <li><code>documentTypeId</code>: Identificador del tipo de documento del usuario.</li>
 *   <li><code>document</code>: Documento de identidad del usuario.</li>
 *   <li><code>psk</code>: Contraseña del usuario.</li>
 *   <li><code>name</code>: Nombre del usuario.</li>
 *   <li><code>pLastName</code>: Apellido paterno del usuario.</li>
 *   <li><code>mLastName</code>: Apellido materno del usuario.</li>
 *   <li><code>phoneNumber</code>: Número de teléfono del usuario.</li>
 *   <li><code>gender</code>: Género asociado al usuario.</li>
 * </ul>
 *
 * <p><strong>Métodos de la interfaz <code>UserDetails</code>:</strong></p>
 * <ul>
 *   <li><code>getAuthorities()</code>: Devuelve una lista vacía, ya que no se implementan roles específicos.</li>
 *   <li><code>getPassword()</code>: Devuelve la contraseña del usuario.</li>
 *   <li><code>getUsername()</code>: Devuelve el documento del usuario como nombre de usuario.</li>
 *   <li><code>isAccountNonExpired()</code>: Siempre devuelve <code>true</code>.</li>
 *   <li><code>isAccountNonLocked()</code>: Siempre devuelve <code>true</code>.</li>
 *   <li><code>isCredentialsNonExpired()</code>: Siempre devuelve <code>true</code>.</li>
 *   <li><code>isEnabled()</code>: Siempre devuelve <code>true</code>.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_usuario")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>userId</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@ManyToOne</code>: Define una relación muchos a uno con la entidad <code>Gender</code>.</li>
 *   <li><code>@JoinColumn(name = "...")</code>: Especifica la columna que relaciona esta entidad con la tabla de géneros.</li>
 *   <li><code>@Column(name = "...")</code>: Mapea atributos de la clase a columnas específicas de la tabla.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 *   <li><code>@ToString</code>: Genera un método <code>toString</code> personalizado.</li>
 *   <li><code>@NoArgsConstructor</code>: Genera un constructor sin argumentos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los argumentos.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Esta clase encapsula la información básica de un usuario, incluyendo sus credenciales,
 * datos personales y su integración con el sistema de seguridad de Spring.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
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
