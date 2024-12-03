package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Carnet;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para representar carnets.
 *
 * Este DTO se utiliza para transferir datos relacionados con los carnets
 * entre las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>id</code>: Identificador único del carnet.</li>
 *   <li><code>statusDTO</code>: Estado asociado al carnet.</li>
 *   <li><code>code</code>: Código único del carnet.</li>
 *   <li><code>carnetIssuanceDate</code>: Fecha de emisión del carnet.</li>
 *   <li><code>carnetExpirationDate</code>: Fecha de expiración del carnet.</li>
 * </ul>
 *
 * <p><strong>Métodos adicionales:</strong></p>
 * <ul>
 *   <li><code>CarnetDTO(Carnet c)</code>: Constructor que convierte una entidad <code>Carnet</code> en un DTO.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@NoArgsConstructor</code>: Genera un constructor sin argumentos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los argumentos.</li>
 * </ul>
 *
 * @author Vasquez
 * @version 1.0
 * @since 29/10/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarnetDTO {
    private Long id;
    private Short statusDTO;
    private String code;
    private LocalDate carnetIssuanceDate;
    private LocalDate carnetExpirationDate;

    /**
     * Constructor que inicializa un DTO basado en una entidad <code>Carnet</code>.
     *
     * @param c Entidad <code>Carnet</code> para convertir en DTO.
     */
    public CarnetDTO(Carnet c) {
        this.id = c.getId();
        this.statusDTO = c.getStatus().getId();
        this.code = c.getCode();
        this.carnetIssuanceDate = c.getCarnetIssuanceDate();
        this.carnetExpirationDate = c.getCarnetExpirationDate();
    }
}