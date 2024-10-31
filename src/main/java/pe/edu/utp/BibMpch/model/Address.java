package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_direccion_cliente")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dicl_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dicl_distrito_id")
    private District district;

    @Column(name = "dicl_direccion")
    private String address;
}
