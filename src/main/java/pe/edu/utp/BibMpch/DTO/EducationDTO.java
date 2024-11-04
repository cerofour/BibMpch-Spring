package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Education;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationDTO {
    private Short id;
    private String educationName;

    public EducationDTO(Education education) {
        this.id = education.getId();
        this.educationName = education.getEducationName();
    }

    public Education toEntity() {
        Education education = new Education();
        education.setId(this.id);
        education.setEducationName(this.educationName);
        return education;
    }

    public static List<EducationDTO> fromEntityList(List<Education> educations) {
        return educations.stream()
                .map(EducationDTO::new)
                .collect(Collectors.toList());
    }
}
