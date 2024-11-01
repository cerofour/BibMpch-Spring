package pe.edu.utp.BibMpch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pe.edu.utp.BibMpch.DTO.LoginUserDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Abstract class that performs the login test first, then extracts the JWT
 * token to be used in any other tests that may require it.
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseTest {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected final ObjectMapper objectMapper = new ObjectMapper();

	protected static String token;

	@Test
	public void loginWithInvalidCredentials() throws Exception {

		final String loginBody = objectMapper.writeValueAsString(
				LoginUserDTO.builder()
						.document("00011122")
						.psk("test13") // wrong password
						.build()
		);

		this.mockMvc.perform(post("/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(loginBody))
				.andExpect(status().is4xxClientError());
	}

	@BeforeAll
	public static void authenticateAndGetToken(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
		final String loginBody = objectMapper.writeValueAsString(
				LoginUserDTO.builder()
						.document("00011122")
						.psk("test123") // wrong password
						.build()
		);
		if (token == null) {
			MvcResult result = mockMvc.perform(post("/auth/login")
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(loginBody))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.token").exists())
					.andReturn();

			token = JsonPath.parse(result.getResponse().getContentAsString())
					.read("$.token");
		}
	}
}
