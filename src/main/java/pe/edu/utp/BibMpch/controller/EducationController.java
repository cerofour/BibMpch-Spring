package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.EducationDTO;
import pe.edu.utp.BibMpch.service.EducationService;

import java.util.List;

/**
 * Controlador para gestionar niveles de educación.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los niveles de educación, incluyendo la creación, obtención,
 * actualización y eliminación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/educations/</code>: Lista todos los niveles de educación.</li>
 *   <li><code>/api/v1/educations/get</code>: Obtiene un nivel de educación por su ID.</li>
 *   <li><code>/api/v1/educations/update</code>: Actualiza un nivel de educación existente.</li>
 *   <li><code>/api/v1/educations/delete</code>: Elimina un nivel de educación por su ID.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los niveles de educación registrados.</li>
 *   <li>Obtener un nivel de educación específico por ID.</li>
 *   <li>Crear nuevos niveles de educación.</li>
 *   <li>Actualizar información de niveles de educación existentes.</li>
 *   <li>Eliminar niveles de educación registrados.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>EducationService</code>: Servicio para la lógica de negocio relacionada con niveles de educación.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
@RestController
@RequestMapping("/api/v1/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    /**
     * Obtiene una lista de todos los niveles de educación.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una lista con todos los niveles de educación registrados.
     */
    @GetMapping("/")
    public ResponseEntity<List<EducationDTO>> getAllEducations() {
        List<EducationDTO> educations = educationService.getAllEducations();
        return ResponseEntity.ok(educations);
    }

    /**
     * Obtiene un nivel de educación por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID del nivel de educación a obtener.
     * @return El nivel de educación solicitado o un error si no se encuentra.
     */
    @GetMapping("/get")
    public ResponseEntity<EducationDTO> getEducationById(@RequestParam Short id) {
        try {
            EducationDTO education = educationService.getEducationById(id);
            return ResponseEntity.ok(education);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Crea un nuevo nivel de educación.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @param educationDTO Objeto con los datos del nivel de educación a crear.
     * @return El nivel de educación creado.
     */
    @PostMapping("/")
    public ResponseEntity<EducationDTO> createEducation(@RequestBody EducationDTO educationDTO) {
        EducationDTO savedEducation = educationService.createEducation(educationDTO);
        return new ResponseEntity<>(savedEducation, HttpStatus.CREATED);
    }

    /**
     * Actualiza un nivel de educación existente.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/update</code></p>
     *
     * @param id ID del nivel de educación a actualizar.
     * @param educationDTO Objeto con los datos actualizados del nivel de educación.
     * @return El nivel de educación actualizado o un error si no se encuentra.
     */
    @PostMapping("/update")
    public ResponseEntity<EducationDTO> updateEducation(@RequestParam Short id,
                                                        @RequestBody EducationDTO educationDTO) {
        try {
            EducationDTO updatedEducation = educationService.updateEducation(id, educationDTO);
            return ResponseEntity.ok(updatedEducation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un nivel de educación por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> DELETE</p>
     * <p><strong>Ruta:</strong> <code>/delete</code></p>
     *
     * @param id ID del nivel de educación a eliminar.
     * @return Una respuesta vacía si la operación es exitosa.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEducationById(@RequestParam Short id) {
        try {
            educationService.deleteEducationById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
