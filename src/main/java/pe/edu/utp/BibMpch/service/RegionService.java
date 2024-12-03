package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.RegionDTO;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.model.Region;
import pe.edu.utp.BibMpch.repository.CountryRepository;
import pe.edu.utp.BibMpch.repository.RegionRepository;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Region}.
 *
 * <p>Proporciona métodos para crear, obtener, actualizar y eliminar regiones en el sistema.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link RegionRepository}: Repositorio para gestionar las operaciones de persistencia de regiones.</li>
 *   <li>{@link CountryRepository}: Repositorio para gestionar los países asociados a las regiones.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #createRegion(RegionDTO)}: Crea una nueva región basada en un DTO.</li>
 *   <li>{@link #getAllRegions()}: Recupera todas las regiones registradas.</li>
 *   <li>{@link #getRegionById(Long)}: Obtiene una región específica por su identificador.</li>
 *   <li>{@link #updateRegion(Long, RegionDTO)}: Actualiza una región existente.</li>
 *   <li>{@link #deleteRegionById(Long)}: Elimina una región por su identificador.</li>
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
public class RegionService {
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    /**
     * Crea una nueva región basada en un DTO.
     *
     * @param regionDTO El DTO que contiene la información de la región a crear.
     * @return Un {@link RegionDTO} que representa la región creada.
     */
    public RegionDTO createRegion(RegionDTO regionDTO) {
        Country country = countryRepository.findById(regionDTO.getCountryId())
                .orElseGet(() -> countryRepository.save(
                        Country.builder()
                                .id(regionDTO.getCountryId())
                                .countryName(regionDTO.getCountryName())
                                .build()
                ));

        Region region = regionDTO.toEntity(country);
        Region savedRegion = regionRepository.save(region);
        return new RegionDTO(savedRegion);
    }

    /**
     * Recupera todas las regiones registradas en el sistema.
     *
     * @return Una lista de {@link RegionDTO} que representa las regiones registradas.
     */
    public List<RegionDTO> getAllRegions() {
        List<Region> regions = (List<Region>) regionRepository.findAll();
        return RegionDTO.fromEntityList(regions);
    }

    /**
     * Obtiene una región específica por su identificador.
     *
     * @param id El identificador de la región.
     * @return Un {@link RegionDTO} que representa la región encontrada.
     * @throws EntityNotFoundException Si no se encuentra la región con el identificador proporcionado.
     */
    public RegionDTO getRegionById(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + id));
        return new RegionDTO(region);
    }

    /**
     * Actualiza una región existente con los datos proporcionados en un DTO.
     *
     * @param id        El identificador de la región a actualizar.
     * @param regionDTO El DTO que contiene los nuevos datos de la región.
     * @return Un {@link RegionDTO} que representa la región actualizada.
     * @throws EntityNotFoundException Si no se encuentra la región con el identificador proporcionado.
     */
    public RegionDTO updateRegion(Long id, RegionDTO regionDTO) {
        Region existingRegion = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + id));

        Country country = countryRepository.findById(regionDTO.getCountryId())
                .orElseGet(() -> countryRepository.save(
                        Country.builder()
                                .id(regionDTO.getCountryId())
                                .countryName(regionDTO.getCountryName())
                                .build()
                ));

        existingRegion.setRegionName(regionDTO.getRegionName());
        existingRegion.setCountry(country);

        Region updatedRegion = regionRepository.save(existingRegion);
        return new RegionDTO(updatedRegion);
    }

    /**
     * Elimina una región por su identificador.
     *
     * @param id El identificador de la región a eliminar.
     * @throws EntityNotFoundException Si no se encuentra la región con el identificador proporcionado.
     */
    public void deleteRegionById(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new EntityNotFoundException("Region not found with id: " + id);
        }
        regionRepository.deleteById(id);
    }
}
