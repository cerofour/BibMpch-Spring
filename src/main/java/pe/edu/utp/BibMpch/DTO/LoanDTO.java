package pe.edu.utp.BibMpch.DTO;

import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para representar préstamos.
 *
 * Este DTO se utiliza para transferir datos relacionados con los préstamos entre
 * las distintas capas de la aplicación, como controladores y servicios.
 *
 * <p><strong>Atributos:</strong></p>
 * <ul>
 *   <li><code>idLoan</code>: Identificador único del préstamo.</li>
 *   <li><code>idCustomer</code>: Identificador del cliente asociado al préstamo.</li>
 *   <li><code>idCode</code>: Identificador del código textual asociado al préstamo.</li>
 *   <li><code>idTypeLoan</code>: Identificador del tipo de préstamo.</li>
 *   <li><code>idStatusLoan</code>: Identificador del estado actual del préstamo.</li>
 *   <li><code>initialDate</code>: Fecha inicial del préstamo.</li>
 *   <li><code>endDate</code>: Fecha de finalización del préstamo.</li>
 *   <li><code>scheduledDate</code>: Fecha programada para la devolución del préstamo.</li>
 * </ul>
 *
 * <p><strong>Anotaciones de Lombok:</strong></p>
 * <ul>
 *   <li><code>@Data</code>: Genera automáticamente los métodos <code>getters</code>, <code>setters</code>, <code>equals</code>, <code>hashCode</code> y <code>toString</code>.</li>
 *   <li><code>@Builder</code>: Permite construir instancias del objeto mediante el patrón Builder.</li>
 *   <li><code>@NoArgsConstructor</code>: Genera un constructor sin argumentos.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor con todos los argumentos.</li>
 *   <li><code>@ToString</code>: Genera un método <code>toString</code> personalizado.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este DTO encapsula toda la información necesaria para gestionar préstamos, incluyendo datos
 * de clientes, códigos asociados, tipos de préstamo, estado y fechas relevantes.
 *
 * @author Huanca
 * @version 1.0
 * @since 29/11/2024
 */
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LoanDTO {
    private Integer idLoan;
    private Long idCustomer;
    private Long idCode;
    private Short idTypeLoan;
    private Short idStatusLoan;
    private LocalDate initialDate;
    private LocalDate endDate;
    private LocalDate scheduledDate;
}

