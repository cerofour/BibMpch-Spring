package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_genero")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gene_id")
    private Long id;

    @Column(name = "gene_nombre")
    private String genderName;
}