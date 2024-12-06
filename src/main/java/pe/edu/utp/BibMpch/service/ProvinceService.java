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

@Service
@RequiredArgsConstructor
public class ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final RegisterActionsService registerActionsService;

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

        registerActionsService.newRegisterAction(
                "Registró una nueva provincia - ID: %d - Provincia: %s - Región: %s".formatted(
                        savedProvince.getId(),
                        savedProvince.getProvinceName(),
                        savedProvince.getRegion().getRegionName())
        );

        return new ProvinceDTO(savedProvince);
    }
    public List<ProvinceDTO> getAllProvinces() {
        List<Province> provinces = (List<Province>) provinceRepository.findAll();
        return ProvinceDTO.fromEntityList(provinces);
    }
    public ProvinceDTO getProvinceById(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Province not found with id: " + id));
        return new ProvinceDTO(province);
    }
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

        registerActionsService.newRegisterAction(
                "Actualizó una provincia - ID: %d - Provincia: %s - Región: %s".formatted(
                        updatedProvince.getId(),
                        updatedProvince.getProvinceName(),
                        updatedProvince.getRegion().getRegionName())
        );

        return new ProvinceDTO(updatedProvince);
    }
    public void deleteProvinceById(Long id) {
        if (!provinceRepository.existsById(id)) {
            throw new EntityNotFoundException("Province not found with id: " + id);
        }
        provinceRepository.deleteById(id);
    }
}