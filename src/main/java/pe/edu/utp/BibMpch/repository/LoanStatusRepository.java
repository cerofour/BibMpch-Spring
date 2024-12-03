package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.LoanStatus;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link LoanStatus}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link LoanStatus}.
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas relacionadas con estados de préstamos, utilizando los métodos prediseñados
 * proporcionados por Spring Data.
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
public interface LoanStatusRepository extends CrudRepository<LoanStatus, Long> {
}
