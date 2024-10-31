package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.CustomerDTO;
import pe.edu.utp.BibMpch.model.*;
import pe.edu.utp.BibMpch.repository.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final CarnetRepository carnetRepository;
    private final EducationRepository educationRepository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        User user = userRepository.findByDocument(customerDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Country country = findOrCreateCountry(customerDTO.getCountryName());
        Region region = findOrCreateRegion(customerDTO.getRegionName(), country);
        Province province = findOrCreateProvince(customerDTO.getProvinceName(), region);
        District district = findOrCreateDistrict(customerDTO.getDistrictName(), province);
        Address address = findOrCreateAddress(customerDTO.getAddress(), district);

        Education education = educationRepository.findByEducationName(customerDTO.getEducationName())
                .orElseThrow(() -> new EntityNotFoundException("Education level not found"));

        Carnet carnet = carnetRepository.findByCode(customerDTO.getCode())
                .orElseGet(() -> carnetRepository.save(
                        Carnet.builder()
                                .code(customerDTO.getCode())
                                .carnetIssuanceDate(customerDTO.getCarnetIssuanceDate())
                                .carnetExpirationDate(customerDTO.getCarnetExpirationDate())
                                .build()
                ));

        Customer customer = Customer.builder()
                .user(user)
                .address(address)
                .email(customerDTO.getEmail())
                .carnet(carnet)
                .education(education)
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        return new CustomerDTO(savedCustomer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = StreamSupport
                .stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return CustomerDTO.fromEntityList(customers);
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        return new CustomerDTO(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        User user = userRepository.findByDocument(customerDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Country country = findOrCreateCountry(customerDTO.getCountryName());
        Region region = findOrCreateRegion(customerDTO.getRegionName(), country);
        Province province = findOrCreateProvince(customerDTO.getProvinceName(), region);
        District district = findOrCreateDistrict(customerDTO.getDistrictName(), province);
        Address address = findOrCreateAddress(customerDTO.getAddress(), district);

        Education education = educationRepository.findByEducationName(customerDTO.getEducationName())
                .orElseThrow(() -> new EntityNotFoundException("Education level not found"));

        Carnet carnet = carnetRepository.findByCode(customerDTO.getCode())
                .orElseGet(() -> carnetRepository.save(
                        Carnet.builder()
                                .code(customerDTO.getCode())
                                .carnetIssuanceDate(customerDTO.getCarnetIssuanceDate())
                                .carnetExpirationDate(customerDTO.getCarnetExpirationDate())
                                .build()
                ));

        existingCustomer.setUser(user);
        existingCustomer.setAddress(address);
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setCarnet(carnet);
        existingCustomer.setEducation(education);

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return new CustomerDTO(updatedCustomer);
    }

    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private Country findOrCreateCountry(String countryName) {
        return countryRepository.findByCountryName(countryName)
                .orElseGet(() -> countryRepository.save(
                        Country.builder()
                                .countryName(countryName)
                                .build()
                ));
    }
    private Region findOrCreateRegion(String regionName, Country country) {
        return regionRepository.findByRegionNameAndCountry(regionName, country)
                .orElseGet(() -> regionRepository.save(
                        Region.builder()
                                .regionName(regionName)
                                .country(country)
                                .build()
                ));
    }
    private Province findOrCreateProvince(String provinceName, Region region) {
        return provinceRepository.findByProvinceNameAndRegion(provinceName, region)
                .orElseGet(() -> provinceRepository.save(
                        Province.builder()
                                .provinceName(provinceName)
                                .region(region)
                                .build()
                ));
    }
    private District findOrCreateDistrict(String districtName, Province province) {
        return districtRepository.findByDistrictNameAndProvince(districtName, province)
                .orElseGet(() -> districtRepository.save(
                        District.builder()
                                .districtName(districtName)
                                .province(province)
                                .build()
                ));
    }
    private Address findOrCreateAddress(String addressName, District district) {
        return addressRepository.findByAddressAndDistrict(addressName, district)
                .orElseGet(() -> addressRepository.save(
                        Address.builder()
                                .address(addressName)
                                .district(district)
                                .build()
                ));
    }
}
