package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.DTO.CountryDTO;
import pe.edu.utp.BibMpch.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/")
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        List<CountryDTO> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }
    @GetMapping("/get")
    public ResponseEntity<CountryDTO> getCountryById(@RequestParam Short id) {
        try {
            CountryDTO country = countryService.getCountryById(id);
            return ResponseEntity.ok(country);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<CountryDTO> createCountry(@RequestBody CountryDTO countryDTO) {
        CountryDTO savedCountry = countryService.createCountry(countryDTO);
        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<CountryDTO> updateCountry(@RequestParam Short id,
                                                    @RequestBody CountryDTO countryDTO) {
        try {
            CountryDTO updatedCountry = countryService.updateCountry(id, countryDTO);
            return ResponseEntity.ok(updatedCountry);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCountryById(@RequestParam Short id) {
        try {
            countryService.deleteCountryById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
