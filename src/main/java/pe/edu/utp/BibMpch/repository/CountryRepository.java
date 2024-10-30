package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Country;

public interface CountryRepository extends CrudRepository<Country, Short> {
}
