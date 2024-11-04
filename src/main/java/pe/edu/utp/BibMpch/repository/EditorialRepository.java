package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Editorial;

import java.util.Optional;

public interface EditorialRepository extends CrudRepository<Editorial, Long> {
	Optional<Editorial> findByName(String name);
}
