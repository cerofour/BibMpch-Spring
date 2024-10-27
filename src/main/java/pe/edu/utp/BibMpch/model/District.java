package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_distrito")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dist_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dist_provincia_id")
    private Province province;

    @Column(name = "dist_nombre")
    private String districtName;
}
