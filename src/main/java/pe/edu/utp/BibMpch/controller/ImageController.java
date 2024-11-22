package pe.edu.utp.BibMpch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.configuration.ImageConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageConfiguration imageConfiguration;

    @GetMapping("/customer/{document}")
    public ResponseEntity<Resource> getCustomerImage(@PathVariable String document) {
        return getImage(document, imageConfiguration.getUploadDirCustomer());
    }

    @GetMapping("/text/{id}")
    public ResponseEntity<Resource> getTextImage(@PathVariable String id) {
        return getImage(id, imageConfiguration.getUploadDirText());
    }

    private ResponseEntity<Resource> getImage(String name, String imagePath) {
        try {

            Path imagePath_ = Paths.get(imagePath)
                    .resolve("%s.%s".formatted(name, imageConfiguration.getAllowedExtension()))
                    .normalize();

            Resource resource = new UrlResource(imagePath_.toUri());

            if (!resource.exists() || !resource.isReadable())
                return ResponseEntity.notFound().build();

            String contentType = Files.probeContentType(imagePath_);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"%s\"".formatted(resource.getFilename()))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

    }
}
