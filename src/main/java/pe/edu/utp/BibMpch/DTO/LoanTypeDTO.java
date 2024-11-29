package pe.edu.utp.BibMpch.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LoanTypeDTO {
    private Short id;
    private String tipoPrestamo;
}
