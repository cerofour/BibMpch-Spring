package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.model.Carnet;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.model.Status;
import pe.edu.utp.BibMpch.repository.CarnetRepository;
import pe.edu.utp.BibMpch.repository.StatusRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Carnet}.
 *
 * <p>Proporciona métodos para crear, obtener y gestionar carnets en el sistema.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link CarnetRepository}: Repositorio para gestionar las operaciones de persistencia de carnets.</li>
 *   <li>{@link StatusRepository}: Repositorio para manejar los estados asociados a los carnets.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #createCarnet(UserDTO)}: Crea un nuevo carnet basado en la información del usuario.</li>
 *   <li>{@link #getAllCarnets()}: Recupera todos los carnets registrados.</li>
 *   <li>{@link #getCarnetById(Long)}: Obtiene un carnet específico por su identificador.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@RequiredArgsConstructor</code>: Genera un constructor para inicializar los atributos finales.</li>
 * </ul>
 *
 * @author Vasquez, Llacsahuanga, Huanca
 * @version 1.0
 * @since 29/10/2024
 */
@Service
@RequiredArgsConstructor
public class CarnetService {
    private final CarnetRepository carnetRepository;
    private final StatusRepository statusRepository;

    /**
     * Crea un nuevo carnet basado en la información del usuario proporcionada.
     *
     * @param userDTO El DTO que contiene la información del usuario.
     * @return La entidad {@link Carnet} que se creó y guardó.
     */
    public Carnet createCarnet(UserDTO userDTO) {

        LocalDate today = LocalDate.now();
        String todayString = today.format(DateTimeFormatter.BASIC_ISO_DATE);

        Carnet carnet = Carnet.builder()
                .status(statusRepository.activeStatus())
                .carnetIssuanceDate(today)
                .carnetExpirationDate(today.plusYears(1))
                .code(userDTO.getDocument() + todayString)
                .build();
        return carnetRepository.save(carnet);
    }

    /**
     * Recupera todos los carnets registrados en el sistema.
     *
     * @return Una lista de entidades {@link Carnet}.
     */
    public List<Carnet> getAllCarnets() {
        return (List<Carnet>) carnetRepository.findAll();
    }

    /**
     * Obtiene un carnet específico por su identificador.
     *
     * @param id El identificador del carnet.
     * @return La entidad {@link Carnet} si existe.
     * @throws EntityNotFoundException Si no se encuentra un carnet con el identificador proporcionado.
     */
    public Carnet getCarnetById(Long id) {
        return carnetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + id));
    }
}
