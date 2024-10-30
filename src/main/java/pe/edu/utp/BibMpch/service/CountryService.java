package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.CountryDTO;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.repository.CountryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryDTO createCountry(CountryDTO countryDTO) {
        Country country = countryDTO.toEntity();
        Country savedCountry = countryRepository.save(country);
        return new CountryDTO(savedCountry);
    }
    public List<CountryDTO> getAllCountries() {
        List<Country> countries = (List<Country>) countryRepository.findAll();
        return CountryDTO.fromEntityList(countries);
    }
    public CountryDTO getCountryById(Short id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));
        return new CountryDTO(country);
    }
    public CountryDTO updateCountry(Short id, CountryDTO countryDTO) {
        Country existingCountry = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));

        existingCountry.setCountryName(countryDTO.getCountryName());

        Country updatedCountry = countryRepository.save(existingCountry);
        return new CountryDTO(updatedCountry);
    }
    public void deleteCountryById(Short id) {
        if (!countryRepository.existsById(id)) {
            throw new EntityNotFoundException("Country not found with id: " + id);
        }
        countryRepository.deleteById(id);
    }
}
