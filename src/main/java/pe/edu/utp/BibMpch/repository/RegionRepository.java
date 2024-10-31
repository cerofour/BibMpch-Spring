package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Country;
import pe.edu.utp.BibMpch.model.Region;

import java.util.Optional;

public interface RegionRepository extends CrudRepository<Region, Long> {
    Optional<Region> findByRegionNameAndCountry(String regionName, Country country);
}