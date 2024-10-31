package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_cliente")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clie_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "clie_usuario_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "clie_direccion_id")
    private Address address;

    @Column(name = "clie_correo")
    private String email;

    @OneToOne
    @JoinColumn(name = "clie_carnet_id")
    private Carnet carnet;

    @ManyToOne
    @JoinColumn(name = "clie_nivel_educativo_id")
    private Education education;
}