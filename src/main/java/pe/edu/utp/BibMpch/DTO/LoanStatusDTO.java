package pe.edu.utp.BibMpch.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LoanStatusDTO {
    private Short id;
    private String nombre;
}
