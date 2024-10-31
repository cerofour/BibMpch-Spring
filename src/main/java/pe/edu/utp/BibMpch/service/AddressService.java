package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.AddressDTO;
import pe.edu.utp.BibMpch.model.Address;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.model.District;
import pe.edu.utp.BibMpch.model.Province;
import pe.edu.utp.BibMpch.model.Region;
import pe.edu.utp.BibMpch.repository.AddressRepository;
import pe.edu.utp.BibMpch.repository.CountryRepository;
import pe.edu.utp.BibMpch.repository.DistrictRepository;
import pe.edu.utp.BibMpch.repository.ProvinceRepository;
import pe.edu.utp.BibMpch.repository.RegionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    public AddressDTO createAddress(AddressDTO addressDTO) {
        Country country = findOrCreateCountry(addressDTO.getCountryId(), addressDTO.getCountryName());
        Region region = findOrCreateRegion(addressDTO.getRegionId(), addressDTO.getRegionName(), country);
        Province province = findOrCreateProvince(addressDTO.getProvinceId(), addressDTO.getProvinceName(), region);
        District district = findOrCreateDistrict(addressDTO.getDistrictId(), addressDTO.getDistrictName(), province);

        Address address = addressDTO.toEntity(district);
        Address savedAddress = addressRepository.save(address);
        return new AddressDTO(savedAddress);
    }
    public List<AddressDTO> getAllAddresses() {
        List<Address> addresses = (List<Address>) addressRepository.findAll();
        return AddressDTO.fromEntityList(addresses);
    }
    public AddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
        return new AddressDTO(address);
    }
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));

        Country country = findOrCreateCountry(addressDTO.getCountryId(), addressDTO.getCountryName());
        Region region = findOrCreateRegion(addressDTO.getRegionId(), addressDTO.getRegionName(), country);
        Province province = findOrCreateProvince(addressDTO.getProvinceId(), addressDTO.getProvinceName(), region);
        District district = findOrCreateDistrict(addressDTO.getDistrictId(), addressDTO.getDistrictName(), province);

        existingAddress.setAddress(addressDTO.getAddress());
        existingAddress.setDistrict(district);

        Address updatedAddress = addressRepository.save(existingAddress);
        return new AddressDTO(updatedAddress);
    }
    public void deleteAddressById(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new EntityNotFoundException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }
    private Country findOrCreateCountry(Short countryId, String countryName) {
        return countryRepository.findById(countryId)
                .orElseGet(() -> countryRepository.save(
                        Country.builder()
                                .id(countryId)
                                .countryName(countryName)
                                .build()
                ));
    }
    private Region findOrCreateRegion(Long regionId, String regionName, Country country) {
        return regionRepository.findById(regionId)
                .orElseGet(() -> regionRepository.save(
                        Region.builder()
                                .id(regionId)
                                .regionName(regionName)
                                .country(country)
                                .build()
                ));
    }
    private Province findOrCreateProvince(Long provinceId, String provinceName, Region region) {
        return provinceRepository.findById(provinceId)
                .orElseGet(() -> provinceRepository.save(
                        Province.builder()
                                .id(provinceId)
                                .provinceName(provinceName)
                                .region(region)
                                .build()
                ));
    }
    private District findOrCreateDistrict(Long districtId, String districtName, Province province) {
        return districtRepository.findById(districtId)
                .orElseGet(() -> districtRepository.save(
                        District.builder()
                                .id(districtId)
                                .districtName(districtName)
                                .province(province)
                                .build()
                ));
    }
}