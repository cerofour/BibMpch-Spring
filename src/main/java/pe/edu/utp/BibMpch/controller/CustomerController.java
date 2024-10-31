package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.CustomerDTO;
import pe.edu.utp.BibMpch.service.CustomerService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    @GetMapping("/get")
    public ResponseEntity<CustomerDTO> getCustomerById(@RequestParam Long id) {
        try {
            CustomerDTO customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO savedCustomer = customerService.createCustomer(customerDTO);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/update")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestParam Long id, @RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
            return ResponseEntity.ok(updatedCustomer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCustomer(@RequestParam Long id) {
        try {
            customerService.deleteCustomerById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
