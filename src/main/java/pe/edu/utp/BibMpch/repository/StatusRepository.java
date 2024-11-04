package pe.edu.utp.BibMpch.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Status;
import java.util.Optional;

public interface StatusRepository extends CrudRepository<Status, Short> {
    Optional<Status> findByStatusName(String statusName);
    boolean existsByStatusName(String statusName);
    void deleteByStatusName(String statusName);

    default Status activeStatus() {
        return findByStatusName("Activo")
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el estado Activo. Se ha realizado el llenado de tablas?"));
    }
}
