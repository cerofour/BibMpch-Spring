package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.EducationDTO;
import pe.edu.utp.BibMpch.model.Education;
import pe.edu.utp.BibMpch.repository.EducationRepository;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Education}.
 *
 * <p>Proporciona métodos para crear, obtener, actualizar y eliminar niveles educativos en el sistema.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link EducationRepository}: Repositorio para gestionar las operaciones de persistencia de niveles educativos.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #createEducation(EducationDTO)}: Crea un nuevo nivel educativo basado en un DTO.</li>
 *   <li>{@link #getAllEducations()}: Recupera todos los niveles educativos registrados.</li>
 *   <li>{@link #getEducationById(Short)}: Obtiene un nivel educativo específico por su identificador.</li>
 *   <li>{@link #updateEducation(Short, EducationDTO)}: Actualiza la información de un nivel educativo existente.</li>
 *   <li>{@link #deleteEducationById(Short)}: Elimina un nivel educativo por su identificador.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@RequiredArgsConstructor</code>: Genera un constructor para inicializar los atributos finales.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 30/10/2024
 */
@Service
@RequiredArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;

    /**
     * Crea un nuevo nivel educativo basado en un DTO.
     *
     * @param educationDTO El DTO que contiene la información del nivel educativo a crear.
     * @return Un {@link EducationDTO} que representa el nivel educativo creado.
     */
    public EducationDTO createEducation(EducationDTO educationDTO) {
        Education education = educationDTO.toEntity();
        Education savedEducation = educationRepository.save(education);
        return new EducationDTO(savedEducation);
    }

    /**
     * Recupera todos los niveles educativos registrados en el sistema.
     *
     * @return Una lista de {@link EducationDTO} que representa los niveles educativos registrados.
     */
    public List<EducationDTO> getAllEducations() {
        List<Education> educations = (List<Education>) educationRepository.findAll();
        return EducationDTO.fromEntityList(educations);
    }

    /**
     * Obtiene un nivel educativo específico por su identificador.
     *
     * @param id El identificador del nivel educativo.
     * @return Un {@link EducationDTO} que representa el nivel educativo encontrado.
     * @throws EntityNotFoundException Si no se encuentra el nivel educativo con el identificador proporcionado.
     */
    public EducationDTO getEducationById(Short id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Education not found with id: " + id));
        return new EducationDTO(education);
    }

    /**
     * Actualiza la información de un nivel educativo existente.
     *
     * @param id           El identificador del nivel educativo a actualizar.
     * @param educationDTO El DTO que contiene la nueva información del nivel educativo.
     * @return Un {@link EducationDTO} que representa el nivel educativo actualizado.
     * @throws EntityNotFoundException Si no se encuentra el nivel educativo con el identificador proporcionado.
     */
    public EducationDTO updateEducation(Short id, EducationDTO educationDTO) {
        Education existingEducation = educationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Education not found with id: " + id));

        existingEducation.setEducationName(educationDTO.getEducationName());

        Education updatedEducation = educationRepository.save(existingEducation);
        return new EducationDTO(updatedEducation);
    }

    /**
     * Elimina un nivel educativo por su identificador.
     *
     * @param id El identificador del nivel educativo a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el nivel educativo con el identificador proporcionado.
     */
    public void deleteEducationById(Short id) {
        if (!educationRepository.existsById(id)) {
            throw new EntityNotFoundException("Education not found with id: " + id);
        }
        educationRepository.deleteById(id);
    }
}
