package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Province;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDTO {
    private Long id;
    private Long region;
    private String provinceName;

    public ProvinceDTO(Province p) {
        this.id = p.getId();
        this.region = p.getRegion().getId();
        this.provinceName = p.getProvinceName();
    }
}