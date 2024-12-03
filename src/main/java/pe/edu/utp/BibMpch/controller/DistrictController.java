package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.DistrictDTO;
import pe.edu.utp.BibMpch.service.DistrictService;

import java.util.List;

/**
 * Controlador para gestionar distritos.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los distritos, incluyendo la creación, obtención,
 * actualización y eliminación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/districts/</code>: Operaciones principales.</li>
 *   <li><code>/api/v1/districts/get</code>: Obtiene un distrito por su ID.</li>
 *   <li><code>/api/v1/districts/update</code>: Actualiza un distrito existente.</li>
 *   <li><code>/api/v1/districts/delete</code>: Elimina un distrito por su ID.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los distritos registrados.</li>
 *   <li>Obtener un distrito específico por ID.</li>
 *   <li>Crear nuevos distritos.</li>
 *   <li>Actualizar información de distritos existentes.</li>
 *   <li>Eliminar distritos registrados.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>DistrictService</code>: Servicio para la lógica de negocio relacionada con distritos.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
@RestController
@RequestMapping("/api/v1/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    /**
     * Obtiene una lista de todos los distritos.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una lista con todos los distritos registrados.
     */
    @GetMapping("/")
    public ResponseEntity<List<DistrictDTO>> getAllDistricts() {
        List<DistrictDTO> districts = districtService.getAllDistricts();
        return ResponseEntity.ok(districts);
    }

    /**
     * Obtiene un distrito por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID del distrito a obtener.
     * @return El distrito solicitado o un error si no se encuentra.
     */
    @GetMapping("/get")
    public ResponseEntity<DistrictDTO> getDistrictById(@RequestParam Long id) {
        try {
            DistrictDTO district = districtService.getDistrictById(id);
            return ResponseEntity.ok(district);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Crea un nuevo distrito.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @param districtDTO Objeto con los datos del distrito a crear.
     * @return El distrito creado.
     */
    @PostMapping("/")
    public ResponseEntity<DistrictDTO> createDistrict(@RequestBody DistrictDTO districtDTO) {
        DistrictDTO savedDistrict = districtService.createDistrict(districtDTO);
        return new ResponseEntity<>(savedDistrict, HttpStatus.CREATED);
    }

    /**
     * Actualiza un distrito existente.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/update</code></p>
     *
     * @param id ID del distrito a actualizar.
     * @param districtDTO Objeto con los datos actualizados del distrito.
     * @return El distrito actualizado.
     */
    @PostMapping("/update")
    public ResponseEntity<DistrictDTO> updateDistrict(@RequestParam Long id, @RequestBody DistrictDTO districtDTO) {
        try {
            DistrictDTO updatedDistrict = districtService.updateDistrict(id, districtDTO);
            return ResponseEntity.ok(updatedDistrict);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un distrito por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> DELETE</p>
     * <p><strong>Ruta:</strong> <code>/delete</code></p>
     *
     * @param id ID del distrito a eliminar.
     * @return Una respuesta vacía si la operación es exitosa.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDistrict(@RequestParam Long id) {
        try {
            districtService.deleteDistrictById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}