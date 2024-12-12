package pe.edu.utp.BibMpch.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.CodeTextualResource;
import pe.edu.utp.BibMpch.repository.CodeTextualResourceRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/text_codes")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
public class CodeTextualResourceController {

    @Autowired
    private CodeTextualResourceRepository codeTextualResourceRepository;

    @GetMapping(value = "/")
    @SuppressWarnings("unused")
    public ResponseEntity<List<CodeTextualResource>> getAllCodes() {
        List<CodeTextualResource> result = new ArrayList<>();

        codeTextualResourceRepository.findAll().forEach(result::add);

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<List<CodeTextualResource>> getById(@PathParam("baseCode") String baseCode) {

        List<CodeTextualResource> ct = codeTextualResourceRepository.findByBaseCode(baseCode);

        return (ct != null) ? ResponseEntity.ok(ct)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(value = "/get_available")
    public ResponseEntity<List<CodeTextualResource>> getAvailableCodesById(@PathParam("baseCode") String baseCode) {

        List<CodeTextualResource> ct = codeTextualResourceRepository
                .findByBaseCodeAndAvailable(baseCode, true);

        return (ct != null) ? ResponseEntity.ok(ct)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
