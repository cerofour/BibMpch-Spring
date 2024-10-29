package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
