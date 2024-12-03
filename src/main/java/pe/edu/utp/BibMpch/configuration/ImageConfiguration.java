package pe.edu.utp.BibMpch.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Configuración para la gestión de imágenes.
 *
 * Esta clase define propiedades configurables para manejar la subida y
 * administración de imágenes en la aplicación. Las propiedades incluyen
 * rutas de subida, extensiones permitidas y endpoints relacionados.
 *
 * <p>Propiedades principales:</p>
 * <ul>
 *   <li><code>uploadDirCustomer</code>: Directorio para las imágenes de clientes.</li>
 *   <li><code>uploadDirText</code>: Directorio para las imágenes relacionadas con textos.</li>
 *   <li><code>allowedExtension</code>: Extensiones de archivo permitidas para las imágenes.</li>
 *   <li><code>endpointCustomer</code>: Endpoint para acceder a imágenes de clientes.</li>
 *   <li><code>endpointText</code>: Endpoint para acceder a imágenes de textos.</li>
 * </ul>
 *
 * <p>Estas propiedades se configuran utilizando el prefijo
 * <code>app.image</code> en el archivo de configuración de la aplicación.</p>
 *
 * @author Huanca
 * @version 1.0
 * @since 21/11/2024
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.image")
public class ImageConfiguration {
    private String uploadDirCustomer;
    private String uploadDirText;
    private String allowedExtension;
    private String endpointCustomer;
    private String endpointText;
}
