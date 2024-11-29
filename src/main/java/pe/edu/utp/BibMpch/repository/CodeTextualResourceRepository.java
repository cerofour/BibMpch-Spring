package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.CodeTextualResource;

import java.util.List;
import java.util.Optional;

public interface CodeTextualResourceRepository extends CrudRepository<CodeTextualResource, Long> {
    Optional<CodeTextualResource> findByBaseCodeAndExemplaryCode(String baseCode, Integer exemplaryCode);
    List<CodeTextualResource> findByBaseCode(String baseCode);
}
