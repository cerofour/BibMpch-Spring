package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.RegionDTO;
import pe.edu.utp.BibMpch.service.RegionService;
import java.util.List;

/**
 * Controlador para gestionar regiones.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con las regiones, incluyendo la creación, obtención,
 * actualización y eliminación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/regions/</code>: Operaciones principales.</li>
 *   <li><code>/api/v1/regions/get</code>: Obtiene una región por su ID.</li>
 *   <li><code>/api/v1/regions/update</code>: Actualiza una región existente.</li>
 *   <li><code>/api/v1/regions/delete</code>: Elimina una región por su ID.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todas las regiones registradas.</li>
 *   <li>Obtener una región específica por ID.</li>
 *   <li>Crear nuevas regiones.</li>
 *   <li>Actualizar información de regiones existentes.</li>
 *   <li>Eliminar regiones registradas.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>RegionService</code>: Servicio para la lógica de negocio relacionada con regiones.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    /**
     * Obtiene una lista de todas las regiones.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una lista con todas las regiones registradas.
     */
    @GetMapping("/")
    public ResponseEntity<List<RegionDTO>> getAllRegions() {
        List<RegionDTO> regions = regionService.getAllRegions();
        return ResponseEntity.ok(regions);
    }

    /**
     * Obtiene una región por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID de la región a obtener.
     * @return La región solicitada o un error si no se encuentra.
     */
    @GetMapping("/get")
    public ResponseEntity<RegionDTO> getRegionById(@RequestParam Long id) {
        try {
            RegionDTO region = regionService.getRegionById(id);
            return ResponseEntity.ok(region);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Crea una nueva región.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @param regionDTO Objeto con los datos de la región a crear.
     * @return La región creada.
     */
    @PostMapping("/")
    public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO regionDTO) {
        RegionDTO savedRegion = regionService.createRegion(regionDTO);
        return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
    }

    /**
     * Actualiza una región existente.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/update</code></p>
     *
     * @param id ID de la región a actualizar.
     * @param regionDTO Objeto con los datos actualizados de la región.
     * @return La región actualizada.
     */
    @PostMapping("/update")
    public ResponseEntity<RegionDTO> updateRegion(@RequestParam Long id,
                                                  @RequestBody RegionDTO regionDTO) {
        try {
            RegionDTO updatedRegion = regionService.updateRegion(id, regionDTO);
            return ResponseEntity.ok(updatedRegion);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina una región por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> DELETE</p>
     * <p><strong>Ruta:</strong> <code>/delete</code></p>
     *
     * @param id ID de la región a eliminar.
     * @return Una respuesta vacía si la operación es exitosa.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRegionById(@RequestParam Long id) {
        try {
            regionService.deleteRegionById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
