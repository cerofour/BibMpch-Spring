package pe.edu.utp.BibMpch.service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.utp.BibMpch.configuration.ImageConfiguration;
import pe.edu.utp.BibMpch.model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ImageService {
	private final ImageConfiguration imageConfiguration;

	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toLowerCase();
	}

	/**
	 * Guarda la imagen del cliente en el directorio respectivo.
	 * @param imageFile
	 * @param customerDoc
	 * @return El path de la imagen del cliente.
	 * @throws Exception
	 */
	public String saveCustomerImage(MultipartFile imageFile, String customerDoc) throws Exception {
		Path directoryPath = Paths.get(imageConfiguration.getUploadDirCustomer());

		try {

			String fileExtension = getFileExtension(
					Objects.requireNonNull(imageFile.getOriginalFilename())
			);

			if(!fileExtension.equals(imageConfiguration.getAllowedExtension()))
				throw new BadRequestException("Formato de imagen no admitido.");

			if (!Files.exists(directoryPath))
				Files.createDirectories(directoryPath);

			String fileName = "%s.%s".formatted(
					customerDoc, imageConfiguration.getAllowedExtension());

			Path filePath = directoryPath.resolve(fileName);

			Files.write(filePath, imageFile.getBytes());

			return "%s/%s".formatted(
					imageConfiguration.getEndpointCustomer(), customerDoc);
		} catch (IOException e) {
			throw new IOException("No se pudo guardar la imagen enviada.");
		}

	}
}
