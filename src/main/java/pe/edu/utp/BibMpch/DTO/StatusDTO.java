package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    private Long id;
    private String statusName;
    private boolean isActive;

    public StatusDTO(Status s) {
        this.id = s.getId();
        this.statusName = s.getStatusName();
        this.isActive = s.isActive();
    }
}