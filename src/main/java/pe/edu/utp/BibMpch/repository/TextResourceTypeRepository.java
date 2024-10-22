package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.TextResourceType;

import java.util.Optional;

public interface TextResourceTypeRepository extends CrudRepository<TextResourceType, Long> {
	Optional<TextResourceType> findByTypename(String typeName);
}
