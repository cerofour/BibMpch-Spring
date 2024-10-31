package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Education;

import java.util.Optional;

public interface EducationRepository extends CrudRepository<Education, Short> {
    Optional<Education> findByEducationName(String educationName);
}
