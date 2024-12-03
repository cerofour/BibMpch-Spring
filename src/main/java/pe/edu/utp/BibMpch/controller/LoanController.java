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

/**
 * Controlador para gestionar préstamos.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los préstamos, incluyendo la creación, obtención,
 * actualización y búsqueda por cliente.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/loans/</code>: Lista todos los préstamos.</li>
 *   <li><code>/api/v1/loans/get</code>: Obtiene un préstamo por su ID.</li>
 *   <li><code>/api/v1/loans/get/customer</code>: Obtiene préstamos por ID de cliente.</li>
 *   <li><code>/api/v1/loans/</code>: Crea un nuevo préstamo.</li>
 *   <li><code>/api/v1/loans/{id}/status</code>: Actualiza el estado de un préstamo.</li>
 *   <li><code>/api/v1/loans/{id}</code>: Actualiza los datos de un préstamo.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los préstamos registrados.</li>
 *   <li>Obtener un préstamo específico por ID.</li>
 *   <li>Buscar préstamos asociados a un cliente específico.</li>
 *   <li>Crear nuevos préstamos.</li>
 *   <li>Actualizar el estado o los datos de un préstamo existente.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>LoanService</code>: Servicio para la lógica de negocio relacionada con los préstamos.</li>
 * </ul>
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
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

        return (loan != null) ? ResponseEntity.ok(loan)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(value = "/get/customer")
    public ResponseEntity<List<Loan>> getByIdCustomer(@PathParam("id") Long id) throws ResourceNotFoundException {

        List<Loan> loan = loanService.getByIdCustomer(id);

        return (loan != null) ? ResponseEntity.ok(loan)
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
