package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.CodeTextualResource;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.model.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {
    Long countByCodeTextualResource(CodeTextualResource codeTextualResource);
    List<Loan> findByCustomer(Customer customer0);
}
