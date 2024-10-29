package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.repository.CountryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public Country findOrCreateCountry(String countryName) {
        Optional<Country> existingCountry = countryRepository.findByCountryName(countryName);
        if (existingCountry.isPresent()) {
            return existingCountry.get();
        } else {
            Country country = new Country();
            country.setCountryName(countryName);
            return countryRepository.save(country);
        }
    }
    public List<Country> getAllCountries() {
        return (List<Country>) countryRepository.findAll();
    }
    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));
    }
    public Country updateCountry(Long id, String countryName) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));
        Optional<Country> existingCountry = countryRepository.findByCountryName(countryName);
        if (existingCountry.isPresent() && !existingCountry.get().getId().equals(id)) {
            throw new IllegalStateException("Another country with the same name already exists.");
        }
        country.setCountryName(countryName);
        return countryRepository.save(country);
    }
    public void deleteCountry(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new EntityNotFoundException("Country not found with id: " + id);
        }
        countryRepository.deleteById(id);
    }
}
