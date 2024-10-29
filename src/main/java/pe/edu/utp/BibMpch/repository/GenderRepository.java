package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Gender;

public interface GenderRepository extends CrudRepository<Gender, Long> {
}
