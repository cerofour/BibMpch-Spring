package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.LoanDTO;
import pe.edu.utp.BibMpch.exceptions.ResourceNotFoundException;
import pe.edu.utp.BibMpch.model.Loan;
import pe.edu.utp.BibMpch.service.LoanService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/loans")
@AllArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Loan>> get() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Loan> getById(@PathParam("id") Long id) {

        Loan loan = loanService.getById(id);

        return (loan != null) ? ResponseEntity.ok(loanService.getById(id))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping(value = "/")
    @SuppressWarnings("unused")
    public ResponseEntity<Loan> newLoan(@RequestBody LoanDTO loanDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(loanService.create(loanDTO));
    }

    @PutMapping(value = "/{id}/status")
    public ResponseEntity<Loan> updateLoanStatus(
            @PathVariable("id") Long idLoan,
            @RequestParam("status") Short idStatusLoan) {
        try {
            Loan updatedLoan = loanService.updateStatus(idLoan, idStatusLoan);
            return ResponseEntity.ok(updatedLoan);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody LoanDTO loanDTO) throws ResourceNotFoundException {
        Loan updatedLoan = loanService.update(id, loanDTO);
        return ResponseEntity.ok(updatedLoan);
    }

}
