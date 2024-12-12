package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tb_recurso_textual_codigo")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeTextualResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reco_id")
    private Long id;

    @Column(name = "reco_rete_codigo_base")
    private String baseCode;

    @Column(name = "reco_codigo_ejemplar")
    private Integer exemplaryCode;

    @Column(name = "reco_disponible")
    private Boolean available;

}
