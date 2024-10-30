package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Status;
import java.util.Optional;

public interface StatusRepository extends CrudRepository<Status, Short> {
    Optional<Status> findByStatusName(String statusName);
    boolean existsByStatusName(String statusName);
    void deleteByStatusName(String statusName);
}
