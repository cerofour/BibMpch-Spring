package pe.edu.utp.BibMpch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación BibMpch.
 *
 * Esta clase es el punto de entrada para la aplicación basada en Spring Boot.
 * Se encarga de inicializar el contexto de la aplicación y arrancar los
 * servicios configurados.
 *
 * <p>Características del proyecto:</p>
 * <ul>
 *   <li>Framework: Spring Boot (versión 3.3.4).</li>
 *   <li>Gestor de dependencias: Maven.</li>
 *   <li>Propósito: Gestión de bibliotecas y préstamos.</li>
 * </ul>
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *   <li>Cargar los beans configurados en la aplicación.</li>
 *   <li>Configurar y ejecutar el contexto de Spring.</li>
 * </ul>Llacsahuanga, Huanca, Lopez, Vasquez
 *
 * @author Llacsahuanga
 * @version 1.0
 * @since 11/10/2024
 */
@SpringBootApplication
public class BibMpchApplication {
	/**
	 * Método principal (main) que inicia la ejecución de la aplicación.
	 *
	 * Este método invoca a SpringApplication.run para inicializar el
	 * contexto de Spring y arrancar todos los componentes necesarios.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BibMpchApplication.class, args);
	}


}
