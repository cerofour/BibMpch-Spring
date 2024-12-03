package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.District;
import pe.edu.utp.BibMpch.model.Province;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link District}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link District}.
 *
 * <p><strong>Métodos personalizados:</strong></p>
 * <ul>
 *   <li>{@link #findByDistrictNameAndProvince(String, Province)}: Busca un distrito por su nombre
 *       y su provincia asociada.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas específicas relacionadas con distritos, además de utilizar los métodos estándar
 * proporcionados por Spring Data.
 *
 * @author Vasquez
 * @version 1.0
 * @since 29/10/2024
 */
public interface DistrictRepository extends CrudRepository<District, Long> {
    Optional<District> findByDistrictNameAndProvince(String districtName, Province province);
}
