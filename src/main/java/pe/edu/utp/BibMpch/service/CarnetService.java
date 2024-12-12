package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.model.Carnet;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.model.Status;
import pe.edu.utp.BibMpch.repository.CarnetRepository;
import pe.edu.utp.BibMpch.repository.StatusRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarnetService {
    private final CarnetRepository carnetRepository;
    private final StatusRepository statusRepository;

    public Carnet createCarnet(UserDTO userDTO) {

        LocalDate today = LocalDate.now();
        String todayString = today.format(DateTimeFormatter.BASIC_ISO_DATE);

        Carnet carnet = Carnet.builder()
                .status(statusRepository.activeStatus())
                .carnetIssuanceDate(today)
                .carnetExpirationDate(today.plusYears(1))
                .code(userDTO.getDocument() + todayString)
                .build();
        return carnetRepository.save(carnet);
    }

    public List<Carnet> getAllCarnets() {
        return (List<Carnet>) carnetRepository.findAll();
    }

    public Carnet getCarnetById(Long id) {
        return carnetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carnet not found with id: " + id));
    }
}
