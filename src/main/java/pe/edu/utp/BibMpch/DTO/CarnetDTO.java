package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Carnet;
import pe.edu.utp.BibMpch.model.Status;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarnetDTO {
    private Long id;
    private Short statusId;
    private String statusName;
    private String code;
    private LocalDate carnetIssuanceDate;
    private LocalDate carnetExpirationDate;

    public CarnetDTO(Carnet carnet) {
        this.id = carnet.getId();
        this.statusId = carnet.getStatus().getId();
        this.statusName = carnet.getStatus().getStatusName();
        this.code = carnet.getCode();
        this.carnetIssuanceDate = carnet.getCarnetIssuanceDate();
        this.carnetExpirationDate = carnet.getCarnetExpirationDate();
    }
    public Carnet toEntity(Status status) {
        return Carnet.builder()
                .id(this.id)
                .status(status)
                .code(this.code)
                .carnetIssuanceDate(this.carnetIssuanceDate)
                .carnetExpirationDate(this.carnetExpirationDate)
                .build();
    }
    public static List<CarnetDTO> fromEntityList(List<Carnet> carnets) {
        return carnets.stream()
                .map(CarnetDTO::new)
                .collect(Collectors.toList());
    }
}