package pe.edu.utp.BibMpch.DTO;

import lombok.*;
import pe.edu.utp.BibMpch.model.Author;

/**
 * Data Transfer Object (DTO) para representar autores.
 *
 * Este DTO se utiliza para transferir datos relacionados con autores
 * entre las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>name</code>: Nombre del autor.</li>
 *   <li><code>plastname</code>: Apellido paterno del autor.</li>
 *   <li><code>mlastname</code>: Apellido materno del autor.</li>
 * </ul>
 *
 * <p><strong>Métodos adicionales:</strong></p>
 * <ul>
 *   <li><code>AuthorDTO(Author a)</code>: Constructor que convierte una entidad <code>Author</code> en un DTO.</li>
 *   <li><code>toEntity()</code>: Convierte este DTO en una entidad <code>Author</code>.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 *   <li><code>@NoArgsConstructor</code>: Genera un constructor sin argumentos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los argumentos.</li>
 *   <li><code>@ToString</code>: Genera el método <code>toString</code> personalizado.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 26/10/2024
 */
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class AuthorDTO {

	private String name;

	private String plastname;

	private String mlastname;

	/**
	 * Constructor que inicializa un DTO basado en una entidad <code>Author</code>.
	 *
	 * @param a Entidad <code>Author</code> para convertir en DTO.
	 */
	public AuthorDTO(Author a) {
		this.name = a.getName();
		this.plastname = a.getPLastName();
		this.mlastname = a.getMLastName();
	}

	/**
	 * Convierte este DTO en una entidad <code>Author</code>.
	 *
	 * @return Una instancia de <code>Author</code> basada en los datos del DTO.
	 */
	public Author toEntity() {
		return Author.builder()
				.name(this.name)
				.pLastName(this.plastname)
				.mLastName(this.mlastname)
				.build();
	}
}
