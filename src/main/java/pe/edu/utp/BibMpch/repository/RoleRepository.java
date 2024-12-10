package pe.edu.utp.BibMpch.repository;


import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.RegisterActions;
import pe.edu.utp.BibMpch.model.Roles;

import java.util.List;

public interface RoleRepository extends CrudRepository<Roles, Long> {
}
