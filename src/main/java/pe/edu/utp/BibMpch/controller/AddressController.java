package pe.edu.utp.BibMpch.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.BibMpch.model.Address;
import pe.edu.utp.BibMpch.repository.AddressRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/addresses")
@AllArgsConstructor
@SuppressWarnings("unused")
public class AddressController {
}
