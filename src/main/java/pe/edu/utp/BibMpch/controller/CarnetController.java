package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.Carnet;
import pe.edu.utp.BibMpch.service.CarnetService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para gestionar carnets.
 *
 * Este controlador proporciona endpoints para listar y obtener carnets
 * registrados en la aplicación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/carnets/</code></li>
 *   <li><code>/api/v1/carnets/get</code></li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los carnets registrados.</li>
 *   <li>Obtener detalles de un carnet específico por ID.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>CarnetService</code>: Servicio para la lógica de negocio relacionada con carnets.</li>
 * </ul>
 *
 * @author Huanca
 * @version 1.0
 * @since 21/11/2024
 */
@RestController
@RequestMapping("/api/v1/carnets")
@AllArgsConstructor
public class CarnetController {

    private final CarnetService carnetService;

    /**
     * Obtiene una lista de todos los carnets.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una respuesta con la lista de carnets.
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<Carnet>> getCarnets() {
        return ResponseEntity.ok(carnetService.getAllCarnets());
    }

    /**
     * Obtiene un carnet por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID opcional del carnet a obtener.
     * @return Una respuesta con el carnet solicitado.
     * @throws BadRequestException Si no se proporciona un ID válido.
     */
    @GetMapping(value = "/get")
    public ResponseEntity<Carnet> getById(@PathParam("id") Optional<Long> id) throws BadRequestException {

        Carnet customer = id.map(carnetService::getCarnetById)
                .orElseThrow(BadRequestException::new);

        return ResponseEntity.ok(customer);
    }

}
