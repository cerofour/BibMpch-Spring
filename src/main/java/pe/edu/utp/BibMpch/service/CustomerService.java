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

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Customer}.
 *
 * <p>Proporciona métodos para crear, obtener y eliminar clientes, manejando las dependencias
 * necesarias para la creación de usuarios, carnets, direcciones y niveles educativos.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link CustomerRepository}: Repositorio para gestionar las operaciones de persistencia de clientes.</li>
 *   <li>{@link EducationRepository}: Repositorio para gestionar los niveles educativos de los clientes.</li>
 *   <li>{@link AuthService}: Servicio para manejar el registro de usuarios.</li>
 *   <li>{@link CarnetService}: Servicio para generar carnets de clientes.</li>
 *   <li>{@link AddressService}: Servicio para gestionar la creación de direcciones.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #createCustomer(CustomerDTO)}: Crea un nuevo cliente utilizando datos del DTO.</li>
 *   <li>{@link #getAllCustomers()}: Recupera todos los clientes registrados.</li>
 *   <li>{@link #getCustomerById(Long)}: Obtiene un cliente específico por su identificador.</li>
 *   <li>{@link #deleteCustomer(Long)}: Elimina un cliente por su identificador.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@RequiredArgsConstructor</code>: Genera un constructor para inicializar los atributos finales.</li>
 * </ul>
 *
 * @author Llacsahuanga, Vasquez
 * @version 1.0
 * @since 29/10/2024
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EducationRepository educationRepository;

    private final AuthService authService;
    private final CarnetService carnetService;
    private final AddressService addressService;

    /**
     * Crea un nuevo cliente utilizando los datos proporcionados en el DTO.
     *
     * @param customerDTO DTO que contiene la información del cliente a crear.
     * @return La entidad {@link Customer} creada y guardada.
     * @throws EntityNotFoundException Si algún nivel educativo o dirección no se encuentra.
     */
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

    /**
     * Recupera todos los clientes registrados en el sistema.
     *
     * @return Una lista de entidades {@link Customer}.
     */
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    /**
     * Obtiene un cliente específico por su identificador.
     *
     * @param id El identificador del cliente.
     * @return La entidad {@link Customer} si existe.
     * @throws EntityNotFoundException Si no se encuentra un cliente con el identificador proporcionado.
     */
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    /**
     * Elimina un cliente por su identificador.
     *
     * @param id El identificador del cliente a eliminar.
     * @throws EntityNotFoundException Si no se encuentra un cliente con el identificador proporcionado.
     */
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

}
