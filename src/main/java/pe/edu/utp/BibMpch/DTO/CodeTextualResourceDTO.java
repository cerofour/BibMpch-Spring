package pe.edu.utp.BibMpch.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CodeTextualResourceDTO {
    private Long id;
    private TextDTO baseCode;
    private Integer exemplaryCode;
    private Boolean available;
}