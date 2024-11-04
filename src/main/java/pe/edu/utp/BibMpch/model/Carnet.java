package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "tb_carnet")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carnet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carn_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carn_tipo_estado_id")
    private Status status;

    @Column(name = "carn_codigo")
    private String code;

    @Column(name = "carn_fec_emision")
    private LocalDate carnetIssuanceDate;

    @Column(name = "carn_fec_vencimiento")
    private LocalDate carnetExpirationDate;
}