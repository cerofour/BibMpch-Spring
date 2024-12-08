package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.RegionDTO;
import pe.edu.utp.BibMpch.service.RegionService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/")
    public ResponseEntity<List<RegionDTO>> getAllRegions() {
        List<RegionDTO> regions = regionService.getAllRegions();
        return ResponseEntity.ok(regions);
    }
    @GetMapping("/get")
    public ResponseEntity<RegionDTO> getRegionById(@RequestParam Long id) {
        try {
            RegionDTO region = regionService.getRegionById(id);
            return ResponseEntity.ok(region);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO regionDTO) {
        RegionDTO savedRegion = regionService.createRegion(regionDTO);
        return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<RegionDTO> updateRegion(@RequestParam Long id,
                                                  @RequestBody RegionDTO regionDTO) {
        try {
            RegionDTO updatedRegion = regionService.updateRegion(id, regionDTO);
            return ResponseEntity.ok(updatedRegion);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRegionById(@RequestParam Long id) {
        try {
            regionService.deleteRegionById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
