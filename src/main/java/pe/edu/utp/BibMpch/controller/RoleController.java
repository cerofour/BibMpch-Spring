package pe.edu.utp.BibMpch.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.BibMpch.authorization.UserRole;
import pe.edu.utp.BibMpch.authorization.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/roles")
@AllArgsConstructor
public class RoleController {

    private final UserRoleRepository userRoleRepository;

    @GetMapping(value = "/")
    @SuppressWarnings("unused")
    public ResponseEntity<List<UserRole>> getAllRoles() {
        List<UserRole> roles = new ArrayList<>();
        userRoleRepository.findAll().forEach(roles::add);
        return ResponseEntity.ok(roles);
    }

    @GetMapping(value = "/get")
    @SuppressWarnings("unused")
    public ResponseEntity<UserRole> getRole(
            @RequestParam(name = "id") Short id) {

        UserRole role = userRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado."));

        return (role != null) ? ResponseEntity.ok(role)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
