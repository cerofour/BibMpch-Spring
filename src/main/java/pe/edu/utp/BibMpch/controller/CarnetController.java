package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.Carnet;
import pe.edu.utp.BibMpch.service.CarnetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carnets")
@AllArgsConstructor
public class CarnetController {

    private final CarnetService carnetService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Carnet>> getCarnets() {
        return ResponseEntity.ok(carnetService.getAllCarnets());
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Carnet> getById(@PathParam("id") Optional<Long> id) throws BadRequestException {

        Carnet customer = id.map(carnetService::getCarnetById)
                .orElseThrow(BadRequestException::new);

        return ResponseEntity.ok(customer);
    }

}
