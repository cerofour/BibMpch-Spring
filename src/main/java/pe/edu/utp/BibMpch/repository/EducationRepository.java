package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Education;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link Education}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link Education}.
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas relacionadas con niveles educativos, utilizando los métodos prediseñados
 * proporcionados por Spring Data.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
public interface EducationRepository extends CrudRepository<Education, Short> {
}
