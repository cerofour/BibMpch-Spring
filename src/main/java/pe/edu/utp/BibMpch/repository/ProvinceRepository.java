package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Province;
import pe.edu.utp.BibMpch.model.Region;

import java.util.Optional;

public interface ProvinceRepository extends CrudRepository<Province, Long> {
    Optional<Province> findByProvinceNameAndRegion(String provinceName, Region region);
}
