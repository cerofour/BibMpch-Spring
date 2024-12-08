package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.LoanStatus;
import pe.edu.utp.BibMpch.service.LoanStatusService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/loan_status")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
public class LoanStatusController {

    private final LoanStatusService loanStatusService;

    @GetMapping(value = "/")
    public ResponseEntity<List<LoanStatus>> get() {
        return ResponseEntity.ok(loanStatusService.getAllLoanStatus());
    }

    @GetMapping(value = "/get")
    public ResponseEntity<LoanStatus> getById(@PathParam("id") Long id) {

        LoanStatus loan = loanStatusService.getById(id);

        return (loan != null) ? ResponseEntity.ok(loanStatusService.getById(id))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
