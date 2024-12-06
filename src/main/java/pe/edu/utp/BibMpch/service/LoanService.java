package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.LoanDTO;
import pe.edu.utp.BibMpch.exceptions.ResourceNotFoundException;
import pe.edu.utp.BibMpch.model.CodeTextualResource;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.model.Loan;
import pe.edu.utp.BibMpch.repository.CodeTextualResourceRepository;
import pe.edu.utp.BibMpch.repository.CustomerRepository;
import pe.edu.utp.BibMpch.repository.LoanRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final CodeTextualResourceRepository codeTextualResourceRepository;
    private final CustomerRepository customerRepository;
    private final RegisterActionsService registerActionsService;

    public List<Loan> getAllLoans() {
        List<Loan> result = new ArrayList<>();

        loanRepository.findAll().forEach(result::add);

        return result;
    }

    public Loan getById(Long id) {
        return loanRepository.findById(id)
                .orElse(null);
    }

    public List<Loan> getByIdCustomer(Long idCustomer) throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(idCustomer)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + idCustomer));

        return loanRepository.findByCustomer(customer);
    }

    public Loan create(LoanDTO loanDTO) throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(loanDTO.getIdCustomer())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + loanDTO.getIdCustomer()));

        CodeTextualResource ctr = codeTextualResourceRepository.findById(loanDTO.getIdCode())
                .orElseThrow(() -> new ResourceNotFoundException("Textual resource not found with ID: " + loanDTO.getIdCode()));

        Loan loan = Loan.builder()
                .customer(customer)
                .codeTextualResource(ctr)
                .idTypeLoan(loanDTO.getIdTypeLoan())
                .idStatusLoan(loanDTO.getIdStatusLoan())
                .initialDate(loanDTO.getInitialDate())
                .endDate(loanDTO.getEndDate())
                .scheduledDate(loanDTO.getScheduledDate())
                .build();

        Loan saveLoan = loanRepository.save(loan);

        registerActionsService.newRegisterAction(
                "Registró un nuevo préstamo - ID: %d - Cliente: %d - ID Estado: %s - ID Recurso: %s".formatted(
                        saveLoan.getId(),
                        saveLoan.getCustomer().getId(),
                        saveLoan.getIdStatusLoan(),
                        saveLoan.getCodeTextualResource().getId())
        );

        return saveLoan;
    }

    public Loan updateStatus(Long idLoan, Short idStatusLoan) throws ResourceNotFoundException {
        Loan existingLoan = loanRepository.findById(idLoan)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + idLoan));

        existingLoan.setIdStatusLoan(idStatusLoan);

        Loan saveLoan = loanRepository.save(existingLoan);

        registerActionsService.newRegisterAction(
                "Actualizó un préstamo - ID: %d - Cliente: %d - ID Estado: %s - ID Recurso: %s".formatted(
                        saveLoan.getId(),
                        saveLoan.getCustomer().getId(),
                        saveLoan.getIdStatusLoan(),
                        saveLoan.getCodeTextualResource().getId())
        );

        return saveLoan;
    }

    public Loan update(Long id, LoanDTO loanDTO) throws ResourceNotFoundException {

        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));

        if (loanDTO.getIdCustomer() != null) {
            Customer customer = customerRepository.findById(loanDTO.getIdCustomer())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + loanDTO.getIdCustomer()));
            existingLoan.setCustomer(customer);
        }

        if (loanDTO.getIdCode() != null) {
            CodeTextualResource ctr = codeTextualResourceRepository.findById(loanDTO.getIdCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Textual resource not found with ID: " + loanDTO.getIdCode()));
            existingLoan.setCodeTextualResource(ctr);
        }

        if (loanDTO.getIdTypeLoan() != null) {
            existingLoan.setIdTypeLoan(loanDTO.getIdTypeLoan());
        }
        if (loanDTO.getIdStatusLoan() != null) {
            existingLoan.setIdStatusLoan(loanDTO.getIdStatusLoan());
        }
        if (loanDTO.getInitialDate() != null) {
            existingLoan.setInitialDate(loanDTO.getInitialDate());
        }
        if (loanDTO.getEndDate() != null) {
            existingLoan.setEndDate(loanDTO.getEndDate());
        }
        if (loanDTO.getScheduledDate() != null) {
            existingLoan.setScheduledDate(loanDTO.getScheduledDate());
        }

        Loan saveLoan = loanRepository.save(existingLoan);

        registerActionsService.newRegisterAction(
                "Actualizó un préstamo - ID: %d - Cliente: %d - ID Estado: %s - ID Recurso: %s".formatted(
                        saveLoan.getId(),
                        saveLoan.getCustomer().getId(),
                        saveLoan.getIdStatusLoan(),
                        saveLoan.getCodeTextualResource().getId())
        );

        return saveLoan;
    }


}
