package pe.edu.utp.BibMpch.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Data Transfer Object (DTO) para representar textos.
 *
 * Este DTO se utiliza para transferir datos relacionados con textos entre
 * las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>title</code>: Título del texto.</li>
 *   <li><code>publicationDate</code>: Fecha de publicación del texto.</li>
 *   <li><code>numPages</code>: Número de páginas del texto.</li>
 *   <li><code>edition</code>: Número de edición del texto.</li>
 *   <li><code>volume</code>: Volumen del texto.</li>
 *   <li><code>baseCode</code>: Código base único del texto.</li>
 *   <li><code>editorialId</code>: Identificador único de la editorial asociada.</li>
 *   <li><code>typeId</code>: Identificador único del tipo de texto.</li>
 *   <li><code>stock</code>: Cantidad de copias disponibles en el inventario.</li>
 *   <li><code>available</code>: Indica si el texto está disponible.</li>
 *   <li><code>authors</code>: Conjunto de identificadores de los autores del texto.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@RequiredArgsConstructor</code>: Genera un constructor con los atributos finales requeridos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los atributos.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 *   <li><code>@ToString</code>: Genera un método <code>toString</code> personalizado.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este DTO encapsula la información básica de un texto, incluyendo detalles de
 * identificación, características y relaciones con otras entidades como autores
 * y editoriales.
 *
 * @author Llacsahuanga, Huanca
 * @version 1.0
 * @since 22/10/2024
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TextDTO {
	private String title;
	private LocalDate publicationDate;
	private Short numPages;
	private Short edition;
	private Short volume;

	private String baseCode;

	private Long editorialId;
	private Long typeId;

	private Short stock;

	private Boolean available;

	private Set<Long> authors;
}
