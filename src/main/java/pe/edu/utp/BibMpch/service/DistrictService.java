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

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final RegisterActionsService registerActionsService;

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

        registerActionsService.newRegisterAction(
                "Registró un nuevo distrito - ID: %d - Distrito: %s - Provincia: %s".formatted(
                        savedDistrict.getId(),
                        savedDistrict.getDistrictName(),
                        savedDistrict.getProvince().getProvinceName())
        );

        return new DistrictDTO(savedDistrict);
    }

    public List<DistrictDTO> getAllDistricts() {
        List<District> districts = (List<District>) districtRepository.findAll();
        return DistrictDTO.fromEntityList(districts);
    }

    public DistrictDTO getDistrictById(Long id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("District not found with id: " + id));
        return new DistrictDTO(district);
    }

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

        registerActionsService.newRegisterAction(
                "Actualizó un distrito - ID: %d - Distrito: %s - Provincia: %s".formatted(
                        updatedDistrict.getId(),
                        updatedDistrict.getDistrictName(),
                        updatedDistrict.getProvince().getProvinceName())
        );

        return new DistrictDTO(updatedDistrict);
    }

    public void deleteDistrictById(Long id) {
        if (!districtRepository.existsById(id)) {
            throw new EntityNotFoundException("District not found with id: " + id);
        }
        districtRepository.deleteById(id);
    }
}
