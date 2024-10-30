package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Gender;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenderDTO {
    private Short id;
    private String genderName;

    public GenderDTO(Gender gender) {
        this.id = gender.getId();
        this.genderName = gender.getGenderName();
    }
    public Gender toEntity() {
        Gender gender = new Gender();
        gender.setId(this.id);
        gender.setGenderName(this.genderName);
        return gender;
    }
    public static List<GenderDTO> fromEntityList(List<Gender> genders) {
        return genders.stream()
                .map(GenderDTO::new)
                .collect(Collectors.toList());
    }
}
