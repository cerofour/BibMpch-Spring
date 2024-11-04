package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.CustomerDTO;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
@SuppressWarnings("unused")
@AllArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping(value = "/")
	public ResponseEntity<List<Customer>> get() {
		return ResponseEntity.ok(customerService.getAllCustomers());
	}

	@GetMapping(value = "/get")
	public ResponseEntity<Customer> getById(@PathVariable Optional<Long> id) throws BadRequestException {

		Customer customer = id.map(customerService::getCustomerById)
						.orElseThrow(BadRequestException::new);

		return ResponseEntity.ok(customer);
	}

	@PostMapping(value = "/")
	public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customerDTO) {
		return ResponseEntity.ok(customerService.createCustomer(customerDTO));
	}
}
