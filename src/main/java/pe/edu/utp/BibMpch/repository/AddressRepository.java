package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Address;
import pe.edu.utp.BibMpch.model.District;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {
    Optional<Address> findByAddressAndDistrict(String addressName, District district);
}
