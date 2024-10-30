package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Carnet;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarnetDTO {
    private Long id;
    private Short statusDTO;
    private String code;
    private LocalDate carnetIssuanceDate;
    private LocalDate carnetExpirationDate;

    public CarnetDTO(Carnet c) {
        this.id = c.getId();
        this.statusDTO = c.getStatus().getId();
        this.code = c.getCode();
        this.carnetIssuanceDate = c.getCarnetIssuanceDate();
        this.carnetExpirationDate = c.getCarnetExpirationDate();
    }
}