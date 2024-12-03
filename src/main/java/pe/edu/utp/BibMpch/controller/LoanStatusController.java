package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.LoanStatus;
import pe.edu.utp.BibMpch.service.LoanStatusService;

import java.util.List;

/**
 * Controlador para gestionar los estados de préstamos.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los estados de préstamos, como listar todos los estados
 * y obtener uno específico por su ID.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/loan_status/</code>: Lista todos los estados de préstamos.</li>
 *   <li><code>/api/v1/loan_status/get</code>: Obtiene un estado de préstamo por su ID.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los estados de préstamos registrados.</li>
 *   <li>Obtener un estado de préstamo específico por su ID.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>LoanStatusService</code>: Servicio para la lógica de negocio relacionada con los estados de préstamos.</li>
 * </ul>
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@RestController
@RequestMapping(value = "/api/v1/loan_status")
@AllArgsConstructor
public class LoanStatusController {

    private final LoanStatusService loanStatusService;

    /**
     * Obtiene una lista de todos los estados de préstamos.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una lista con todos los estados de préstamos registrados.
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<LoanStatus>> get() {
        return ResponseEntity.ok(loanStatusService.getAllLoanStatus());
    }

    /**
     * Obtiene un estado de préstamo por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID del estado de préstamo a obtener.
     * @return El estado de préstamo solicitado o un error si no se encuentra.
     */
    @GetMapping(value = "/get")
    public ResponseEntity<LoanStatus> getById(@PathParam("id") Long id) {

        LoanStatus loan = loanStatusService.getById(id);

        return (loan != null) ? ResponseEntity.ok(loanStatusService.getById(id))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
