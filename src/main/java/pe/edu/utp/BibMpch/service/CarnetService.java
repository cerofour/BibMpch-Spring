package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.CarnetDTO;
import pe.edu.utp.BibMpch.model.Carnet;
import pe.edu.utp.BibMpch.model.Status;
import pe.edu.utp.BibMpch.repository.CarnetRepository;
import pe.edu.utp.BibMpch.repository.StatusRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarnetService {
    private final CarnetRepository carnetRepository;
    private final StatusRepository statusRepository;

    public CarnetDTO createCarnet(CarnetDTO carnetDTO) {
        Status status = findExistingStatus(carnetDTO.getStatusId());

        Carnet carnet = carnetDTO.toEntity(status);
        Carnet savedCarnet = carnetRepository.save(carnet);
        return new CarnetDTO(savedCarnet);
    }
    public List<CarnetDTO> getAllCarnets() {
        List<Carnet> carnets = (List<Carnet>) carnetRepository.findAll();
        return CarnetDTO.fromEntityList(carnets);
    }
    public CarnetDTO getCarnetById(Long id) {
        Carnet carnet = carnetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + id));
        return new CarnetDTO(carnet);
    }
    public CarnetDTO updateCarnet(Long id, CarnetDTO carnetDTO) {
        Carnet existingCarnet = carnetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + id));

        Status status = findExistingStatus(carnetDTO.getStatusId());

        existingCarnet.setStatus(status);
        existingCarnet.setCode(carnetDTO.getCode());
        existingCarnet.setCarnetIssuanceDate(carnetDTO.getCarnetIssuanceDate());
        existingCarnet.setCarnetExpirationDate(carnetDTO.getCarnetExpirationDate());

        Carnet updatedCarnet = carnetRepository.save(existingCarnet);
        return new CarnetDTO(updatedCarnet);
    }
    public void deleteCarnetById(Long id) {
        if (!carnetRepository.existsById(id)) {
            throw new EntityNotFoundException("Carnet not found with id: " + id);
        }
        carnetRepository.deleteById(id);
    }
    private Status findExistingStatus(Short statusId) {
        return statusRepository.findById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + statusId));
    }
}
