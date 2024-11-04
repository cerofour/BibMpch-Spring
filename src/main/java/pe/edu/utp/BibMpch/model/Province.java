package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Table(name = "tb_provincia")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prov_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prov_region_id")
    private Region region;

    @Column(name = "prov_nombre")
    private String provinceName;
}
