package br.com.estoque;

import br.com.estoque.dao.EstoqueDAO;
import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.dao.UsuarioDAO;
import br.com.estoque.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:estoque_test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "app.jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=",
        "app.admin.email=",
        "app.admin.password="
})
class EstoqueApiIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private UsuarioDAO usuarioDAO;
    @Autowired private ProdutoDAO produtoDAO;
    @Autowired private EstoqueDAO estoqueDAO;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeEach
    void preparar() {
        produtoDAO.deleteAll();
        estoqueDAO.deleteAll();
        usuarioDAO.deleteAll();
        criarUsuario("admin@teste.com", "ADMIN");
        criarUsuario("user@teste.com", "USER");
    }

    private void criarUsuario(String email, String role) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode("senha12345"));
        usuario.setRole(role);
        usuarioDAO.save(usuario);
    }

    private String token(String email) throws Exception {
        String resposta = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\",\"senha\":\"senha12345\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("Bearer"))
                .andReturn().getResponse().getContentAsString();
        return "Bearer " + new com.fasterxml.jackson.databind.ObjectMapper()
                .readTree(resposta).get("token").asText();
    }

    @Test
    void exigeAutenticacaoEPermissao() throws Exception {
        mockMvc.perform(post("/estoques")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Principal\",\"localizacao\":\"Blumenau\"}"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/estoques")
                        .header("Authorization", token("user@teste.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Principal\",\"localizacao\":\"Blumenau\"}"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/estoques")
                        .header("Authorization", "Bearer token-invalido"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void cadastraBuscaAtualizaEExcluiEstoque() throws Exception {
        String admin = token("admin@teste.com");

        String resposta = mockMvc.perform(post("/estoques")
                        .header("Authorization", admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Principal\",\"localizacao\":\"Blumenau\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Principal"))
                .andReturn().getResponse().getContentAsString();

        int id = new com.fasterxml.jackson.databind.ObjectMapper().readTree(resposta).get("id").asInt();

        mockMvc.perform(get("/estoques/busca").param("nome", "prin")
                        .header("Authorization", admin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id));

        mockMvc.perform(get("/estoques/localizacao").param("localizacao", "blum")
                        .header("Authorization", admin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id));

        mockMvc.perform(put("/estoques/" + id)
                        .header("Authorization", admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Secundário\",\"localizacao\":\"Gaspar\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Secundário"));

        mockMvc.perform(delete("/estoques/" + id)
                        .header("Authorization", admin))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/estoques/" + id)
                        .header("Authorization", admin))
                .andExpect(status().isNotFound());
    }

    @Test
    void protegeExclusaoDeEstoqueComProdutos() throws Exception {
        String admin = token("admin@teste.com");
        String estoque = mockMvc.perform(post("/estoques")
                        .header("Authorization", admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Principal\",\"localizacao\":\"Blumenau\"}"))
                .andReturn().getResponse().getContentAsString();
        int id = new com.fasterxml.jackson.databind.ObjectMapper().readTree(estoque).get("id").asInt();

        mockMvc.perform(post("/produtos")
                        .header("Authorization", admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Mouse\",\"descricao\":\"Sem fio\",\"preco\":50.0,\"quantidade\":10,\"quantidadeMinima\":2,\"estoqueId\":" + id + "}"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/estoques/" + id)
                        .header("Authorization", admin))
                .andExpect(status().isConflict());

        mockMvc.perform(get("/produtos/busca").param("nome", "mou")
                        .header("Authorization", admin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estoque.id").value(id));
    }

    @Test
    void validaDadosEConsultaUsuariosSemExporSenhas() throws Exception {
        String admin = token("admin@teste.com");

        mockMvc.perform(post("/estoques")
                        .header("Authorization", admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\",\"localizacao\":\"Blumenau\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/usuarios/busca").param("email", "ADMIN")
                        .header("Authorization", admin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("admin@teste.com"))
                .andExpect(jsonPath("$[0].senha").doesNotExist());

        mockMvc.perform(get("/usuarios/perfil").param("perfil", "USER")
                        .header("Authorization", admin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].role").value("USER"));

        mockMvc.perform(get("/usuarios")
                        .header("Authorization", token("user@teste.com")))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/usuarios")
                        .header("Authorization", admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"novo@teste.com\",\"senha\":\"senha12345\",\"role\":\"USER\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.senha").doesNotExist());
    }
}
