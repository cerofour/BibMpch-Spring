package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Status;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusDTO {
    private Short id;
    //NotNull y Size
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
        return statuses.stream()
                .map(StatusDTO::new)
                .collect(Collectors.toList());
    }
}