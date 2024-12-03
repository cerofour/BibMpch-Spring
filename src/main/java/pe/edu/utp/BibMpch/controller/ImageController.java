package pe.edu.utp.BibMpch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.configuration.ImageConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controlador para gestionar imágenes.
 *
 * Este controlador proporciona endpoints para obtener imágenes asociadas
 * con clientes o textos. Las imágenes se cargan desde directorios
 * configurados en la aplicación.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/image/customer/{document}</code>: Obtiene una imagen de cliente.</li>
 *   <li><code>/api/v1/image/text/{id}</code>: Obtiene una imagen de texto.</li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Cargar imágenes desde el sistema de archivos según identificadores específicos.</li>
 *   <li>Devolver las imágenes en formato HTTP adecuado.</li>
 *   <li>Manejar errores relacionados con la lectura de imágenes.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>ImageConfiguration</code>: Configuración para los directorios y extensiones de imágenes.</li>
 * </ul>
 *
 * @author Huanca
 * @version 1.0
 * @since 21/11/2024
 */
@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageConfiguration imageConfiguration;

    /**
     * Obtiene una imagen asociada a un cliente.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/customer/{document}</code></p>
     *
     * @param document Documento del cliente cuyo archivo de imagen se desea obtener.
     * @return Una respuesta con la imagen como recurso, o un error si no se encuentra.
     */
    @GetMapping("/customer/{document}")
    public ResponseEntity<Resource> getCustomerImage(@PathVariable String document) {
        return getImage(document, imageConfiguration.getUploadDirCustomer());
    }

    /**
     * Obtiene una imagen asociada a un texto.
     *
     * <p><strong>Tipo de solicitud:</strong> GET</p>
     * <p><strong>Ruta:</strong> <code>/text/{id}</code></p>
     *
     * @param id Identificador del texto cuyo archivo de imagen se desea obtener.
     * @return Una respuesta con la imagen como recurso, o un error si no se encuentra.
     */
    @GetMapping("/text/{id}")
    public ResponseEntity<Resource> getTextImage(@PathVariable String id) {
        return getImage(id, imageConfiguration.getUploadDirText());
    }

    /**
     * Método privado para cargar imágenes desde el sistema de archivos.
     *
     * <p>Este método busca un archivo en el sistema de archivos basado en un nombre
     * y un directorio configurado, y lo devuelve como un recurso HTTP.</p>
     *
     * @param name Nombre del archivo de la imagen.
     * @param imagePath Directorio donde se encuentra la imagen.
     * @return Una respuesta con el recurso de la imagen o un error si no es accesible.
     */
    private ResponseEntity<Resource> getImage(String name, String imagePath) {
        try {

            Path imagePath_ = Paths.get(imagePath)
                    .resolve("%s.%s".formatted(name, imageConfiguration.getAllowedExtension()))
                    .normalize();

            Resource resource = new UrlResource(imagePath_.toUri());

            if (!resource.exists() || !resource.isReadable())
                return ResponseEntity.notFound().build();

            String contentType = Files.probeContentType(imagePath_);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"%s\"".formatted(resource.getFilename()))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

    }
}
