package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.ProvinceDTO;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.model.Province;
import pe.edu.utp.BibMpch.model.Region;
import pe.edu.utp.BibMpch.repository.CountryRepository;
import pe.edu.utp.BibMpch.repository.ProvinceRepository;
import pe.edu.utp.BibMpch.repository.RegionRepository;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Province}.
 *
 * <p>Proporciona métodos para crear, obtener, actualizar y eliminar provincias en el sistema.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link ProvinceRepository}: Repositorio para gestionar las operaciones de persistencia de provincias.</li>
 *   <li>{@link RegionRepository}: Repositorio para gestionar las regiones asociadas a las provincias.</li>
 *   <li>{@link CountryRepository}: Repositorio para gestionar los países asociados a las provincias.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #createProvince(ProvinceDTO)}: Crea una nueva provincia basada en un DTO.</li>
 *   <li>{@link #getAllProvinces()}: Recupera todas las provincias registradas.</li>
 *   <li>{@link #getProvinceById(Long)}: Obtiene una provincia específica por su identificador.</li>
 *   <li>{@link #updateProvince(Long, ProvinceDTO)}: Actualiza una provincia existente.</li>
 *   <li>{@link #deleteProvinceById(Long)}: Elimina una provincia por su identificador.</li>
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
public class ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    /**
     * Crea una nueva provincia basada en un DTO.
     *
     * @param provinceDTO El DTO que contiene la información de la provincia a crear.
     * @return Un {@link ProvinceDTO} que representa la provincia creada.
     */
    public ProvinceDTO createProvince(ProvinceDTO provinceDTO) {
        Country country = countryRepository.findById(provinceDTO.getCountryId())
                .orElseGet(() -> countryRepository.save(
                        Country.builder()
                                .id(provinceDTO.getCountryId())
                                .countryName(provinceDTO.getCountryName())
                                .build()
                ));

        Region region = regionRepository.findById(provinceDTO.getRegionId())
                .orElseGet(() -> regionRepository.save(
                        Region.builder()
                                .id(provinceDTO.getRegionId())
                                .country(country)
                                .regionName(provinceDTO.getRegionName())
                                .build()
                ));

        Province province = provinceDTO.toEntity(region);
        Province savedProvince = provinceRepository.save(province);
        return new ProvinceDTO(savedProvince);
    }

    /**
     * Recupera todas las provincias registradas en el sistema.
     *
     * @return Una lista de {@link ProvinceDTO} que representa las provincias registradas.
     */
    public List<ProvinceDTO> getAllProvinces() {
        List<Province> provinces = (List<Province>) provinceRepository.findAll();
        return ProvinceDTO.fromEntityList(provinces);
    }

    /**
     * Obtiene una provincia específica por su identificador.
     *
     * @param id El identificador de la provincia.
     * @return Un {@link ProvinceDTO} que representa la provincia encontrada.
     * @throws EntityNotFoundException Si no se encuentra la provincia con el identificador proporcionado.
     */
    public ProvinceDTO getProvinceById(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Province not found with id: " + id));
        return new ProvinceDTO(province);
    }

    /**
     * Actualiza una provincia existente con los datos proporcionados en un DTO.
     *
     * @param id          El identificador de la provincia a actualizar.
     * @param provinceDTO El DTO que contiene los nuevos datos de la provincia.
     * @return Un {@link ProvinceDTO} que representa la provincia actualizada.
     * @throws EntityNotFoundException Si no se encuentra la provincia con el identificador proporcionado.
     */
    public ProvinceDTO updateProvince(Long id, ProvinceDTO provinceDTO) {
        Province existingProvince = provinceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Province not found with id: " + id));

        Country country = countryRepository.findById(provinceDTO.getCountryId())
                .orElseGet(() -> countryRepository.save(
                        Country.builder()
                                .id(provinceDTO.getCountryId())
                                .countryName(provinceDTO.getCountryName())
                                .build()
                ));

        Region region = regionRepository.findById(provinceDTO.getRegionId())
                .orElseGet(() -> regionRepository.save(
                        Region.builder()
                                .id(provinceDTO.getRegionId())
                                .country(country)
                                .regionName(provinceDTO.getRegionName())
                                .build()
                ));

        existingProvince.setProvinceName(provinceDTO.getProvinceName());
        existingProvince.setRegion(region);

        Province updatedProvince = provinceRepository.save(existingProvince);
        return new ProvinceDTO(updatedProvince);
    }

    /**
     * Elimina una provincia por su identificador.
     *
     * @param id El identificador de la provincia a eliminar.
     * @throws EntityNotFoundException Si no se encuentra la provincia con el identificador proporcionado.
     */
    public void deleteProvinceById(Long id) {
        if (!provinceRepository.existsById(id)) {
            throw new EntityNotFoundException("Province not found with id: " + id);
        }
        provinceRepository.deleteById(id);
    }
}