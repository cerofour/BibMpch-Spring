package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.GenderDTO;
import pe.edu.utp.BibMpch.model.Gender;
import pe.edu.utp.BibMpch.repository.GenderRepository;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Gender}.
 *
 * <p>Proporciona métodos para crear, obtener, actualizar y eliminar géneros en el sistema.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link GenderRepository}: Repositorio para gestionar las operaciones de persistencia de géneros.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #createGender(GenderDTO)}: Crea un nuevo género basado en un DTO.</li>
 *   <li>{@link #getAllGenders()}: Recupera todos los géneros registrados.</li>
 *   <li>{@link #getGenderById(Short)}: Obtiene un género específico por su identificador.</li>
 *   <li>{@link #updateGender(Short, GenderDTO)}: Actualiza la información de un género existente.</li>
 *   <li>{@link #deleteGenderById(Short)}: Elimina un género por su identificador.</li>
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
public class GenderService {
    private final GenderRepository genderRepository;

    /**
     * Crea un nuevo género basado en un DTO.
     *
     * @param genderDTO El DTO que contiene la información del género a crear.
     * @return Un {@link GenderDTO} que representa el género creado.
     */
    public GenderDTO createGender(GenderDTO genderDTO) {
        Gender gender = genderDTO.toEntity();
        Gender savedGender = genderRepository.save(gender);
        return new GenderDTO(savedGender);
    }

    /**
     * Recupera todos los géneros registrados en el sistema.
     *
     * @return Una lista de {@link GenderDTO} que representa los géneros registrados.
     */
    public List<GenderDTO> getAllGenders() {
        List<Gender> genders = (List<Gender>) genderRepository.findAll();
        return GenderDTO.fromEntityList(genders);
    }

    /**
     * Obtiene un género específico por su identificador.
     *
     * @param id El identificador del género.
     * @return Un {@link GenderDTO} que representa el género encontrado.
     * @throws EntityNotFoundException Si no se encuentra el género con el identificador proporcionado.
     */
    public GenderDTO getGenderById(Short id) {
        Gender gender = genderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gender not found with id: " + id));
        return new GenderDTO(gender);
    }

    /**
     * Actualiza la información de un género existente.
     *
     * @param id         El identificador del género a actualizar.
     * @param genderDTO  El DTO que contiene la nueva información del género.
     * @return Un {@link GenderDTO} que representa el género actualizado.
     * @throws EntityNotFoundException Si no se encuentra el género con el identificador proporcionado.
     */
    public GenderDTO updateGender(Short id, GenderDTO genderDTO) {
        Gender existingGender = genderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gender not found with id: " + id));

        existingGender.setGenderName(genderDTO.getGenderName());

        Gender updatedGender = genderRepository.save(existingGender);
        return new GenderDTO(updatedGender);
    }

    /**
     * Elimina un género por su identificador.
     *
     * @param id El identificador del género a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el género con el identificador proporcionado.
     */
    public void deleteGenderById(Short id) {
        if (!genderRepository.existsById(id)) {
            throw new EntityNotFoundException("Gender not found with id: " + id);
        }
        genderRepository.deleteById(id);
    }
}
