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

        Address address = addressRepository.findById(customer.getAddress().getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + customer.getAddress().getId()));

        Carnet carnet = carnetRepository.findById(customer.getCarnet().getId())
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + customer.getCarnet().getId()));

        customer.setUser(user);
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

        Address address = addressRepository.findById(customerDetails.getAddress().getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + customerDetails.getAddress().getId()));

        Carnet carnet = carnetRepository.findById(customerDetails.getCarnet().getId())
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + customerDetails.getCarnet().getId()));

        existingCustomer.setUser(user);
        existingCustomer.setAddress(address);
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
