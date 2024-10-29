package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.District;
import pe.edu.utp.BibMpch.model.Province;
import pe.edu.utp.BibMpch.repository.DistrictRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final ProvinceService provinceService;

    public District findOrCreateDistrict(String districtName, String provinceName, String regionName, String countryName) {
        Province province = provinceService.findOrCreateProvince(provinceName, regionName, countryName);
        Optional<District> existingDistrict = districtRepository.findByDistrictNameAndProvince(districtName, province);
        if (existingDistrict.isPresent()) {
            return existingDistrict.get();
        } else {
            District district = new District();
            district.setDistrictName(districtName);
            district.setProvince(province);
            return districtRepository.save(district);
        }
    }
    public List<District> findAllDistricts() {
        return (List<District>) districtRepository.findAll();
    }
    public District findDistrictById(Long id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("District not found with id: " + id));
    }
    public District updateDistrict(Long id, String newDistrictName, String provinceName, String regionName, String countryName) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("District not found with id: " + id));
        Province province = provinceService.findOrCreateProvince(provinceName, regionName, countryName);

        Optional<District> existingDistrict = districtRepository.findByDistrictNameAndProvince(newDistrictName, province);
        if (existingDistrict.isPresent() && !existingDistrict.get().getId().equals(id)) {
            throw new IllegalStateException("Another district with the same name and province already exists.");
        }

        district.setDistrictName(newDistrictName);
        district.setProvince(province);
        return districtRepository.save(district);
    }
    public void deleteDistrict(Long id) {
        if (!districtRepository.existsById(id)) {
            throw new EntityNotFoundException("District not found with id: " + id);
        }
        districtRepository.deleteById(id);
    }
}
