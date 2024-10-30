package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Status;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    private Short id;
    private String statusName;
    private boolean isActive;

    public StatusDTO(Status status) {
        this.id = status.getId();
        this.statusName = status.getStatusName();
        this.isActive = status.isActive();
    }
    public Status toEntity() {
        Status status = new Status();
        status.setId(this.id);
        status.setStatusName(this.statusName);
        status.setActive(this.isActive);
        return status;
    }
    public static List<StatusDTO> fromEntityList(List<Status> statuses) {
        List<StatusDTO> statusDTOs = new ArrayList<>();
        for (Status status : statuses) {
            statusDTOs.add(new StatusDTO(status));
        }
        return statusDTOs;
    }
}