package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.AddressDTO;
import pe.edu.utp.BibMpch.model.Address;
import pe.edu.utp.BibMpch.model.District;
import pe.edu.utp.BibMpch.repository.AddressRepository;
import pe.edu.utp.BibMpch.repository.DistrictRepository;

@Service
@AllArgsConstructor
class AddressService {
	private AddressRepository addressRepository;
	private DistrictRepository districtRepository;
	private RegisterActionsService registerActionsService;

	public Address build(AddressDTO addressDTO) throws EntityNotFoundException {
		District district = districtRepository.findById(addressDTO.getDistrict())
				.orElseThrow(() -> new EntityNotFoundException(String.format("Distrito con id %d no encontrado. No se pudo agregar esta dirección", addressDTO.getDistrict())));

		Address address = Address.builder()
				.district(district)
				.address(addressDTO.getAddress())
				.build();

		Address saveAddress = addressRepository.save(address);

		registerActionsService.newRegisterAction(
				"Registró una nueva dirección - ID: %d".formatted(saveAddress.getId()));

        return saveAddress;
	}
}