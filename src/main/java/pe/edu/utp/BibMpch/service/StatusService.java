package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.StatusDTO;
import pe.edu.utp.BibMpch.model.Status;
import pe.edu.utp.BibMpch.repository.StatusRepository;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Status}.
 *
 * <p>Proporciona métodos para crear, obtener, actualizar y eliminar estados en el sistema,
 * manejando tanto identificadores como nombres de estado.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link StatusRepository}: Repositorio para gestionar las operaciones de persistencia de estados.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #createStatus(StatusDTO)}: Crea un nuevo estado basado en un DTO.</li>
 *   <li>{@link #getAllStatuses()}: Recupera todos los estados registrados.</li>
 *   <li>{@link #getStatusById(Short)}: Obtiene un estado específico por su identificador.</li>
 *   <li>{@link #getStatusByStatusName(String)}: Obtiene un estado específico por su nombre.</li>
 *   <li>{@link #updateStatus(Short, StatusDTO)}: Actualiza un estado existente por su identificador.</li>
 *   <li>{@link #updateStatusByStatusName(String, StatusDTO)}: Actualiza un estado existente por su nombre.</li>
 *   <li>{@link #deleteStatusById(Short)}: Elimina un estado por su identificador.</li>
 *   <li>{@link #deleteStatusByStatusName(String)}: Elimina un estado por su nombre.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@RequiredArgsConstructor</code>: Genera un constructor para inicializar los atributos finales.</li>
 * </ul>
 *
 * @author Vasquez, Llacsahuanga
 * @version 1.0
 * @since 29/10/2024
 */
@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    /**
     * Crea un nuevo estado basado en un DTO.
     *
     * @param statusDTO El DTO que contiene la información del estado a crear.
     * @return Un {@link StatusDTO} que representa el estado creado.
     */
    public StatusDTO createStatus(StatusDTO statusDTO) {
        Status status = statusDTO.toEntity();
        Status savedStatus = statusRepository.save(status);
        return new StatusDTO(savedStatus);
    }

    /**
     * Recupera todos los estados registrados en el sistema.
     *
     * @return Una lista de {@link StatusDTO} que representa los estados registrados.
     */
    public List<StatusDTO> getAllStatuses() {
        List<Status> statuses = (List<Status>) statusRepository.findAll();
        return StatusDTO.fromEntityList(statuses);
    }

    /**
     * Obtiene un estado específico por su identificador.
     *
     * @param id El identificador del estado.
     * @return Un {@link StatusDTO} que representa el estado encontrado.
     * @throws EntityNotFoundException Si no se encuentra el estado con el identificador proporcionado.
     */
    public StatusDTO getStatusById(Short id) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + id));
        return new StatusDTO(status);
    }

    /**
     * Obtiene un estado específico por su nombre.
     *
     * @param statusName El nombre del estado.
     * @return Un {@link StatusDTO} que representa el estado encontrado.
     * @throws EntityNotFoundException Si no se encuentra el estado con el nombre proporcionado.
     */
    public StatusDTO getStatusByStatusName(String statusName) {
        Status status = statusRepository.findByStatusName(statusName)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with statusName: " + statusName));
        return new StatusDTO(status);
    }

    /**
     * Actualiza un estado existente por su identificador.
     *
     * @param id        El identificador del estado a actualizar.
     * @param statusDTO El DTO que contiene los nuevos datos del estado.
     * @return Un {@link StatusDTO} que representa el estado actualizado.
     * @throws EntityNotFoundException Si no se encuentra el estado con el identificador proporcionado.
     */
    public StatusDTO updateStatus(Short id, StatusDTO statusDTO) {
        Status existingStatus = statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + id));

        existingStatus.setStatusName(statusDTO.getStatusName());
        existingStatus.setActive(statusDTO.isActive());

        Status updatedStatus = statusRepository.save(existingStatus);
        return new StatusDTO(updatedStatus);
    }

    /**
     * Actualiza un estado existente por su nombre.
     *
     * @param statusName El nombre del estado a actualizar.
     * @param statusDTO  El DTO que contiene los nuevos datos del estado.
     * @return Un {@link StatusDTO} que representa el estado actualizado.
     * @throws EntityNotFoundException Si no se encuentra el estado con el nombre proporcionado.
     */
    public StatusDTO updateStatusByStatusName(String statusName, StatusDTO statusDTO) {
        Status existingStatus = statusRepository.findByStatusName(statusName)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with statusName: " + statusName));

        existingStatus.setStatusName(statusDTO.getStatusName());
        existingStatus.setActive(statusDTO.isActive());

        Status updatedStatus = statusRepository.save(existingStatus);
        return new StatusDTO(updatedStatus);
    }

    /**
     * Elimina un estado por su identificador.
     *
     * @param id El identificador del estado a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el estado con el identificador proporcionado.
     */
    public void deleteStatusById(Short id) {
        if (!statusRepository.existsById(id)) {
            throw new EntityNotFoundException("Status not found with id: " + id);
        }
        statusRepository.deleteById(id);
    }

    /**
     * Elimina un estado por su nombre.
     *
     * @param statusName El nombre del estado a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el estado con el nombre proporcionado.
     */
    public void deleteStatusByStatusName(String statusName) {
        if (!statusRepository.existsByStatusName(statusName)) {
            throw new EntityNotFoundException("Status not found with statusName: " + statusName);
        }
        statusRepository.deleteByStatusName(statusName);
    }
}
