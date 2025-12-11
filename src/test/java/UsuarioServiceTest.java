import org.example.database.Conexao;
import org.example.model.Usuario;
import org.example.service.usuario.UsuarioService;
import org.example.service.usuario.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioService usuarioService;

    @BeforeEach
    void setup() throws SQLException {
        usuarioService = new UsuarioServiceImpl();
        prepararBanco();
    }

    private void prepararBanco() throws SQLException {
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            // Derruba a tabela com o nome EXATO que o repository usa
            stmt.execute("DROP TABLE IF EXISTS Usuario");

            // Cria a tabela igual à utilizada no repository
            stmt.execute("""
            CREATE TABLE Usuario (
                id INT PRIMARY KEY AUTO_INCREMENT,
                nome VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                setor VARCHAR(100),
                ativo BOOLEAN
            )
        """);
        }
    }

    @Test
    void naoDeveCadastrarUsuarioNulo() {
        assertThrows(RuntimeException.class,
                () -> usuarioService.cadastrarUsuario(null));
    }

    @Test
    void naoDeveCadastrarSemNome() {
        Usuario u = new Usuario();
        u.setEmail("teste@gmail.com");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> usuarioService.cadastrarUsuario(u)
        );

        assertEquals("Nome é obrigatório!", ex.getMessage());
    }

    @Test
    void naoDeveCadastrarSemEmail() {
        Usuario u = new Usuario();
        u.setNome("Carlos");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> usuarioService.cadastrarUsuario(u)
        );

        assertEquals("Email é obrigatório!", ex.getMessage());
    }

    @Test
    void deveCadastrarUsuarioComSucesso() throws SQLException {
        Usuario u = new Usuario();
        u.setNome("Fernanda");
        u.setEmail("fernanda@gmail.com");

        Usuario cadastrado = usuarioService.cadastrarUsuario(u);

        assertNotNull(cadastrado);
        assertNotNull(cadastrado.getId());
        assertEquals("Fernanda", cadastrado.getNome());
        assertEquals("fernanda@gmail.com", cadastrado.getEmail());
    }

    @Test
    void deveRetornarErroSeUsuarioNaoExistirAoBuscar() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> usuarioService.buscarUsuarioPorId(999)
        );

        assertEquals("Usuário não encontrado!", ex.getMessage());
    }
}
