package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.AddressDTO;
import pe.edu.utp.BibMpch.DTO.CustomerDTO;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.DTO.UserToClientDTO;
import pe.edu.utp.BibMpch.model.*;
import pe.edu.utp.BibMpch.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EducationRepository educationRepository;
    private final RegisterActionsService registerActionsService;
    private final UserRepository userRepository;

    private final AuthService authService;
    private final CarnetService carnetService;
    private final AddressService addressService;

    public Customer createCustomer(CustomerDTO customerDTO) throws EntityNotFoundException {

        customerDTO.getUserData().setRoleId((short)2); // ensure this is a client role.
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

        Customer saveCustomer = customerRepository.save(customer);

        registerActionsService.newRegisterAction(
                "Creó un nuevo cliente - ID:%d - Document: %s - UserID: %s".formatted(
                        saveCustomer.getId(),
                        saveCustomer.getUser().getDocument(),
                        saveCustomer.getUser().getUserId())
        );

        return saveCustomer;
    }

    public Customer createFromUser(UserToClientDTO data) {
        User user = userRepository.findById(data.getId()).orElseThrow();

        if (user.getRole().getId() == (short)2)
            return null;

        Carnet carnet = carnetService.createCarnet(UserDTO.builder()
                .name(user.getName())
                .mlastname(user.getMLastName())
                .plastname(user.getPLastName())
                .phoneNumber(user.getPhoneNumber())
                .genderId(user.getGender().getId())
                .psk("")
                .roleId(user.getRole().getId())
                .document(user.getDocument())
                .documentTypeId(user.getDocumentTypeId())
                .build()
        );

        Education education = educationRepository.findById(data.getEducationLevel())
                .orElseThrow(() -> new EntityNotFoundException("Nivel educativo no encontrado."));

        Address address = addressService.build(AddressDTO.builder()
                .district(data.getAddressDTO().getDistrict())
                .address(data.getAddressDTO().getAddress())
                .build());

        Customer customer = Customer.builder()
                .user(user)
                .carnet(carnet)
                .email(data.getEmail())
                .education(education)
                .address(address)
                .build();

        Customer saveCustomer = customerRepository.save(customer);

        registerActionsService.newRegisterAction(
                "Creó un nuevo cliente - ID:%d - Document: %s - UserID: %s".formatted(
                        saveCustomer.getId(),
                        saveCustomer.getUser().getDocument(),
                        saveCustomer.getUser().getUserId())
        );

        return saveCustomer;
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
