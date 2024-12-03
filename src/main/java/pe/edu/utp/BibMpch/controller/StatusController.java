package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.StatusDTO;
import pe.edu.utp.BibMpch.service.StatusService;

import java.util.List;

/**
 * Controlador para gestionar estados.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los estados, incluyendo la creación, obtención,
 * actualización y eliminación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/statuses/</code>: Operaciones principales.</li>
 *   <li><code>/api/v1/statuses/get</code>: Obtiene un estado por su ID o nombre.</li>
 *   <li><code>/api/v1/statuses/update</code>: Actualiza un estado existente.</li>
 *   <li><code>/api/v1/statuses/delete</code>: Elimina un estado por su ID o nombre.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los estados registrados.</li>
 *   <li>Obtener un estado específico por ID o nombre.</li>
 *   <li>Crear nuevos estados.</li>
 *   <li>Actualizar información de estados existentes.</li>
 *   <li>Eliminar estados registrados.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>StatusService</code>: Servicio para la lógica de negocio relacionada con estados.</li>
 * </ul>
 *
 * @author Llacsahuanga, Vasquez
 * @version 1.0
 * @since 29/10/2024
 */
@RestController
@RequestMapping("/api/v1/statuses")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    /**
     * Obtiene una lista de todos los estados.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una lista con todos los estados registrados.
     */
    @GetMapping("/")
    public ResponseEntity<List<StatusDTO>> getAllStatuses() {
        List<StatusDTO> statuses = statusService.getAllStatuses();
        return ResponseEntity.ok(statuses);
    }

    /**
     * Obtiene un estado por su ID o nombre.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID opcional del estado a obtener.
     * @param statusName Nombre opcional del estado a obtener.
     * @return El estado solicitado o un error si no se encuentra.
     */
    @GetMapping("/get")
    public ResponseEntity<StatusDTO> getStatusByAttribute(@RequestParam(required = false) Short id,
                                                          @RequestParam(required = false) String statusName) {
        try {
            if (id != null) {
                StatusDTO status = statusService.getStatusById(id);
                return ResponseEntity.ok(status);
            } else if (statusName != null) {
                StatusDTO status = statusService.getStatusByStatusName(statusName);
                return ResponseEntity.ok(status);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Crea un nuevo estado.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @param statusDTO Objeto con los datos del estado a crear.
     * @return El estado creado.
     */
    @PostMapping("/")
    public ResponseEntity<StatusDTO> createStatus(@RequestBody StatusDTO statusDTO) {
        StatusDTO savedStatus = statusService.createStatus(statusDTO);
        return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
    }

    /**
     * Actualiza un estado existente por ID o nombre.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/update</code></p>
     *
     * @param id ID opcional del estado a actualizar.
     * @param statusName Nombre opcional del estado a actualizar.
     * @param statusDetails Objeto con los datos actualizados del estado.
     * @return El estado actualizado.
     */
    @PostMapping("/update")
    public ResponseEntity<StatusDTO> updateStatus(@RequestParam(required = false) Short id,
                                                  @RequestParam(required = false) String statusName,
                                                  @RequestBody StatusDTO statusDetails) {
        try {
            if (id != null) {
                StatusDTO updatedStatus = statusService.updateStatus(id, statusDetails);
                return ResponseEntity.ok(updatedStatus);
            } else if (statusName != null) {
                StatusDTO updatedStatus = statusService.updateStatusByStatusName(statusName, statusDetails);
                return ResponseEntity.ok(updatedStatus);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un estado por su ID o nombre.
     *
     * <p><strong>Tipo de solicitud:</strong> DELETE</p>
     * <p><strong>Ruta:</strong> <code>/delete</code></p>
     *
     * @param id ID opcional del estado a eliminar.
     * @param statusName Nombre opcional del estado a eliminar.
     * @return Una respuesta vacía si la operación es exitosa.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteStatusByAttribute(@RequestParam(required = false) Short id,
                                                        @RequestParam(required = false) String statusName) {
        try {
            if (id != null) {
                statusService.deleteStatusById(id);
                return ResponseEntity.noContent().build();
            } else if (statusName != null) {
                statusService.deleteStatusByStatusName(statusName);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
