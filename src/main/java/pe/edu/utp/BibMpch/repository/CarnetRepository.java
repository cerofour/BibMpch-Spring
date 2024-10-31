package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Carnet;
import java.util.Optional;

public interface CarnetRepository extends CrudRepository<Carnet, Long> {
    Optional<Carnet> findByCode(String code);
}
