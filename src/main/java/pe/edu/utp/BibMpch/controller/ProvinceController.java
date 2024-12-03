package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.ProvinceDTO;
import pe.edu.utp.BibMpch.service.ProvinceService;
import java.util.List;

/**
 * Controlador para gestionar provincias.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con las provincias, incluyendo la creación, obtención,
 * actualización y eliminación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/provinces/</code>: Operaciones principales.</li>
 *   <li><code>/api/v1/provinces/get</code>: Obtiene una provincia por su ID.</li>
 *   <li><code>/api/v1/provinces/update</code>: Actualiza una provincia existente.</li>
 *   <li><code>/api/v1/provinces/delete</code>: Elimina una provincia por su ID.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todas las provincias registradas.</li>
 *   <li>Obtener una provincia específica por ID.</li>
 *   <li>Crear nuevas provincias.</li>
 *   <li>Actualizar información de provincias existentes.</li>
 *   <li>Eliminar provincias registradas.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>ProvinceService</code>: Servicio para la lógica de negocio relacionada con provincias.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
@RestController
@RequestMapping("/api/v1/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceService provinceService;

    /**
     * Obtiene una lista de todas las provincias.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una lista con todas las provincias registradas.
     */
    @GetMapping("/")
    public ResponseEntity<List<ProvinceDTO>> getAllProvinces() {
        List<ProvinceDTO> provinces = provinceService.getAllProvinces();
        return ResponseEntity.ok(provinces);
    }

    /**
     * Obtiene una provincia por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID de la provincia a obtener.
     * @return La provincia solicitada o un error si no se encuentra.
     */
    @GetMapping("/get")
    public ResponseEntity<ProvinceDTO> getProvinceById(@RequestParam Long id) {
        try {
            ProvinceDTO province = provinceService.getProvinceById(id);
            return ResponseEntity.ok(province);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Crea una nueva provincia.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @param provinceDTO Objeto con los datos de la provincia a crear.
     * @return La provincia creada.
     */
    @PostMapping("/")
    public ResponseEntity<ProvinceDTO> createProvince(@RequestBody ProvinceDTO provinceDTO) {
        ProvinceDTO savedProvince = provinceService.createProvince(provinceDTO);
        return new ResponseEntity<>(savedProvince, HttpStatus.CREATED);
    }

    /**
     * Crea una nueva provincia.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @param provinceDTO Objeto con los datos de la provincia a crear.
     * @return La provincia creada.
     */
    @PostMapping("/update")
    public ResponseEntity<ProvinceDTO> updateProvince(@RequestParam Long id,
                                                      @RequestBody ProvinceDTO provinceDTO) {
        try {
            ProvinceDTO updatedProvince = provinceService.updateProvince(id, provinceDTO);
            return ResponseEntity.ok(updatedProvince);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina una provincia por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> DELETE</p>
     * <p><strong>Ruta:</strong> <code>/delete</code></p>
     *
     * @param id ID de la provincia a eliminar.
     * @return Una respuesta vacía si la operación es exitosa.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProvinceById(@RequestParam Long id) {
        try {
            provinceService.deleteProvinceById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
