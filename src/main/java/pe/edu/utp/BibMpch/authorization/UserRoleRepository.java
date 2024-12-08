package pe.edu.utp.BibMpch.authorization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Short> {
	Optional<UserRole> findByName(UserRoleEnum name);
}
