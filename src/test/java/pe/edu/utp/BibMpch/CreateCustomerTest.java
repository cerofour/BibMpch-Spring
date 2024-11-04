package pe.edu.utp.BibMpch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import pe.edu.utp.BibMpch.DTO.AddressDTO;
import pe.edu.utp.BibMpch.DTO.CustomerDTO;
import pe.edu.utp.BibMpch.DTO.UserDTO;
import pe.edu.utp.BibMpch.model.Customer;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.repository.CarnetRepository;
import pe.edu.utp.BibMpch.repository.CustomerRepository;
import pe.edu.utp.BibMpch.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateCustomerTest extends BaseTest {
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final SecureRandom random = new SecureRandom();

	public String digitStringGenerator(int count) {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(8);

		for (int i = 0; i < count; i++) {
			int digit = random.nextInt(10); // Generates a random digit between 0 and 9
			sb.append(digit);
		}

		return sb.toString();
	}

	public String documentGenerator() {
		return digitStringGenerator(8);
	}

	public String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(CHARACTERS.length());
			sb.append(CHARACTERS.charAt(index));
		}
		return sb.toString();
	}

	@Test
	void shouldCreateCustomerUserAndCarnet() throws Exception {

		final String randomDocument = documentGenerator();
		final String randomName = generateRandomString(12);
		final String randomPlastname = generateRandomString(12);
		final String randomMlastname = generateRandomString(12);
		final String randomPhoneNumber = digitStringGenerator(9);
		final String randomEmail = generateRandomString(12) + "@gmail.com";

		final String requestBody = objectMapper.writeValueAsString(
				CustomerDTO.builder()
						.userData(UserDTO.builder()
								.document(randomDocument)
								.documentTypeId((short) 1)
								.name(randomName)
								.plastname(randomPlastname)
								.mlastname(randomMlastname)
								.phoneNumber(randomPhoneNumber)
								.roleId((short)1)
								.genderId((short)1)
								.psk(generateRandomString(24))
								.build())
						.addressData(AddressDTO.builder()
								.district((long)2)
								.address("Direccion Test")
								.build())
						.educationLevelId((short)1)
						.email(randomEmail)
						.build());

		mockMvc.perform(post("/api/v1/customers/")
						.header("Authorization", "Bearer " + token)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/users/")
						.header("Authorization", "Bearer " + token)
					.param("document", randomDocument))
				.andExpect(status().isOk());
	}

}
