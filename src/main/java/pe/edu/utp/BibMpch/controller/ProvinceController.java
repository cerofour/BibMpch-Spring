package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.ProvinceDTO;
import pe.edu.utp.BibMpch.service.ProvinceService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/provinces")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping("/")
    public ResponseEntity<List<ProvinceDTO>> getAllProvinces() {
        List<ProvinceDTO> provinces = provinceService.getAllProvinces();
        return ResponseEntity.ok(provinces);
    }
    @GetMapping("/get")
    public ResponseEntity<ProvinceDTO> getProvinceById(@RequestParam Long id) {
        try {
            ProvinceDTO province = provinceService.getProvinceById(id);
            return ResponseEntity.ok(province);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<ProvinceDTO> createProvince(@RequestBody ProvinceDTO provinceDTO) {
        ProvinceDTO savedProvince = provinceService.createProvince(provinceDTO);
        return new ResponseEntity<>(savedProvince, HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<ProvinceDTO> updateProvince(@RequestParam Long id,
                                                      @RequestBody ProvinceDTO provinceDTO) {
        try {
            ProvinceDTO updatedProvince = provinceService.updateProvince(id, provinceDTO);
            return ResponseEntity.ok(updatedProvince);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProvinceById(@RequestParam Long id) {
        try {
            provinceService.deleteProvinceById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
