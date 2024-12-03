package pe.edu.utp.BibMpch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.DTO.LoanDTO;
import pe.edu.utp.BibMpch.exceptions.ResourceNotFoundException;
import pe.edu.utp.BibMpch.model.CodeTextualResource;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.model.Loan;
import pe.edu.utp.BibMpch.repository.CodeTextualResourceRepository;
import pe.edu.utp.BibMpch.repository.CustomerRepository;
import pe.edu.utp.BibMpch.repository.LoanRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Loan}.
 *
 * <p>Proporciona métodos para crear, obtener, actualizar y manejar los préstamos registrados en el sistema.</p>
 *
 * <p><strong>Dependencias:</strong></p>
 * <ul>
 *   <li>{@link LoanRepository}: Repositorio para las operaciones de persistencia de préstamos.</li>
 *   <li>{@link CodeTextualResourceRepository}: Repositorio para gestionar los recursos textuales asociados.</li>
 *   <li>{@link CustomerRepository}: Repositorio para gestionar los clientes asociados a los préstamos.</li>
 * </ul>
 *
 * @author Huanca
 * @version 1.0
 * @since 28/11/2024
 */
@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final CodeTextualResourceRepository codeTextualResourceRepository;
    private final CustomerRepository customerRepository;

    /**
     * Recupera todos los préstamos registrados en el sistema.
     *
     * @return Una lista de entidades {@link Loan}.
     */
    public List<Loan> getAllLoans() {
        List<Loan> result = new ArrayList<>();

        loanRepository.findAll().forEach(result::add);

        return result;
    }

    /**
     * Obtiene un préstamo específico por su identificador.
     *
     * @param id El identificador del préstamo.
     * @return La entidad {@link Loan} si existe; de lo contrario, <code>null</code>.
     */
    public Loan getById(Long id) {
        return loanRepository.findById(id)
                .orElse(null);
    }

    /**
     * Recupera todos los préstamos asociados a un cliente específico.
     *
     * @param idCustomer El identificador del cliente.
     * @return Una lista de entidades {@link Loan} asociadas al cliente.
     * @throws ResourceNotFoundException Si el cliente no se encuentra registrado.
     */
    public List<Loan> getByIdCustomer(Long idCustomer) throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(idCustomer)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + idCustomer));

        return loanRepository.findByCustomer(customer);
    }

    /**
     * Crea un nuevo préstamo basado en un DTO.
     *
     * @param loanDTO DTO que contiene los datos del préstamo.
     * @return La entidad {@link Loan} creada y persistida.
     * @throws ResourceNotFoundException Si el cliente o el recurso textual asociado no existen.
     */
    public Loan create(LoanDTO loanDTO) throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(loanDTO.getIdCustomer())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + loanDTO.getIdCustomer()));

        CodeTextualResource ctr = codeTextualResourceRepository.findById(loanDTO.getIdCode())
                .orElseThrow(() -> new ResourceNotFoundException("Textual resource not found with ID: " + loanDTO.getIdCode()));

        Loan loan = Loan.builder()
                .customer(customer)
                .codeTextualResource(ctr)
                .idTypeLoan(loanDTO.getIdTypeLoan())
                .idStatusLoan(loanDTO.getIdStatusLoan())
                .initialDate(loanDTO.getInitialDate())
                .endDate(loanDTO.getEndDate())
                .scheduledDate(loanDTO.getScheduledDate())
                .build();
        return loanRepository.save(loan);
    }

    /**
     * Actualiza el estado de un préstamo.
     *
     * @param idLoan       El identificador del préstamo.
     * @param idStatusLoan El nuevo estado del préstamo.
     * @return La entidad {@link Loan} actualizada.
     * @throws ResourceNotFoundException Si el préstamo no se encuentra registrado.
     */
    public Loan updateStatus(Long idLoan, Short idStatusLoan) throws ResourceNotFoundException {
        Loan existingLoan = loanRepository.findById(idLoan)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + idLoan));

        existingLoan.setIdStatusLoan(idStatusLoan);

        return loanRepository.save(existingLoan);
    }

    /**
     * Actualiza un préstamo existente con los datos proporcionados en el DTO.
     *
     * @param id      El identificador del préstamo.
     * @param loanDTO DTO que contiene los datos actualizados del préstamo.
     * @return La entidad {@link Loan} actualizada.
     * @throws ResourceNotFoundException Si el préstamo, cliente o recurso textual asociado no se encuentran registrados.
     */
    public Loan update(Long id, LoanDTO loanDTO) throws ResourceNotFoundException {

        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));

        if (loanDTO.getIdCustomer() != null) {
            Customer customer = customerRepository.findById(loanDTO.getIdCustomer())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + loanDTO.getIdCustomer()));
            existingLoan.setCustomer(customer);
        }

        if (loanDTO.getIdCode() != null) {
            CodeTextualResource ctr = codeTextualResourceRepository.findById(loanDTO.getIdCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Textual resource not found with ID: " + loanDTO.getIdCode()));
            existingLoan.setCodeTextualResource(ctr);
        }

        if (loanDTO.getIdTypeLoan() != null) {
            existingLoan.setIdTypeLoan(loanDTO.getIdTypeLoan());
        }
        if (loanDTO.getIdStatusLoan() != null) {
            existingLoan.setIdStatusLoan(loanDTO.getIdStatusLoan());
        }
        if (loanDTO.getInitialDate() != null) {
            existingLoan.setInitialDate(loanDTO.getInitialDate());
        }
        if (loanDTO.getEndDate() != null) {
            existingLoan.setEndDate(loanDTO.getEndDate());
        }
        if (loanDTO.getScheduledDate() != null) {
            existingLoan.setScheduledDate(loanDTO.getScheduledDate());
        }

        return loanRepository.save(existingLoan);
    }


}
