package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tb_rol_usuario")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rolu_id")
    private Short id;

    @Column(name = "rolu_nombre")
    private String role;

}
