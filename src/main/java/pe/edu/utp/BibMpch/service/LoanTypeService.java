package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.LoanType;
import pe.edu.utp.BibMpch.repository.LoanTypeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link LoanType}.
 *
 * <p>Proporciona métodos para obtener todos los tipos de préstamos registrados y obtener un tipo
 * específico por su identificador.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link LoanTypeRepository}: Repositorio para gestionar las operaciones de persistencia de los tipos de préstamos.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #getAllLoanTypes()}: Recupera todos los tipos de préstamos registrados.</li>
 *   <li>{@link #getById(Long)}: Obtiene un tipo de préstamo específico por su identificador.</li>
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
public class LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;

    /**
     * Recupera todos los tipos de préstamos registrados en el sistema.
     *
     * @return Una lista de entidades {@link LoanType}.
     */
    public List<LoanType> getAllLoanTypes() {
        List<LoanType> result = new ArrayList<>();

        loanTypeRepository.findAll().forEach(result::add);

        return result;
    }

    /**
     * Obtiene un tipo de préstamo específico por su identificador.
     *
     * @param id El identificador del tipo de préstamo.
     * @return La entidad {@link LoanType} si existe; de lo contrario, <code>null</code>.
     */
    public LoanType getById(Long id) {
        return loanTypeRepository.findById(id)
                .orElse(null);
    }
}