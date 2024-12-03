package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.TextResourceType;

import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link TextResourceType}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link TextResourceType}.
 *
 * <p><strong>Métodos personalizados:</strong></p>
 * <ul>
 *   <li>{@link #findByTypename(String)}: Busca un tipo de recurso textual por su nombre.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas relacionadas con tipos de recursos textuales, además de utilizar los métodos estándar
 * proporcionados por Spring Data.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 22/10/2024
 */
public interface TextResourceTypeRepository extends CrudRepository<TextResourceType, Long> {
	Optional<TextResourceType> findByTypename(String typeName);
}
