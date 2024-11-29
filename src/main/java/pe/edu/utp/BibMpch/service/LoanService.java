package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.LoanDTO;
import pe.edu.utp.BibMpch.exceptions.ResourceNotFoundException;
import pe.edu.utp.BibMpch.model.CodeTextualResource;
import pe.edu.utp.BibMpch.model.Loan;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.repository.CodeTextualResourceRepository;
import pe.edu.utp.BibMpch.repository.LoanRepository;
import pe.edu.utp.BibMpch.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final CodeTextualResourceRepository codeTextualResourceRepository;

    public List<Loan> getAllLoans() {
        List<Loan> result = new ArrayList<>();

        loanRepository.findAll().forEach(result::add);

        return result;
    }

    public Loan getById(Long id) {
        return loanRepository.findById(id)
                .orElse(null);
    }

    public Loan create(LoanDTO loanDTO) throws ResourceNotFoundException {

        User user = userRepository.findById(loanDTO.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + loanDTO.getIdUser()));

        CodeTextualResource ctr = codeTextualResourceRepository.findById(loanDTO.getIdCode())
                .orElseThrow(() -> new ResourceNotFoundException("Textual resource not found with ID: " + loanDTO.getIdCode()));

        Loan loan = Loan.builder()
                .user(user)
                .codeTextualResource(ctr)
                .idTypeLoan(loanDTO.getIdTypeLoan())
                .idStatusLoan(loanDTO.getIdStatusLoan())
                .initialDate(loanDTO.getInitialDate())
                .endDate(loanDTO.getEndDate())
                .scheduledDate(loanDTO.getScheduledDate())
                .build();
        return loanRepository.save(loan);
    }

    public Loan updateStatus(Long idLoan, Short idStatusLoan) throws ResourceNotFoundException {
        Loan existingLoan = loanRepository.findById(idLoan)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + idLoan));

        existingLoan.setIdStatusLoan(idStatusLoan);

        return loanRepository.save(existingLoan);
    }

    public Loan update(Long id, LoanDTO loanDTO) throws ResourceNotFoundException {

        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));

        if (loanDTO.getIdUser() != null) {
            User user = userRepository.findById(loanDTO.getIdUser())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + loanDTO.getIdUser()));
            existingLoan.setUser(user);
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

        return loanRepository.save(existingLoan);
    }


}
