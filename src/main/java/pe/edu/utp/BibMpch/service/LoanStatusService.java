package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.LoanStatus;
import pe.edu.utp.BibMpch.repository.LoanStatusRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link LoanStatus}.
 *
 * <p>Proporciona métodos para obtener todos los estados de préstamos registrados y obtener un estado
 * específico por su identificador.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link LoanStatusRepository}: Repositorio para gestionar las operaciones de persistencia de los estados de préstamos.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #getAllLoanStatus()}: Recupera todos los estados de préstamos registrados.</li>
 *   <li>{@link #getById(Long)}: Obtiene un estado de préstamo específico por su identificador.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@RequiredArgsConstructor</code>: Genera un constructor para inicializar los atributos finales.</li>
 * </ul>
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@Service
@RequiredArgsConstructor
public class LoanStatusService {
    private final LoanStatusRepository loanStatusRepository;

    /**
     * Recupera todos los estados de préstamos registrados en el sistema.
     *
     * @return Una lista de entidades {@link LoanStatus}.
     */
    public List<LoanStatus> getAllLoanStatus() {
        List<LoanStatus> result = new ArrayList<>();

        loanStatusRepository.findAll().forEach(result::add);

        return result;
    }

    /**
     * Obtiene un estado de préstamo específico por su identificador.
     *
     * @param id El identificador del estado de préstamo.
     * @return La entidad {@link LoanStatus} si existe; de lo contrario, <code>null</code>.
     */
    public LoanStatus getById(Long id) {
        return loanStatusRepository.findById(id)
                .orElse(null);
    }
}
