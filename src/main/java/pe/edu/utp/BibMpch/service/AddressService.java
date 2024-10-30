//package pe.edu.utp.BibMpch.service;
//
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import pe.edu.utp.BibMpch.model.Address;
//import pe.edu.utp.BibMpch.model.District;
//import pe.edu.utp.BibMpch.repository.AddressRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class AddressService {
//    private final AddressRepository addressRepository;
//    private final DistrictService districtService;
//
//    public Address findOrCreateAddress(String addressStr, String districtName, String provinceName, String regionName, String countryName) {
//        District district = districtService.findOrCreateDistrict(districtName, provinceName, regionName, countryName);
//        Optional<Address> existingAddress = addressRepository.findByAddressAndDistrict(addressStr, district);
//        if (existingAddress.isPresent()) {
//            return existingAddress.get();
//        } else {
//            Address newAddress = new Address();
//            newAddress.setAddress(addressStr);
//            newAddress.setDistrict(district);
//            return addressRepository.save(newAddress);
//        }
//    }
//    public List<Address> findAllAddresses() {
//        return (List<Address>) addressRepository.findAll();
//    }
//    public Address findAddressById(Long id) {
//        return addressRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
//    }
//    public Address updateAddress(Long id, String newAddress, String districtName, String provinceName, String regionName, String countryName) {
//        Address address = addressRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
//        District district = districtService.findOrCreateDistrict(districtName, provinceName, regionName, countryName);
//        address.setAddress(newAddress);
//        address.setDistrict(district);
//        return addressRepository.save(address);
//    }
//    public void deleteAddress(Long id) {
//        if (!addressRepository.existsById(id)) {
//            throw new EntityNotFoundException("Address not found with id: " + id);
//        }
//        addressRepository.deleteById(id);
//    }
//}
