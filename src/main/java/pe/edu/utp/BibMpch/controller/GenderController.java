package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.GenderDTO;
import pe.edu.utp.BibMpch.service.GenderService;
import java.util.List;

/**
 * Controlador para gestionar géneros.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los géneros, incluyendo la creación, obtención,
 * actualización y eliminación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/genders/</code>: Lista todos los géneros.</li>
 *   <li><code>/api/v1/genders/get</code>: Obtiene un género por su ID.</li>
 *   <li><code>/api/v1/genders/update</code>: Actualiza un género existente.</li>
 *   <li><code>/api/v1/genders/delete</code>: Elimina un género por su ID.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los géneros registrados.</li>
 *   <li>Obtener un género específico por su ID.</li>
 *   <li>Crear nuevos géneros.</li>
 *   <li>Actualizar información de géneros existentes.</li>
 *   <li>Eliminar géneros registrados.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>GenderService</code>: Servicio para la lógica de negocio relacionada con géneros.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
@RestController
@RequestMapping("/api/v1/genders")
@RequiredArgsConstructor
public class GenderController {

    private final GenderService genderService;

    /**
     * Obtiene una lista de todos los géneros.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una lista con todos los géneros registrados.
     */
    @GetMapping("/")
    public ResponseEntity<List<GenderDTO>> getAllGenders() {
        List<GenderDTO> genders = genderService.getAllGenders();
        return ResponseEntity.ok(genders);
    }

    /**
     * Obtiene un género por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID del género a obtener.
     * @return El género solicitado o un error si no se encuentra.
     */
    @GetMapping("/get")
    public ResponseEntity<GenderDTO> getGenderById(@RequestParam Short id) {
        try {
            GenderDTO gender = genderService.getGenderById(id);
            return ResponseEntity.ok(gender);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Crea un nuevo género.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @param genderDTO Objeto con los datos del género a crear.
     * @return El género creado.
     */
    @PostMapping("/")
    public ResponseEntity<GenderDTO> createGender(@RequestBody GenderDTO genderDTO) {
        GenderDTO savedGender = genderService.createGender(genderDTO);
        return new ResponseEntity<>(savedGender, HttpStatus.CREATED);
    }

    /**
     * Actualiza un género existente por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/update</code></p>
     *
     * @param id ID del género a actualizar.
     * @param genderDTO Objeto con los datos actualizados del género.
     * @return El género actualizado o un error si no se encuentra.
     */
    @PostMapping("/update")
    public ResponseEntity<GenderDTO> updateGender(@RequestParam Short id,
                                                  @RequestBody GenderDTO genderDTO) {
        try {
            GenderDTO updatedGender = genderService.updateGender(id, genderDTO);
            return ResponseEntity.ok(updatedGender);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un género por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> DELETE</p>
     * <p><strong>Ruta:</strong> <code>/delete</code></p>
     *
     * @param id ID del género a eliminar.
     * @return Una respuesta vacía si la operación es exitosa.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGenderById(@RequestParam Short id) {
        try {
            genderService.deleteGenderById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
