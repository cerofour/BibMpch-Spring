package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.CountryDTO;
import pe.edu.utp.BibMpch.service.CountryService;

import java.util.List;

/**
 * Controlador para gestionar países.
 *
 * Este controlador maneja las operaciones relacionadas con los países,
 * incluyendo la creación, obtención, actualización y eliminación de países.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/countries/</code></li>
 *   <li><code>/api/v1/countries/get</code></li>
 *   <li><code>/api/v1/countries/update</code></li>
 *   <li><code>/api/v1/countries/delete</code></li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los países registrados.</li>
 *   <li>Obtener detalles de un país específico por ID.</li>
 *   <li>Crear nuevos países.</li>
 *   <li>Actualizar información de países existentes.</li>
 *   <li>Eliminar países registrados.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>CountryService</code>: Servicio para la lógica de negocio relacionada con países.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    /**
     * Obtiene una lista de todos los países.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una respuesta con la lista de países.
     */
    @GetMapping("/")
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        List<CountryDTO> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

    /**
     * Obtiene un país por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param id ID del país a obtener.
     * @return Una respuesta con el país solicitado.
     */
    @GetMapping("/get")
    public ResponseEntity<CountryDTO> getCountryById(@RequestParam Short id) {
        try {
            CountryDTO country = countryService.getCountryById(id);
            return ResponseEntity.ok(country);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Crea un nuevo país.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @param countryDTO Datos del país a crear.
     * @return Una respuesta con el país creado.
     */
    @PostMapping("/")
    public ResponseEntity<CountryDTO> createCountry(@RequestBody CountryDTO countryDTO) {
        CountryDTO savedCountry = countryService.createCountry(countryDTO);
        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }

    /**
     * Actualiza un país existente.
     *
     * <p><strong>Tipo de solicitud:</strong> POST</p>
     * <p><strong>Ruta:</strong> <code>/update</code></p>
     *
     * @param id ID del país a actualizar.
     * @param countryDTO Datos actualizados del país.
     * @return Una respuesta con el país actualizado.
     */
    @PostMapping("/update")
    public ResponseEntity<CountryDTO> updateCountry(@RequestParam Short id,
                                                    @RequestBody CountryDTO countryDTO) {
        try {
            CountryDTO updatedCountry = countryService.updateCountry(id, countryDTO);
            return ResponseEntity.ok(updatedCountry);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un país por su ID.
     *
     * <p><strong>Tipo de solicitud:</strong> DELETE</p>
     * <p><strong>Ruta:</strong> <code>/delete</code></p>
     *
     * @param id ID del país a eliminar.
     * @return Una respuesta vacía si el país fue eliminado exitosamente.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCountryById(@RequestParam Short id) {
        try {
            countryService.deleteCountryById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
