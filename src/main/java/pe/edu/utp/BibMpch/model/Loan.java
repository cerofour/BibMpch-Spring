package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "tb_prestamo")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pres_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pres_cliente_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "pres_recurso_textual_codigo_id")
    private CodeTextualResource codeTextualResource;

    @Column(name = "pres_tipo_prestamo_id")
    private Short idTypeLoan;

    @Column(name = "pres_estado_prestamo_id")
    private Short idStatusLoan;

    @Column(name = "pres_fec_inicial")
    private LocalDate initialDate;

    @Column(name = "pres_fec_final")
    private LocalDate endDate;

    @Column(name = "pres_fec_programada")
    private LocalDate scheduledDate;

}
