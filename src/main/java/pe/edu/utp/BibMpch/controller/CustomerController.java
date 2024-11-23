package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.utp.BibMpch.DTO.CustomerDTO;
import pe.edu.utp.BibMpch.configuration.ImageConfiguration;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.service.CustomerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
@SuppressWarnings("unused")
@AllArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	private final ImageConfiguration imageConfiguration;

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

	@PostMapping(value = "/", consumes = "multipart/form-data")
	public ResponseEntity<Customer> createCustomer(
			@RequestPart("customer") CustomerDTO customerDTO,
			@RequestPart("image") MultipartFile imageFile) {


		Path directoryPath = Paths.get(imageConfiguration.getUploadDirCustomer());

		try {

			String fileExtension = getFileExtension(
					Objects.requireNonNull(imageFile.getOriginalFilename())
			);

			if(!fileExtension.equals(imageConfiguration.getAllowedExtension()))
				return ResponseEntity.internalServerError().body(null);

			if (!Files.exists(directoryPath))
				Files.createDirectories(directoryPath);

			String fileName = "%s.%s".formatted(
					customerDTO.getUserData().getDocument(), imageConfiguration.getAllowedExtension());

			Path filePath = directoryPath.resolve(fileName);

			Files.write(filePath, imageFile.getBytes());

			Customer customer = customerService.createCustomer(customerDTO);

			customer.setImageUrl("%s/%s".formatted(
					imageConfiguration.getEndpointCustomer(), customerDTO.getUserData().getDocument()));

			return ResponseEntity.ok(customer);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().body(null);
		}

	}

	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toLowerCase();
	}
}

