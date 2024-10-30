package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.GenderDTO;
import pe.edu.utp.BibMpch.model.Gender;
import pe.edu.utp.BibMpch.repository.GenderRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderService {
    private final GenderRepository genderRepository;

    public GenderDTO createGender(GenderDTO genderDTO) {
        Gender gender = genderDTO.toEntity();
        Gender savedGender = genderRepository.save(gender);
        return new GenderDTO(savedGender);
    }
    public List<GenderDTO> getAllGenders() {
        List<Gender> genders = (List<Gender>) genderRepository.findAll();
        return GenderDTO.fromEntityList(genders);
    }
    public GenderDTO getGenderById(Short id) {
        Gender gender = genderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gender not found with id: " + id));
        return new GenderDTO(gender);
    }
    public GenderDTO updateGender(Short id, GenderDTO genderDTO) {
        Gender existingGender = genderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gender not found with id: " + id));

        existingGender.setGenderName(genderDTO.getGenderName());

        Gender updatedGender = genderRepository.save(existingGender);
        return new GenderDTO(updatedGender);
    }
    public void deleteGenderById(Short id) {
        if (!genderRepository.existsById(id)) {
            throw new EntityNotFoundException("Gender not found with id: " + id);
        }
        genderRepository.deleteById(id);
    }
}
