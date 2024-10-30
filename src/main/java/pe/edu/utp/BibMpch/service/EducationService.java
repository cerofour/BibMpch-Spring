package pe.edu.utp.BibMpch.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.EducationDTO;
import pe.edu.utp.BibMpch.model.Education;
import pe.edu.utp.BibMpch.repository.EducationRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;

    public EducationDTO createEducation(EducationDTO educationDTO) {
        Education education = educationDTO.toEntity();
        Education savedEducation = educationRepository.save(education);
        return new EducationDTO(savedEducation);
    }
    public List<EducationDTO> getAllEducations() {
        List<Education> educations = (List<Education>) educationRepository.findAll();
        return EducationDTO.fromEntityList(educations);
    }
    public EducationDTO getEducationById(Short id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Education not found with id: " + id));
        return new EducationDTO(education);
    }
    public EducationDTO updateEducation(Short id, EducationDTO educationDTO) {
        Education existingEducation = educationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Education not found with id: " + id));

        existingEducation.setEducationName(educationDTO.getEducationName());

        Education updatedEducation = educationRepository.save(existingEducation);
        return new EducationDTO(updatedEducation);
    }
    public void deleteEducationById(Short id) {
        if (!educationRepository.existsById(id)) {
            throw new EntityNotFoundException("Education not found with id: " + id);
        }
        educationRepository.deleteById(id);
    }
}
