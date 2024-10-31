package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.CarnetDTO;
import pe.edu.utp.BibMpch.service.CarnetService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carnets")
@RequiredArgsConstructor
public class CarnetController {

    private final CarnetService carnetService;

    @GetMapping("/")
    public ResponseEntity<List<CarnetDTO>> getAllCarnets() {
        List<CarnetDTO> carnets = carnetService.getAllCarnets();
        return ResponseEntity.ok(carnets);
    }
    @GetMapping("/get")
    public ResponseEntity<CarnetDTO> getCarnetById(@RequestParam Long id) {
        try {
            CarnetDTO carnet = carnetService.getCarnetById(id);
            return ResponseEntity.ok(carnet);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<CarnetDTO> createCarnet(@RequestBody CarnetDTO carnetDTO) {
        try {
            CarnetDTO savedCarnet = carnetService.createCarnet(carnetDTO);
            return new ResponseEntity<>(savedCarnet, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/update")
    public ResponseEntity<CarnetDTO> updateCarnet(@RequestParam Long id, @RequestBody CarnetDTO carnetDTO) {
        try {
            CarnetDTO updatedCarnet = carnetService.updateCarnet(id, carnetDTO);
            return ResponseEntity.ok(updatedCarnet);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCarnet(@RequestParam Long id) {
        try {
            carnetService.deleteCarnetById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
