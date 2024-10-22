package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.TextResource;

public interface TextRepository extends CrudRepository<TextResource, Long> {
}
