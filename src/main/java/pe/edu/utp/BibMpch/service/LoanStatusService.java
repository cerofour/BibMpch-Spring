package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.LoanStatus;
import pe.edu.utp.BibMpch.repository.LoanStatusRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanStatusService {
    private final LoanStatusRepository loanStatusRepository;

    public List<LoanStatus> getAllLoanStatus() {
        List<LoanStatus> result = new ArrayList<>();

        loanStatusRepository.findAll().forEach(result::add);

        return result;
    }

    public LoanStatus getById(Long id) {
        return loanStatusRepository.findById(id)
                .orElse(null);
    }
}
