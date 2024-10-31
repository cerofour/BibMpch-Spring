package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
