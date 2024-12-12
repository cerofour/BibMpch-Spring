package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tb_estado_prestamo")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "espr_id")
    private Short id;

    @Column(name = "espr_nombre")
    private String name;

}
