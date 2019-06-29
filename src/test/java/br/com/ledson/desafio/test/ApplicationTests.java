package br.com.ledson.desafio.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ledson.desafio.Application;
import br.com.ledson.desafio.domain.Usuario;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTests {
	private static Long id;
	private final String login = "teste";
	private final String nome = "Teste da Silva";
	private final String cargo = "Testeiro";
	private final String telefone = "+55 (84) 83783-83783";

	private String credenciais = "ledson:kappa123";
	private String auth;
	private MockMvc mock;

	@Autowired
	private ObjectMapper object;

	@Autowired
	private WebApplicationContext wac;

	/*
	 * Inicializamos a string de autenticação e o mockmvc.
	 */
	@Before
	public void setUp() {
		auth = "Basic " + new String(Base64.getEncoder().encode(credenciais.getBytes()));
		mock = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/*
	 * Nossos testes seguem uma ordem, uma vez que não temos certeza sobre o
	 * estado atual do banco no momento da execução.
	 * Assim, a primeira coisa a fazer é inserir um usuário para ter certeza
	 * que existe alguma coisa no banco.
	 */
	@Test
	public void testAInsertUser() throws JsonProcessingException, Exception {
		Usuario usuario = new Usuario(0L, login, "USER", nome, cargo, telefone, null);

		/*
		 * Testando se o POST funciona.
		 */
		MvcResult result =
				mock.perform(
						post("/usuarios")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.header("Authorization", auth)
						.content(object.writeValueAsString(usuario)))
				.andExpect(status().is(201))
				.andReturn();

		String json = result.getResponse().getContentAsString();
		Usuario novoUsuario = new ObjectMapper().readValue(json, Usuario.class);
		id = novoUsuario.getId();

		/*
		 * Testando se o objeto retornado tem os parâmetros
		 * que pedimos para inserir.
		 */
		assertEquals(login, novoUsuario.getLogin());
		assertEquals(nome, novoUsuario.getNome());
		assertEquals(cargo, novoUsuario.getCargo());
		assertEquals(telefone, novoUsuario.getTelefone());
	}

	/*
	 * Se o teste anterior funcionou, temos pelo menos 1 usuário em banco.
	 * Esse teste pega os usuários e checa se temos alguém no banco.
	 */
	@Test
	public void testBGetUsers() throws JsonProcessingException, Exception {
		/*
		 * Testando se o GET funciona.
		 */
		MvcResult result =
				mock.perform(
						get("/usuarios")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.header("Authorization", auth))
				.andExpect(status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		List<Usuario> usuarios = new ObjectMapper().readValue(json, new TypeReference<List<Usuario>>() {});

		/*
		 * Testando se coseguimos pegar pelo menos um usuário.
		 */
		assertEquals(true, usuarios.size() > 0);
	}

	/*
	 * Agora vamos atualizar o usuário inserido no primeiro teste.
	 */
	@Test
	public void testCUpdateUser() throws JsonProcessingException, Exception {
		/*
		 * Parâmetros a seguem atualizados.
		 */
		final String novoCargo = "Testador";
		Usuario usuario = new Usuario(id, null, null, null, novoCargo, null, null);

		/*
		 * Testando se o PUT funciona.
		 */
		MvcResult result =
				mock.perform(
						put("/usuarios")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.header("Authorization", auth)
						.content(object.writeValueAsString(usuario)))
				.andExpect(status().is(201))
				.andReturn();

		String json = result.getResponse().getContentAsString();
		Usuario novoUsuario = new ObjectMapper().readValue(json, Usuario.class);

		/*
		 * Testando se o objeto retornado tem os parâmetros
		 * antigos e o parâmetro modificado.
		 */
		assertEquals(id, novoUsuario.getId());
		assertEquals(login, novoUsuario.getLogin());
		assertEquals(nome, novoUsuario.getNome());
		assertEquals(novoCargo, novoUsuario.getCargo());
		assertEquals(telefone, novoUsuario.getTelefone());
	}

	/*
	 * Finalizando a sequência de testes, iremos deletar o usuário
	 * inserido no primeiro teste.
	 */
	@Test
	public void testDDeleteUser() throws JsonProcessingException, Exception {
		/*
		 * Testando se o DELETE funciona.
		 */
		mock.perform(
				delete("/usuarios/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", auth))
		.andExpect(status().is(204));
	}
}