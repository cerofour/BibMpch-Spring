package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.CodeTextualResource;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link CodeTextualResource}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link CodeTextualResource}.
 *
 * <p><strong>Métodos personalizados:</strong></p>
 * <ul>
 *   <li>{@link #findByBaseCodeAndExemplaryCode(String, Integer)}: Busca un recurso textual
 *       por su código base y código ejemplar.</li>
 *   <li>{@link #findByBaseCode(String)}: Busca todos los recursos textuales asociados a un código base.</li>
 * </ul>
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas específicas relacionadas con recursos textuales por códigos base y ejemplares,
 * además de utilizar los métodos estándar proporcionados por Spring Data.
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
public interface CodeTextualResourceRepository extends CrudRepository<CodeTextualResource, Long> {
    Optional<CodeTextualResource> findByBaseCodeAndExemplaryCode(String baseCode, Integer exemplaryCode);
    List<CodeTextualResource> findByBaseCode(String baseCode);
}
