package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.Province;
import pe.edu.utp.BibMpch.model.Region;
import pe.edu.utp.BibMpch.repository.ProvinceRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final RegionService regionService;

    public Province findOrCreateProvince(String provinceName, String regionName, String countryName) {
        Region region = regionService.findOrCreateRegion(regionName, countryName);
        Optional<Province> existingProvince = provinceRepository.findByProvinceNameAndRegion(provinceName, region);
        if (existingProvince.isPresent()) {
            return existingProvince.get();
        } else {
            Province province = new Province();
            province.setProvinceName(provinceName);
            province.setRegion(region);
            return provinceRepository.save(province);
        }
    }
    public List<Province> findAllProvinces() {
        return (List<Province>) provinceRepository.findAll();
    }
    public Province findProvinceById(Long id) {
        return provinceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Province not found with id: " + id));
    }
    public Province updateProvince(Long id, String provinceName, String regionName, String countryName) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Province not found with id: " + id));
        Region region = regionService.findOrCreateRegion(regionName, countryName);

        Optional<Province> existingProvince = provinceRepository.findByProvinceNameAndRegion(provinceName, region);
        if (existingProvince.isPresent() && !existingProvince.get().getId().equals(id)) {
            throw new IllegalStateException("Another province with the same name and region already exists.");
        }
        province.setProvinceName(provinceName);
        province.setRegion(region);
        return provinceRepository.save(province);
    }
    public void deleteProvince(Long id) {
        if (!provinceRepository.existsById(id)) {
            throw new EntityNotFoundException("Province not found with id: " + id);
        }
        provinceRepository.deleteById(id);
    }
}
