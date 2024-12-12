package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.StatusDTO;
import pe.edu.utp.BibMpch.service.StatusService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statuses")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/")
    public ResponseEntity<List<StatusDTO>> getAllStatuses() {
        List<StatusDTO> statuses = statusService.getAllStatuses();
        return ResponseEntity.ok(statuses);
    }
    @GetMapping("/get")
    public ResponseEntity<StatusDTO> getStatusByAttribute(@RequestParam(required = false) Short id,
                                                          @RequestParam(required = false) String statusName) {
        try {
            if (id != null) {
                StatusDTO status = statusService.getStatusById(id);
                return ResponseEntity.ok(status);
            } else if (statusName != null) {
                StatusDTO status = statusService.getStatusByStatusName(statusName);
                return ResponseEntity.ok(status);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<StatusDTO> createStatus(@RequestBody StatusDTO statusDTO) {
        StatusDTO savedStatus = statusService.createStatus(statusDTO);
        return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<StatusDTO> updateStatus(@RequestParam(required = false) Short id,
                                                  @RequestParam(required = false) String statusName,
                                                  @RequestBody StatusDTO statusDetails) {
        try {
            if (id != null) {
                StatusDTO updatedStatus = statusService.updateStatus(id, statusDetails);
                return ResponseEntity.ok(updatedStatus);
            } else if (statusName != null) {
                StatusDTO updatedStatus = statusService.updateStatusByStatusName(statusName, statusDetails);
                return ResponseEntity.ok(updatedStatus);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteStatusByAttribute(@RequestParam(required = false) Short id,
                                                        @RequestParam(required = false) String statusName) {
        try {
            if (id != null) {
                statusService.deleteStatusById(id);
                return ResponseEntity.noContent().build();
            } else if (statusName != null) {
                statusService.deleteStatusByStatusName(statusName);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
