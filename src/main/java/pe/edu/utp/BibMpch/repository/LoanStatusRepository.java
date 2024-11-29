package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.LoanStatus;

public interface LoanStatusRepository extends CrudRepository<LoanStatus, Long> {
}
