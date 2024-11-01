package pe.edu.utp.BibMpch.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TextDTO {
	private String title;
	private LocalDate publicationDate;
	private Short numPages;
	private Short edition;
	private Short volume;

	private Long editorialId;
	private Long typeId;

	private Set<Long> authors;
}
