package pe.edu.utp.BibMpch.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Status;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link Status}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link Status}.
 *
 * <p><strong>Métodos personalizados:</strong></p>
 * <ul>
 *   <li>{@link #findByStatusName(String)}: Busca un estado por su nombre.</li>
 *   <li>{@link #existsByStatusName(String)}: Verifica si existe un estado con el nombre proporcionado.</li>
 *   <li>{@link #deleteByStatusName(String)}: Elimina un estado por su nombre.</li>
 *   <li>{@link #activeStatus()}: Obtiene el estado "Activo" si existe; lanza una excepción si no se encuentra.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar consultas específicas
 * relacionadas con estados, además de utilizar los métodos estándar proporcionados por Spring Data.
 *
 * @author Vasquez, Llacsahuanga
 * @version 1.0
 * @since 29/10/2024
 */
public interface StatusRepository extends CrudRepository<Status, Short> {
    Optional<Status> findByStatusName(String statusName);
    boolean existsByStatusName(String statusName);
    void deleteByStatusName(String statusName);

    default Status activeStatus() {
        return findByStatusName("Activo")
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el estado Activo. Se ha realizado el llenado de tablas?"));
    }
}
