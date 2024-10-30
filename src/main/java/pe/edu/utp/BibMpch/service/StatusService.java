package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.Status;
import pe.edu.utp.BibMpch.repository.StatusRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }
    public Iterable<Status> getAllStatuses() {
        return statusRepository.findAll();
    }
    public Status getStatusById(Short id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + id));
    }
    public List<Status> getStatusesByIsActive(boolean isActive) {
        return statusRepository.findByIsActive(isActive);
    }
    public Status getStatusByStatusName(String statusName) {
        return statusRepository.findByStatusName(statusName)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with statusName: " + statusName));
    }
    public Status updateStatus(Short id, Status newStatus) {
        Status existingStatus = statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with id: " + id));

        existingStatus.setStatusName(newStatus.getStatusName());
        existingStatus.setActive(newStatus.isActive());

        return statusRepository.save(existingStatus);
    }
    public Status updateStatusByStatusName(String statusName, Status statusDetails) {
        Status existingStatus = statusRepository.findByStatusName(statusName)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with statusName: " + statusName));

        existingStatus.setStatusName(statusDetails.getStatusName());
        existingStatus.setActive(statusDetails.isActive());
        return statusRepository.save(existingStatus);
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
