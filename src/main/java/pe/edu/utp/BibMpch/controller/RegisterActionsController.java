package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.RegisterActions;
import pe.edu.utp.BibMpch.repository.RegisterActionsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/logs")
@AllArgsConstructor
public class RegisterActionsController {

    private RegisterActionsRepository registerActionsRepository;


    @GetMapping(value = "/")
    public ResponseEntity<List<RegisterActions>> get() {
        List<RegisterActions> ra = new ArrayList<>();

        registerActionsRepository.findAll().forEach(ra::add);

        return ResponseEntity.ok(ra);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<List<RegisterActions>> getByUser(@PathParam("id") Optional<Long> id) {
        return ResponseEntity.ok(registerActionsRepository.findByUserId(id.get()));
    }
}
