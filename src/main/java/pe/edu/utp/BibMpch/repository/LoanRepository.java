package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.CodeTextualResource;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.model.Loan;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link Loan}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link Loan}.
 *
 * <p><strong>Métodos personalizados:</strong></p>
 * <ul>
 *   <li>{@link #countByCodeTextualResource(CodeTextualResource)}: Cuenta la cantidad de préstamos asociados
 *       a un recurso textual específico.</li>
 *   <li>{@link #findByCustomer(Customer)}: Busca todos los préstamos realizados por un cliente específico.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar consultas específicas
 * relacionadas con préstamos, además de utilizar los métodos estándar proporcionados por Spring Data.
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
public interface LoanRepository extends CrudRepository<Loan, Long> {
    Long countByCodeTextualResource(CodeTextualResource codeTextualResource);
    List<Loan> findByCustomer(Customer customer0);
}
