package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.StatusDTO;
import pe.edu.utp.BibMpch.model.Status;
import pe.edu.utp.BibMpch.repository.StatusRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusDTO createStatus(StatusDTO statusDTO) {
        Status status = statusDTO.toEntity();
        Status savedStatus = statusRepository.save(status);
        return new StatusDTO(savedStatus);
    }
    public List<StatusDTO> getAllStatuses() {
        List<Status> statuses = (List<Status>) statusRepository.findAll();
        return StatusDTO.fromEntityList(statuses);
    }
    public StatusDTO getStatusById(Short id) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + id));
        return new StatusDTO(status);
    }
    public StatusDTO getStatusByStatusName(String statusName) {
        Status status = statusRepository.findByStatusName(statusName)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with statusName: " + statusName));
        return new StatusDTO(status);
    }
    public StatusDTO updateStatus(Short id, StatusDTO statusDTO) {
        Status existingStatus = statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + id));

        existingStatus.setStatusName(statusDTO.getStatusName());
        existingStatus.setActive(statusDTO.isActive());

        Status updatedStatus = statusRepository.save(existingStatus);
        return new StatusDTO(updatedStatus);
    }
    public StatusDTO updateStatusByStatusName(String statusName, StatusDTO statusDTO) {
        Status existingStatus = statusRepository.findByStatusName(statusName)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with statusName: " + statusName));

        existingStatus.setStatusName(statusDTO.getStatusName());
        existingStatus.setActive(statusDTO.isActive());

        Status updatedStatus = statusRepository.save(existingStatus);
        return new StatusDTO(updatedStatus);
    }
    public void deleteStatusById(Short id) {
        if (!statusRepository.existsById(id)) {
            throw new EntityNotFoundException("Status not found with id: " + id);
        }
        statusRepository.deleteById(id);
    }
    public void deleteStatusByStatusName(String statusName) {
        if (!statusRepository.existsByStatusName(statusName)) {
            throw new EntityNotFoundException("Status not found with statusName: " + statusName);
        }
        statusRepository.deleteByStatusName(statusName);
    }
}
