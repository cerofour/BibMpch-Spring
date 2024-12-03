package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.User;

import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link User}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link User}.
 *
 * <p><strong>Métodos personalizados:</strong></p>
 * <ul>
 *   <li>{@link #findByDocument(String)}: Busca un usuario por su documento.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas específicas relacionadas con usuarios, además de utilizar los métodos estándar
 * proporcionados por Spring Data.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByDocument(String username);
}
