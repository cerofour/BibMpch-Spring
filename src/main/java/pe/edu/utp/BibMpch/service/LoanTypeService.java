package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.LoanType;
import pe.edu.utp.BibMpch.repository.LoanTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;

    public List<LoanType> getAllLoanTypes() {
        List<LoanType> result = new ArrayList<>();

        loanTypeRepository.findAll().forEach(result::add);

        return result;
    }

    public LoanType getById(Long id) {
        return loanTypeRepository.findById(id)
                .orElse(null);
    }
}