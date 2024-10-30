package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.District;
import pe.edu.utp.BibMpch.model.Province;
import java.util.Optional;

public interface DistrictRepository extends CrudRepository<District, Long> {
    Optional<District> findByDistrictNameAndProvince(String districtName, Province province);
}
