package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Editorial;

import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link Editorial}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link Editorial}.
 *
 * <p><strong>Métodos personalizados:</strong></p>
 * <ul>
 *   <li>{@link #findByName(String)}: Busca una editorial por su nombre.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas específicas relacionadas con editoriales, además de utilizar los métodos estándar
 * proporcionados por Spring Data.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 22/10/2024
 */
public interface EditorialRepository extends CrudRepository<Editorial, Long> {
	Optional<Editorial> findByName(String name);
}
