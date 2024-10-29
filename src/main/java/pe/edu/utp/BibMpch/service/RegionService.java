package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.model.Region;
import pe.edu.utp.BibMpch.repository.RegionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;
    private final CountryService countryService;

    public Region findOrCreateRegion(String regionName, String countryName) {
        Country country = countryService.findOrCreateCountry(countryName);
        Optional<Region> existingRegion = regionRepository.findByRegionNameAndCountry(regionName, country);
        if (existingRegion.isPresent()) {
            return existingRegion.get();
        } else {
            Region region = new Region();
            region.setRegionName(regionName);
            region.setCountry(country);
            return regionRepository.save(region);
        }
    }
    public List<Region> findAllRegions() {
        return (List<Region>) regionRepository.findAll();
    }
    public Region findRegionById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + id));
    }
    public Region updateRegion(Long id, String regionName, String countryName) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + id));
        Country country = countryService.findOrCreateCountry(countryName);
        Optional<Region> existingRegion = regionRepository.findByRegionNameAndCountry(regionName, country);
        if (existingRegion.isPresent() && !existingRegion.get().getId().equals(id)) {
            throw new IllegalStateException("Another region with the same name and country already exists.");
        }
        region.setRegionName(regionName);
        region.setCountry(country);
        return regionRepository.save(region);
    }
    public void deleteRegion(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new EntityNotFoundException("Region not found with id: " + id);
        }
        regionRepository.deleteById(id);
    }
}
