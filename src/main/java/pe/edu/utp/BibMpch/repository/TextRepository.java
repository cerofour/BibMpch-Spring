package pe.edu.utp.BibMpch.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.BibMpch.model.Text;

/**
 * Repositorio para gestionar las operaciones de persistencia relacionadas con la entidad {@link Text}.
 *
 * <p>Este repositorio extiende {@link CrudRepository}, proporcionando métodos estándar
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad {@link Text}.
 *
 * <p><strong>Utilidad:</strong></p>
 * Este repositorio facilita la interacción con la base de datos para realizar
 * consultas relacionadas con recursos textuales, utilizando los métodos prediseñados
 * proporcionados por Spring Data.
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 27/10/2024
 */
public interface TextRepository extends CrudRepository<Text, Long> {
}
