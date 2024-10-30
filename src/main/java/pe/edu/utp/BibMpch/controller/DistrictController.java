package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.DistrictDTO;
import pe.edu.utp.BibMpch.service.DistrictService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("/")
    public ResponseEntity<List<DistrictDTO>> getAllDistricts() {
        List<DistrictDTO> districts = districtService.getAllDistricts();
        return ResponseEntity.ok(districts);
    }
    @GetMapping("/get")
    public ResponseEntity<DistrictDTO> getDistrictById(@RequestParam Long id) {
        try {
            DistrictDTO district = districtService.getDistrictById(id);
            return ResponseEntity.ok(district);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<DistrictDTO> createDistrict(@RequestBody DistrictDTO districtDTO) {
        DistrictDTO savedDistrict = districtService.createDistrict(districtDTO);
        return new ResponseEntity<>(savedDistrict, HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<DistrictDTO> updateDistrict(@RequestParam Long id, @RequestBody DistrictDTO districtDTO) {
        try {
            DistrictDTO updatedDistrict = districtService.updateDistrict(id, districtDTO);
            return ResponseEntity.ok(updatedDistrict);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDistrict(@RequestParam Long id) {
        try {
            districtService.deleteDistrictById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}