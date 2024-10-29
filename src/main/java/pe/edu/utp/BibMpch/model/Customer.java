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

    @Column(name = "clie_nombre")
    private String name;

    @Column(name = "clie_apellido_paterno")
    private String pLastName;

    @Column(name = "clie_apellido_materno")
    private String mLastName;

    @ManyToOne
    @JoinColumn(name = "clie_genero_id")
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "clie_direccion_id")
    private Address address;

    @Column(name = "clie_telefono")
    private String phoneNumber;

    @Column(name = "clie_correo")
    private String email;

    @OneToOne
    @JoinColumn(name = "clie_carnet_id")
    private Carnet carnet;
}