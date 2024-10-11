package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
