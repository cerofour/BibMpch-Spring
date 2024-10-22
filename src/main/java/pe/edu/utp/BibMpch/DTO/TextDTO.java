package pe.edu.utp.BibMpch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class TextDTO {
	private String title;
	private LocalDate publicationDate;
	private Short numPages;
	private Short edition = 0;
	private Short volume = 0;

	private String editorialName;
	private String textType;
}
