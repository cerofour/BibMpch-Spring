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

/**
 * Controlador para gestionar clientes.
 *
 * Este controlador maneja las operaciones relacionadas con los clientes,
 * incluyendo la creación, obtención y listado de clientes, así como la
 * gestión de imágenes asociadas a cada cliente.
 *
 * <p><strong>Rutas base:</strong></p>
 * <ul>
 *   <li><code>/api/v1/customers/</code></li>
 *   <li><code>/api/v1/customers/get</code></li>
 * </ul>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Listar todos los clientes registrados.</li>
 *   <li>Obtener detalles de un cliente específico por ID.</li>
 *   <li>Crear nuevos clientes con imágenes asociadas.</li>
 * </ul>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li><code>CustomerService</code>: Servicio para la lógica de negocio relacionada con clientes.</li>
 *   <li><code>ImageConfiguration</code>: Configuraciones para la gestión de imágenes.</li>
 * </ul>
 *
 * <p><strong>Notas:</strong></p>
 * <ul>
 *   <li>Este controlador maneja operaciones con imágenes, asegurando que se almacenen correctamente en el servidor.</li>
 *   <li>Utiliza <code>MultipartFile</code> para recibir archivos de imagen en las solicitudes.</li>
 * </ul>
 *
 * @author Llacsahuanga, Huanca
 * @version 1.0
 * @since 4/11/2024
 */
@RestController
@RequestMapping("/api/v1/customers")
@SuppressWarnings("unused")
@AllArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	private final ImageConfiguration imageConfiguration;

	/**
	 * Obtiene una lista de todos los clientes.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/</code></p>
	 *
	 * @return Una respuesta con la lista de clientes.
	 */
	@GetMapping(value = "/")
	public ResponseEntity<List<Customer>> get() {
		return ResponseEntity.ok(customerService.getAllCustomers());
	}

	/**
	 * Obtiene un cliente por su ID.
	 *
	 * <p><strong>Tipo de solicitud:</strong> GET</p>
	 * <p><strong>Ruta:</strong> <code>/get</code></p>
	 *
	 * @param id ID opcional del cliente a obtener.
	 * @return Una respuesta con el cliente solicitado.
	 * @throws BadRequestException Si no se proporciona un ID válido.
	 */
	@GetMapping(value = "/get")
	public ResponseEntity<Customer> getById(@PathParam("id") Optional<Long> id) throws BadRequestException {

		Customer customer = id.map(customerService::getCustomerById)
						.orElseThrow(BadRequestException::new);

		customer.setImageUrl(
				String.join("", imageConfiguration.getEndpointCustomer(), "/",
						customer.getUser().getDocument()));

		return ResponseEntity.ok(customer);
	}

	/**
	 * Crea un nuevo cliente con una imagen asociada.
	 *
	 * <p><strong>Tipo de solicitud:</strong> POST</p>
	 * <p><strong>Ruta:</strong> <code>/</code></p>
	 * <p><strong>Consumo:</strong> <code>multipart/form-data</code></p>
	 *
	 * @param customerDTO Datos del cliente a crear.
	 * @param imageFile Archivo de imagen asociado al cliente.
	 * @return Una respuesta con el cliente creado.
	 */
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

	/**
	 * Obtiene la extensión de un archivo dado su nombre.
	 *
	 * @param fileName Nombre del archivo.
	 * @return La extensión del archivo en minúsculas.
	 */
	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toLowerCase();
	}
}

