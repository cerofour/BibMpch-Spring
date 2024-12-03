package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.LoanType;
import pe.edu.utp.BibMpch.service.LoanTypeService;

import java.util.List;

/**
 * Controlador para gestionar tipos de préstamos.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los tipos de préstamos, como listar todos los tipos
 * y obtener uno específico por su ID.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/loan_types/</code>: Lista todos los tipos de préstamos.</li>
 *   <li><code>/api/v1/loan_types/get</code>: Obtiene un tipo de préstamo por su ID.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los tipos de préstamos registrados.</li>
 *   <li>Obtener un tipo de préstamo específico por ID.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>LoanTypeService</code>: Servicio para la lógica de negocio relacionada con los tipos de préstamos.</li>
 * </ul>
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@RestController
@RequestMapping(value = "/api/v1/loan_types")
@AllArgsConstructor
public class LoanTypeController {

    private final LoanTypeService loanTypeService;

    /**
     * Obtiene una lista de todos los tipos de préstamos.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una lista con todos los tipos de préstamos registrados.
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<LoanType>> get() {
        return ResponseEntity.ok(loanTypeService.getAllLoanTypes());
    }

    /**
     * Obtiene un tipo de préstamo por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID del tipo de préstamo a obtener.
     * @return El tipo de préstamo solicitado o un error si no se encuentra.
     */
    @GetMapping(value = "/get")
    public ResponseEntity<LoanType> getById(@PathParam("id") Long id) {

        LoanType loan = loanTypeService.getById(id);

        return (loan != null) ? ResponseEntity.ok(loanTypeService.getById(id))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}