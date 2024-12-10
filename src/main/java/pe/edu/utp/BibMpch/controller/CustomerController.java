package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.utp.BibMpch.DTO.CustomerDTO;
import pe.edu.utp.BibMpch.DTO.UserToClientDTO;
import pe.edu.utp.BibMpch.configuration.ImageConfiguration;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.repository.UserRepository;
import pe.edu.utp.BibMpch.service.CustomerService;
import pe.edu.utp.BibMpch.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
@SuppressWarnings("unused")
@AllArgsConstructor
public class CustomerController {

	private final CustomerService customerService;
	private final UserRepository userRepository;
	private final ImageConfiguration imageConfiguration;
	private final ImageService imageService;

	@GetMapping(value = "/")
	public ResponseEntity<List<Customer>> get() {
		return ResponseEntity.ok(customerService.getAllCustomers());
	}

	@GetMapping(value = "/get")
	public ResponseEntity<Customer> getById(@PathParam("id") Optional<Long> id) throws BadRequestException {

		Customer customer = id.map(customerService::getCustomerById)
						.orElseThrow(BadRequestException::new);

		customer.setImageUrl(
				String.join("", imageConfiguration.getEndpointCustomer(), "/",
						customer.getUser().getDocument()));

		return ResponseEntity.ok(customer);
	}

	@GetMapping(value = "/me")
	public ResponseEntity<Customer> getMe() {
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/user_to_client", consumes = "multipart/form-data")
	public ResponseEntity<Customer> getById(
			@RequestPart("data") UserToClientDTO data,
			@RequestPart("image") MultipartFile imageFile) throws Exception {

		User user = userRepository.findById(data.getId())
				.orElseThrow(() -> new EntityNotFoundException("No se encontró al usuario con esta id."));

		String imgPath = imageService.saveCustomerImage(imageFile, user.getDocument());
		Customer customer = customerService.createFromUser(data);

		return ResponseEntity.ok(customer);
	}

	@PostMapping(value = "/", consumes = "multipart/form-data")
	public ResponseEntity<Customer> createCustomer(
			@RequestPart("customer") CustomerDTO customerDTO,
			@RequestPart("image") MultipartFile imageFile) throws Exception {

		String imgPath = imageService.saveCustomerImage(imageFile, customerDTO.getUserData().getDocument());

		Customer customer = customerService.createCustomer(customerDTO);
		customer.setImageUrl(imgPath);
		return ResponseEntity.ok(customer);
	}

	@PutMapping(value = "/{id}", consumes = "multipart/form-data")
	public ResponseEntity<Customer> updateCustomer(
			@PathVariable Long id,
			@RequestPart("customer") CustomerDTO customerDTO,
			@RequestPart(value = "image", required = false) MultipartFile imageFile) throws Exception {

		String imgPath = imageService.updateCustomerImage(imageFile, customerDTO.getUserData().getDocument());

		Customer customer = customerService.updateCustomer(id, customerDTO);
		if(imgPath!=null) customer.setImageUrl(imgPath);
		return ResponseEntity.ok(customer);
	}
}

