package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.CodeTextualResource;
import pe.edu.utp.BibMpch.repository.CodeTextualResourceRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar códigos de recursos textuales.
 *
 * Este controlador proporciona endpoints para manejar las operaciones
 * relacionadas con los códigos de recursos textuales, incluyendo la obtención
 * de todos los códigos y la búsqueda por código base.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/text_codes/</code>: Lista todos los códigos de recursos textuales.</li>
 *   <li><code>/api/v1/text_codes/get</code>: Obtiene códigos de recursos textuales por código base.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los códigos de recursos textuales registrados.</li>
 *   <li>Buscar códigos de recursos textuales específicos por su código base.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>CodeTextualResourceRepository</code>: Repositorio para interactuar con la base de datos de códigos textuales.</li>
 * </ul>
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@RestController
@RequestMapping(value = "/api/v1/text_codes")
@AllArgsConstructor
public class CodeTextualResourceController {

    @Autowired
    private CodeTextualResourceRepository codeTextualResourceRepository;

    /**
     * Obtiene una lista de todos los códigos de recursos textuales registrados.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/</code></p>
     *
     * @return Una lista con todos los códigos de recursos textuales registrados.
     */
    @GetMapping(value = "/")
    @SuppressWarnings("unused")
    public ResponseEntity<List<CodeTextualResource>> getAllCodes() {
        List<CodeTextualResource> result = new ArrayList<>();

        codeTextualResourceRepository.findAll().forEach(result::add);

        return ResponseEntity.ok(result);
    }

    /**
     * Obtiene una lista de códigos de recursos textuales por su código base.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/get</code></p>
     *
     * @param baseCode Código base del recurso textual.
     * @return Una lista con los códigos de recursos textuales que coinciden o un error si no se encuentran.
     */
    @GetMapping(value = "/get")
    public ResponseEntity<List<CodeTextualResource>> getById(@PathParam("baseCode") String baseCode) {

        List<CodeTextualResource> ct = codeTextualResourceRepository.findByBaseCode(baseCode);

        return (ct != null) ? ResponseEntity.ok(ct)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
