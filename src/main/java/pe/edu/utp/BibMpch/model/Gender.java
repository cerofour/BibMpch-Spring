package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_genero")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gene_id")
    private Short id;

    @Column(name = "gene_nombre")
    private String genderName;
}
