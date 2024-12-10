package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.model.Roles;
import pe.edu.utp.BibMpch.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping(value = "/")
    @SuppressWarnings("unused")
    public ResponseEntity<List<Roles>> getAllRoles() {
        List<Roles> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return ResponseEntity.ok(roles);
    }

    @GetMapping(value = "/get")
    @SuppressWarnings("unused")
    public ResponseEntity<Roles> getRole(
            @RequestParam(name = "id") Long id) {

        Roles role = roleRepository.findById(id).get();

        return (role != null) ? ResponseEntity.ok(role)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
