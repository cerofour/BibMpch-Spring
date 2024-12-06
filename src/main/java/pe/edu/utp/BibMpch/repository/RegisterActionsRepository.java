package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.RegisterActions;

import java.util.List;

public interface RegisterActionsRepository extends CrudRepository<RegisterActions, Long> {
    List<RegisterActions> findByUserId(Long id);
}
