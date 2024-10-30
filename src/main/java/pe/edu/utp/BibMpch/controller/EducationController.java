package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.EducationDTO;
import pe.edu.utp.BibMpch.service.EducationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping("/")
    public ResponseEntity<List<EducationDTO>> getAllEducations() {
        List<EducationDTO> educations = educationService.getAllEducations();
        return ResponseEntity.ok(educations);
    }
    @GetMapping("/get")
    public ResponseEntity<EducationDTO> getEducationById(@RequestParam Short id) {
        try {
            EducationDTO education = educationService.getEducationById(id);
            return ResponseEntity.ok(education);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<EducationDTO> createEducation(@RequestBody EducationDTO educationDTO) {
        EducationDTO savedEducation = educationService.createEducation(educationDTO);
        return new ResponseEntity<>(savedEducation, HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<EducationDTO> updateEducation(@RequestParam Short id,
                                                        @RequestBody EducationDTO educationDTO) {
        try {
            EducationDTO updatedEducation = educationService.updateEducation(id, educationDTO);
            return ResponseEntity.ok(updatedEducation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEducationById(@RequestParam Short id) {
        try {
            educationService.deleteEducationById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
