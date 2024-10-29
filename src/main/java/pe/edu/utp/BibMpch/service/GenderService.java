package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.Gender;
import pe.edu.utp.BibMpch.repository.GenderRepository;

@Service
@RequiredArgsConstructor
public class GenderService {
    private final GenderRepository genderRepository;

    public Gender createGender(Gender gender) {
        return genderRepository.save(gender);
    }
    public Iterable<Gender> getAllGenders() {
        return genderRepository.findAll();
    }
    public Gender getGenderById(Long id) {
        return genderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gender not found with id: " + id));
    }
    public Gender updateGender(Long id, Gender newGender) {
        Gender existingGender = genderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gender not found with id: " + id));
        existingGender.setGenderName(newGender.getGenderName());
        return genderRepository.save(existingGender);
    }
    public void deleteGenderById(Long id) {
        if (!genderRepository.existsById(id)) {
            throw new EntityNotFoundException("Gender not found with id: " + id);
        }
        genderRepository.deleteById(id);
    }
}
