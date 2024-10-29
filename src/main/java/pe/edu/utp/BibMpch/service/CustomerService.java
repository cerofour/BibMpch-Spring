package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.*;
import pe.edu.utp.BibMpch.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final GenderRepository genderRepository;
    private final AddressRepository addressRepository;
    private final CarnetRepository carnetRepository;

    public Customer createCustomer(Customer customer) {

        User user = userRepository.findById(customer.getUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + customer.getUser().getUserId()));

        Gender gender = genderRepository.findById(customer.getGender().getId())
                .orElseThrow(() -> new EntityNotFoundException("Gender not found with id: " + customer.getGender().getId()));

        Address address = addressRepository.findById(customer.getAddress().getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + customer.getAddress().getId()));

        Carnet carnet = carnetRepository.findById(customer.getCarnet().getId())
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + customer.getCarnet().getId()));

        customer.setUser(user);
        customer.setGender(gender);
        customer.setAddress(address);
        customer.setCarnet(carnet);

        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        User user = userRepository.findById(customerDetails.getUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + customerDetails.getUser().getUserId()));

        Gender gender = genderRepository.findById(customerDetails.getGender().getId())
                .orElseThrow(() -> new EntityNotFoundException("Gender not found with id: " + customerDetails.getGender().getId()));

        Address address = addressRepository.findById(customerDetails.getAddress().getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + customerDetails.getAddress().getId()));

        Carnet carnet = carnetRepository.findById(customerDetails.getCarnet().getId())
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + customerDetails.getCarnet().getId()));

        existingCustomer.setUser(user);
        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setPLastName(customerDetails.getPLastName());
        existingCustomer.setMLastName(customerDetails.getMLastName());
        existingCustomer.setGender(gender);
        existingCustomer.setAddress(address);
        existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setCarnet(carnet);

        return customerRepository.save(existingCustomer);
    }
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
