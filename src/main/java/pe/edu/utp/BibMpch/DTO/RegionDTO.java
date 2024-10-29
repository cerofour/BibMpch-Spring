package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Region;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {
    private Long id;
    private String regionName;

    public RegionDTO(Region r) {
        this.id = r.getId();
        this.regionName = r.getRegionName();
    }
}