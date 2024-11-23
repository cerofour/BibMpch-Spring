package pe.edu.utp.BibMpch.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.image")
public class ImageConfiguration {
    private String uploadDirCustomer;
    private String uploadDirText;
    private String allowedExtension;
    private String endpointCustomer;
    private String endpointText;
}
