package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.LoanType;

public interface LoanTypeRepository extends CrudRepository<LoanType, Long> {
}
