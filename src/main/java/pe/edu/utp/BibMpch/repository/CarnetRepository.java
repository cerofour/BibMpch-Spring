package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Carnet;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link Carnet}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link Carnet}.
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas relacionadas con carnets, utilizando métodos prediseñados proporcionados por Spring Data.
 *
 * @author Vasquez
 * @version 1.0
 * @since 29/10/2024
 */
public interface CarnetRepository extends CrudRepository<Carnet, Long> {
}
