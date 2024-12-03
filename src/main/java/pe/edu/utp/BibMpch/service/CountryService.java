package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.CountryDTO;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.repository.CountryRepository;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Country}.
 *
 * <p>Proporciona métodos para crear, actualizar, obtener y eliminar países en el sistema.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link CountryRepository}: Repositorio para gestionar las operaciones de persistencia de países.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #createCountry(CountryDTO)}: Crea un nuevo país basado en un DTO.</li>
 *   <li>{@link #getAllCountries()}: Recupera todos los países registrados.</li>
 *   <li>{@link #getCountryById(Short)}: Obtiene un país específico por su identificador.</li>
 *   <li>{@link #updateCountry(Short, CountryDTO)}: Actualiza la información de un país existente.</li>
 *   <li>{@link #deleteCountryById(Short)}: Elimina un país por su identificador.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@RequiredArgsConstructor</code>: Genera un constructor para inicializar los atributos finales.</li>
 * </ul>
 *
 * @author Llacsahuanga, Vasquez
 * @version 1.0
 * @since 29/10/2024
 */
@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    /**
     * Crea un nuevo país basado en un DTO.
     *
     * @param countryDTO El DTO que contiene la información del país a crear.
     * @return Un {@link CountryDTO} que representa el país creado.
     */
    public CountryDTO createCountry(CountryDTO countryDTO) {
        Country country = countryDTO.toEntity();
        Country savedCountry = countryRepository.save(country);
        return new CountryDTO(savedCountry);
    }

    /**
     * Recupera todos los países registrados en el sistema.
     *
     * @return Una lista de {@link CountryDTO} que representa los países registrados.
     */
    public List<CountryDTO> getAllCountries() {
        List<Country> countries = (List<Country>) countryRepository.findAll();
        return CountryDTO.fromEntityList(countries);
    }

    /**
     * Obtiene un país específico por su identificador.
     *
     * @param id El identificador del país.
     * @return Un {@link CountryDTO} que representa el país encontrado.
     * @throws EntityNotFoundException Si no se encuentra el país con el identificador proporcionado.
     */
    public CountryDTO getCountryById(Short id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));
        return new CountryDTO(country);
    }

    /**
     * Actualiza la información de un país existente.
     *
     * @param id         El identificador del país a actualizar.
     * @param countryDTO El DTO que contiene la nueva información del país.
     * @return Un {@link CountryDTO} que representa el país actualizado.
     * @throws EntityNotFoundException Si no se encuentra el país con el identificador proporcionado.
     */
    public CountryDTO updateCountry(Short id, CountryDTO countryDTO) {
        Country existingCountry = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));

        existingCountry.setCountryName(countryDTO.getCountryName());

        Country updatedCountry = countryRepository.save(existingCountry);
        return new CountryDTO(updatedCountry);
    }

    /**
     * Elimina un país por su identificador.
     *
     * @param id El identificador del país a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el país con el identificador proporcionado.
     */
    public void deleteCountryById(Short id) {
        if (!countryRepository.existsById(id)) {
            throw new EntityNotFoundException("Country not found with id: " + id);
        }
        countryRepository.deleteById(id);
    }
}
