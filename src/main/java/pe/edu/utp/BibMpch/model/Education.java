package pe.edu.utp.BibMpch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "tb_nivel_educativo")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nied_id")
    private Short id;

    @Column(name = "nied_nombre")
    private String educationName;

    @OneToMany(mappedBy = "education", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Customer> customers;
}