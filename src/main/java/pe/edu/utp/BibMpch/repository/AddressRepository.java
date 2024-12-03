package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Address;
import pe.edu.utp.BibMpch.model.District;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link Address}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link Address}.
 *
 * <p><strong>Métodos personalizados:</strong></p>
 * <ul>
 *   <li>{@link #findByAddressAndDistrict(String, District)}: Busca una dirección específica
 *       en un distrito dado.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas relacionadas con direcciones y distritos, utilizando métodos prediseñados
 * y personalizados.
 *
 * @author Vasquez
 * @version 1.0
 * @since 29/10/2024
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
    Optional<Address> findByAddressAndDistrict(String addressStr, District district);
}
