---
title: Testing
subtitle: Guía para la elaboración de tests a controladores.
authors:
  - name: Diego Llacsahuanga
  - date: 01/11/2024
---

# Cómo escribir tests

Para realizar un test específico se debe crear un nueva clase en la carpeta `src/test/java/pe/edu/utp/BibMpch`, la cual
deberá heredar de la clase abstracta `BaseTest`.

## BaseTest

Debido a que utilizamos autenticacion para la mayor parte de los endpoints de la api, es necesario enviar un header
`Authorization: "Bearer + {jwt-token}"` en cada request que hagamos. La clase BaseTest se encarga de realizar este
login previo y de extraer el token JWT para que sea utilizado en los demás tests.

## Ejemplo de testing a un controlador

El siguiente ejemplo hace un test al endpoint `"/texts/"` con una solicitud GET, y envía el token de autenticacion para
recibir acceso a ese endpoint.

```java
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
```

En este test se utilizó el método `get` de `MockMvcRequestBuilder`, se pueden utilizar también los
métodos `post` y utilizar opciones de configuración de las requests.

```java
// enviar un request body
String requestBody = "...";

// ...
mockMvc.perform(get("...")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(requestBody));
// ...
```

# Más información

- [AssertJ](https://assertj.github.io/doc/)
- Documentación de [MockMvcRequestBuilder](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockMvcRequestBuilders.html)
- [MockMvcResultMatchers](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/MockMvcResultMatchers.html)
para testear diferentes valores de la respuesta de una request.