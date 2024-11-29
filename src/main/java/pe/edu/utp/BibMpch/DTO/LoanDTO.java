package pe.edu.utp.BibMpch.DTO;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LoanDTO {
    private Integer idLoan;
    private Long idUser;
    private Long idCode;
    private Short idTypeLoan;
    private Short idStatusLoan;
    private LocalDate initialDate;
    private LocalDate endDate;
    private LocalDate scheduledDate;
}

