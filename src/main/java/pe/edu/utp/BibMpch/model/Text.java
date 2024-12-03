package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

/**
 * Entidad que representa un recurso textual en el sistema.
 *
 * Esta clase mapea la tabla <code>tb_recurso_textual</code> en la base de datos,
 * donde se almacenan los datos relacionados con los recursos textuales.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del recurso textual.</li>
 *   <li><code>type</code>: Tipo de recurso textual asociado.</li>
 *   <li><code>editorial</code>: Editorial asociada al recurso textual.</li>
 *   <li><code>title</code>: Título del recurso textual.</li>
 *   <li><code>publicationDate</code>: Fecha de publicación del recurso textual.</li>
 *   <li><code>pages</code>: Número de páginas del recurso textual.</li>
 *   <li><code>edition</code>: Número de edición del recurso textual.</li>
 *   <li><code>volume</code>: Volumen del recurso textual.</li>
 *   <li><code>available</code>: Indica si el recurso textual está disponible.</li>
 *   <li><code>baseCode</code>: Código base único del recurso textual.</li>
 *   <li><code>authors</code>: Conjunto de autores asociados al recurso textual.</li>
 *   <li><code>stock</code>: Cantidad de copias disponibles en inventario (atributo transitorio).</li>
 *   <li><code>imageUrl</code>: URL de la imagen del recurso textual (atributo transitorio).</li>
 * </ul>
 *
 * <p><strong>Anotaciones de JPA:</strong></p>
 * <ul>
 *   <li><code>@Entity</code>: Marca esta clase como una entidad de JPA.</li>
 *   <li><code>@Table(name = "tb_recurso_textual")</code>: Especifica la tabla de la base de datos asociada.</li>
 *   <li><code>@Id</code>: Indica que el atributo <code>id</code> es la clave primaria.</li>
 *   <li><code>@GeneratedValue(strategy = GenerationType.IDENTITY)</code>: Define la estrategia de generación automática del identificador.</li>
 *   <li><code>@OneToOne</code>: Define relaciones uno a uno con <code>TextResourceType</code> y <code>Editorial</code>.</li>
 *   <li><code>@ManyToMany</code>: Define una relación de muchos a muchos con la entidad <code>Author</code>.</li>
 *   <li><code>@JoinTable</code>: Especifica la tabla intermedia para la relación de muchos a muchos.</li>
 *   <li><code>@Transient</code>: Indica que <code>stock</code> e <code>imageUrl</code> no se persisten en la base de datos.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 *   <li><code>@NoArgsConstructor</code>: Genera un constructor sin argumentos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los argumentos.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Esta clase encapsula la información básica de un recurso textual, incluyendo sus metadatos,
 * autores asociados y atributos transitorios, facilitando su manejo en el sistema.
 *
 * @author Llacsahuanga, Huanca
 * @version 1.0
 * @since 22/10/2024
 */
@Entity
@Data
@Builder
@Table(name = "tb_recurso_textual")
@AllArgsConstructor
@NoArgsConstructor
public class Text {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rete_id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "rete_tipo_texto_id")
	private TextResourceType type;

	@OneToOne
	@JoinColumn(name = "rete_editorial_id")
	private Editorial editorial;

	@Column(name = "rete_titulo")
	private String title;

	@Column(name = "rete_fec_publicacion")
	private LocalDate publicationDate;

	@Column(name = "rete_num_paginas")
	private Short pages;

	@Column(name = "rete_edicion")
	private Short edition;

	@Column(name = "rete_volumen")
	private Short volume;

	@Column(name = "rete_activo")
	private Boolean available;

	@Column(name = "rete_codigo_base")
	private String baseCode;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "tb_recurso_textual_autor",
			joinColumns = @JoinColumn(name = "reau_recurso_textual_id"),
			inverseJoinColumns = @JoinColumn(name = "reau_autor_id"))
	private Set<Author> authors;

	@Transient
	private Short stock;

	@Transient
	private String imageUrl;
}
