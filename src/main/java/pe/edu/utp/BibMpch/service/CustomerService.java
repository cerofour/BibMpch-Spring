package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.CustomerDTO;
import pe.edu.utp.BibMpch.model.*;
import pe.edu.utp.BibMpch.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EducationRepository educationRepository;

    private final AuthService authService;
    private final CarnetService carnetService;
    private final AddressService addressService;

    public Customer createCustomer(CustomerDTO customerDTO) throws EntityNotFoundException {

        User user = authService.signup(customerDTO.getUserData());
        Carnet carnet = carnetService.createCarnet(customerDTO.getUserData());

        Education education = educationRepository.findById(customerDTO.getEducationLevelId())
                .orElseThrow(() -> new EntityNotFoundException("Nivel educativo no encontrado."));

        Address address = addressService.build(customerDTO.getAddressData());

        Customer customer = Customer.builder()
                .user(user)
                .carnet(carnet)
                .email(customerDTO.getEmail())
                .education(education)
                .address(address)
                .build();

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

}
