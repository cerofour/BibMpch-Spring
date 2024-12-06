package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Table(name = "tb_registro_accion_usuario")
@Entity
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class RegisterActions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reau_id")
    private Long id;

    @Column(name = "reau_usuario_id")
    private Long userId;

    @Column(name = "reau_detalle")
    private String detail;

    @Column(name = "reau_fec_hora")
    private OffsetDateTime fec;

    @Column(name = "reau_direccion_ip")
    private String ip;
}
