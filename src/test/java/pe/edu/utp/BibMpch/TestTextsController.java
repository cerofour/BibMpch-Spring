package pe.edu.utp.BibMpch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTextsController extends BaseTest {

	@Test
	public void getAllTextsWithAuthentication() throws Exception {
		mockMvc.perform(get("/api/v1/texts/")
					.header("Authorization", "Bearer " + token)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
