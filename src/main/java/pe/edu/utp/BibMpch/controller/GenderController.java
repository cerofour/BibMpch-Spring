package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.GenderDTO;
import pe.edu.utp.BibMpch.service.GenderService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/genders")
@RequiredArgsConstructor
public class GenderController {

    private final GenderService genderService;

    @GetMapping("/")
    public ResponseEntity<List<GenderDTO>> getAllGenders() {
        List<GenderDTO> genders = genderService.getAllGenders();
        return ResponseEntity.ok(genders);
    }
    @GetMapping("/get")
    public ResponseEntity<GenderDTO> getGenderById(@RequestParam Short id) {
        try {
            GenderDTO gender = genderService.getGenderById(id);
            return ResponseEntity.ok(gender);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<GenderDTO> createGender(@RequestBody GenderDTO genderDTO) {
        GenderDTO savedGender = genderService.createGender(genderDTO);
        return new ResponseEntity<>(savedGender, HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<GenderDTO> updateGender(@RequestParam Short id,
                                                  @RequestBody GenderDTO genderDTO) {
        try {
            GenderDTO updatedGender = genderService.updateGender(id, genderDTO);
            return ResponseEntity.ok(updatedGender);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGenderById(@RequestParam Short id) {
        try {
            genderService.deleteGenderById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
