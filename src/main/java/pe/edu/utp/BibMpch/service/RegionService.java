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

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final RegisterActionsService registerActionsService;

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

        registerActionsService.newRegisterAction(
                "Registró una nueva región - ID: %d - Región: %s - País: %s".formatted(
                        savedRegion.getId(),
                        savedRegion.getRegionName(),
                        savedRegion.getCountry().getCountryName())
        );

        return new RegionDTO(savedRegion);
    }
    public List<RegionDTO> getAllRegions() {
        List<Region> regions = (List<Region>) regionRepository.findAll();
        return RegionDTO.fromEntityList(regions);
    }
    public RegionDTO getRegionById(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + id));
        return new RegionDTO(region);
    }
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

        registerActionsService.newRegisterAction(
                "Actualizó una nueva región - ID: %d - Región: %s - País: %s".formatted(
                        updatedRegion.getId(),
                        updatedRegion.getRegionName(),
                        updatedRegion.getCountry().getCountryName())
        );


        return new RegionDTO(updatedRegion);
    }
    public void deleteRegionById(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new EntityNotFoundException("Region not found with id: " + id);
        }
        regionRepository.deleteById(id);
    }
}
