package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
