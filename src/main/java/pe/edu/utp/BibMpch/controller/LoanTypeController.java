package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.LoanType;
import pe.edu.utp.BibMpch.service.LoanTypeService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/loan_types")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
public class LoanTypeController {

    private final LoanTypeService loanTypeService;

    @GetMapping(value = "/")
    public ResponseEntity<List<LoanType>> get() {
        return ResponseEntity.ok(loanTypeService.getAllLoanTypes());
    }

    @GetMapping(value = "/get")
    public ResponseEntity<LoanType> getById(@PathParam("id") Long id) {

        LoanType loan = loanTypeService.getById(id);

        return (loan != null) ? ResponseEntity.ok(loanTypeService.getById(id))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}