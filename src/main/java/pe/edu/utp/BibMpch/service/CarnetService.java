package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.Carnet;
import pe.edu.utp.BibMpch.model.Status;
import pe.edu.utp.BibMpch.repository.CarnetRepository;
import pe.edu.utp.BibMpch.repository.StatusRepository;

@Service
@RequiredArgsConstructor
public class CarnetService {
    private final CarnetRepository carnetRepository;
    private final StatusRepository statusRepository;

    public Carnet createCarnet(Carnet carnet) {
        Status status = statusRepository.findById(carnet.getStatus().getId())
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + carnet.getStatus().getId()));
        carnet.setStatus(status);
        return carnetRepository.save(carnet);
    }
    public Iterable<Carnet> getAllCarnets() {
        return carnetRepository.findAll();
    }
    public Carnet getCarnetById(Long id) {
        return carnetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + id));
    }
    public Carnet updateCarnet(Long id, Carnet newCarnet) {
        Carnet existingCarnet = carnetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + id));
        Status status = statusRepository.findById(newCarnet.getStatus().getId())
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + newCarnet.getStatus().getId()));
        existingCarnet.setStatus(status);
        existingCarnet.setCode(newCarnet.getCode());
        existingCarnet.setCarnetIssuanceDate(newCarnet.getCarnetIssuanceDate());
        existingCarnet.setCarnetExpirationDate(newCarnet.getCarnetExpirationDate());
        return carnetRepository.save(existingCarnet);
    }
    public void deleteCarnetById(Long id) {
        if (!carnetRepository.existsById(id)) {
            throw new EntityNotFoundException("Carnet not found with id: " + id);
        }
        carnetRepository.deleteById(id);
    }
}
