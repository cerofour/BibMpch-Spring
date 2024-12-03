package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.DistrictDTO;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.model.District;
import pe.edu.utp.BibMpch.model.Province;
import pe.edu.utp.BibMpch.model.Region;
import pe.edu.utp.BibMpch.repository.CountryRepository;
import pe.edu.utp.BibMpch.repository.DistrictRepository;
import pe.edu.utp.BibMpch.repository.ProvinceRepository;
import pe.edu.utp.BibMpch.repository.RegionRepository;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link District}.
 *
 * <p>Proporciona métodos para crear, obtener, actualizar y eliminar distritos, manejando
 * la interacción con provincias, regiones y países.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link DistrictRepository}: Repositorio para gestionar las operaciones de persistencia de distritos.</li>
 *   <li>{@link ProvinceRepository}: Repositorio para gestionar las provincias asociadas.</li>
 *   <li>{@link RegionRepository}: Repositorio para gestionar las regiones asociadas.</li>
 *   <li>{@link CountryRepository}: Repositorio para gestionar los países asociados.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #createDistrict(DistrictDTO)}: Crea un nuevo distrito basado en un DTO.</li>
 *   <li>{@link #getAllDistricts()}: Recupera todos los distritos registrados.</li>
 *   <li>{@link #getDistrictById(Long)}: Obtiene un distrito específico por su identificador.</li>
 *   <li>{@link #updateDistrict(Long, DistrictDTO)}: Actualiza la información de un distrito existente.</li>
 *   <li>{@link #deleteDistrictById(Long)}: Elimina un distrito por su identificador.</li>
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
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    /**
     * Crea un nuevo distrito basado en un DTO.
     *
     * @param districtDTO El DTO que contiene la información del distrito a crear.
     * @return Un {@link DistrictDTO} que representa el distrito creado.
     */
    public DistrictDTO createDistrict(DistrictDTO districtDTO) {
        Country country = countryRepository.findById(districtDTO.getCountryId())
                .orElseGet(() -> countryRepository.save(
                        Country.builder()
                                .id(districtDTO.getCountryId())
                                .countryName(districtDTO.getCountryName())
                                .build()
                ));

        Region region = regionRepository.findById(districtDTO.getRegionId())
                .orElseGet(() -> regionRepository.save(
                        Region.builder()
                                .id(districtDTO.getRegionId())
                                .country(country)
                                .regionName(districtDTO.getRegionName())
                                .build()
                ));

        Province province = provinceRepository.findById(districtDTO.getProvinceId())
                .orElseGet(() -> provinceRepository.save(
                        Province.builder()
                                .id(districtDTO.getProvinceId())
                                .region(region)
                                .provinceName(districtDTO.getProvinceName())
                                .build()
                ));

        District district = districtDTO.toEntity(province);
        District savedDistrict = districtRepository.save(district);
        return new DistrictDTO(savedDistrict);
    }

    /**
     * Recupera todos los distritos registrados en el sistema.
     *
     * @return Una lista de {@link DistrictDTO} que representa los distritos registrados.
     */
    public List<DistrictDTO> getAllDistricts() {
        List<District> districts = (List<District>) districtRepository.findAll();
        return DistrictDTO.fromEntityList(districts);
    }

    /**
     * Obtiene un distrito específico por su identificador.
     *
     * @param id El identificador del distrito.
     * @return Un {@link DistrictDTO} que representa el distrito encontrado.
     * @throws EntityNotFoundException Si no se encuentra el distrito con el identificador proporcionado.
     */
    public DistrictDTO getDistrictById(Long id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("District not found with id: " + id));
        return new DistrictDTO(district);
    }

    /**
     * Actualiza la información de un distrito existente.
     *
     * @param id          El identificador del distrito a actualizar.
     * @param districtDTO El DTO que contiene la nueva información del distrito.
     * @return Un {@link DistrictDTO} que representa el distrito actualizado.
     * @throws EntityNotFoundException Si no se encuentra el distrito con el identificador proporcionado.
     */
    public DistrictDTO updateDistrict(Long id, DistrictDTO districtDTO) {
        District existingDistrict = districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("District not found with id: " + id));

        Country country = countryRepository.findById(districtDTO.getCountryId())
                .orElseGet(() -> countryRepository.save(
                        Country.builder()
                                .id(districtDTO.getCountryId())
                                .countryName(districtDTO.getCountryName())
                                .build()
                ));

        Region region = regionRepository.findById(districtDTO.getRegionId())
                .orElseGet(() -> regionRepository.save(
                        Region.builder()
                                .id(districtDTO.getRegionId())
                                .country(country)
                                .regionName(districtDTO.getRegionName())
                                .build()
                ));

        Province province = provinceRepository.findById(districtDTO.getProvinceId())
                .orElseGet(() -> provinceRepository.save(
                        Province.builder()
                                .id(districtDTO.getProvinceId())
                                .region(region)
                                .provinceName(districtDTO.getProvinceName())
                                .build()
                ));

        existingDistrict.setDistrictName(districtDTO.getDistrictName());
        existingDistrict.setProvince(province);

        District updatedDistrict = districtRepository.save(existingDistrict);
        return new DistrictDTO(updatedDistrict);
    }

    /**
     * Elimina un distrito por su identificador.
     *
     * @param id El identificador del distrito a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el distrito con el identificador proporcionado.
     */
    public void deleteDistrictById(Long id) {
        if (!districtRepository.existsById(id)) {
            throw new EntityNotFoundException("District not found with id: " + id);
        }
        districtRepository.deleteById(id);
    }
}
