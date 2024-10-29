package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.BibMpch.model.Card;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private Long id;
    private String statusDTO;
    private String code;
    private LocalDate cardIssuanceDate;
    private LocalDate cardExpirationDate;

    public CardDTO(Card c) {
        this.id = c.getId();
        this.statusDTO = c.getStatus().getStatusName();
        this.code = c.getCode();
        this.cardIssuanceDate = c.getCardIssuanceDate();
        this.cardExpirationDate = c.getCardExpirationDate();
    }
}