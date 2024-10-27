package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_region")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regi_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "regi_pais_id")
    private Country country;

    @Column(name = "regi_nombre")
    private String regionName;
}
