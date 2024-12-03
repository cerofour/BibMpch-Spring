package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.AddressDTO;
import pe.edu.utp.BibMpch.model.Address;
import pe.edu.utp.BibMpch.model.District;
import pe.edu.utp.BibMpch.repository.AddressRepository;
import pe.edu.utp.BibMpch.repository.DistrictRepository;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Address}.
 *
 * <p>Proporciona métodos para construir y guardar instancias de direcciones, asegurando la validación
 * de los datos y la relación con los distritos.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link AddressRepository}: Repositorio para gestionar las operaciones de persistencia de direcciones.</li>
 *   <li>{@link DistrictRepository}: Repositorio para gestionar las operaciones de persistencia de distritos.</li>
 * </ul>
 *
 * <p><strong>Métodos principales:</strong></p>
 * <ul>
 *   <li>{@link #build(AddressDTO)}: Construye una dirección a partir de un DTO y la guarda en la base de datos.</li>
 * </ul>
 *
 * <p><strong>Anotaciones:</strong></p>
 * <ul>
 *   <li><code>@Service</code>: Marca esta clase como un componente de servicio en Spring.</li>
 *   <li><code>@AllArgsConstructor</code>: Genera un constructor que incluye todos los atributos declarados en la clase.</li>
 * </ul>
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 4/11/2024
 */
@Service
@AllArgsConstructor
class AddressService {
	private AddressRepository addressRepository;
	private DistrictRepository districtRepository;

	/**
	 * Construye una dirección a partir de un {@link AddressDTO} y la guarda en la base de datos.
	 *
	 * @param addressDTO El DTO que contiene los datos de la dirección a construir.
	 * @return La entidad {@link Address} que se creó y guardó.
	 * @throws EntityNotFoundException Si el distrito asociado no existe.
	 */
	public Address build(AddressDTO addressDTO) throws EntityNotFoundException {
		District district = districtRepository.findById(addressDTO.getDistrict())
				.orElseThrow(() -> new EntityNotFoundException(String.format("Distrito con id %d no encontrado. No se pudo agregar esta dirección", addressDTO.getDistrict())));

		Address address = Address.builder()
				.district(district)
				.address(addressDTO.getAddress())
				.build();

		return addressRepository.save(address);
	}
}