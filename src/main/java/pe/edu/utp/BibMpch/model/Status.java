package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_tipo_estado")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ties_id")
    private Short id;

    @Column(name = "ties_tipo")
    private String statusName;

    @Column(name = "ties_activo")
    private boolean isActive;
}