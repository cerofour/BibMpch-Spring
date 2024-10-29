package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenderDTO {
    private Long id;
    private String genderName;

    public GenderDTO(Gender g) {
        this.id = g.getId();
        this.genderName = g.getGenderName();
    }
}