package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/addresses")
@PreAuthorize("hasAnyRole('Administrador', 'Bibliotecario')")
@AllArgsConstructor
@SuppressWarnings("unused")
public class AddressController {
}
