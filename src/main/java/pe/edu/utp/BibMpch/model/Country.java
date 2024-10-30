package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_pais")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pais_id")
    private Short id;

    @Column(name = "pais_nombre")
    private String countryName;
}