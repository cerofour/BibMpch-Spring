package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.model.Status;
import pe.edu.utp.BibMpch.service.StatusService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statuses")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/")
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> statuses = (List<Status>) statusService.getAllStatuses();
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Status>> getStatusesByIsActive(@RequestParam boolean isActive) {
        List<Status> statuses = statusService.getStatusesByIsActive(isActive);
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/get")
    public ResponseEntity<Status> getStatusByAttribute(@RequestParam(required = false) Long id,
                                                       @RequestParam(required = false) String statusName) {
        try {
            if (id != null) {
                Status status = statusService.getStatusById(id);
                return ResponseEntity.ok(status);
            } else if (statusName != null) {
                Status status = statusService.getStatusByStatusName(statusName);
                return ResponseEntity.ok(status);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status savedStatus = statusService.createStatus(status);
        return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteStatusByAttribute(@RequestParam(required = false) Long id,
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

    @PostMapping("/update")
    public ResponseEntity<Status> updateStatus(@RequestParam(required = false) Long id,
                                               @RequestParam(required = false) String statusName,
                                               @RequestBody Status statusDetails) {
        try {
            if (id != null) {
                Status updatedStatus = statusService.updateStatus(id, statusDetails);
                return ResponseEntity.ok(updatedStatus);
            } else if (statusName != null) {
                Status updatedStatus = statusService.updateStatusByStatusName(statusName, statusDetails);
                return ResponseEntity.ok(updatedStatus);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
