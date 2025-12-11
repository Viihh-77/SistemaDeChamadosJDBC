import org.example.database.Conexao;
import org.example.model.Chamado;
import org.example.model.Usuario;
import org.example.service.chamado.ChamadoService;
import org.example.service.chamado.ChamadoServiceImpl;
import org.example.service.usuario.UsuarioService;
import org.example.service.usuario.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ChamadoServiceTest {

    private ChamadoService chamadoService;
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() throws SQLException {
        prepararBanco();
        usuarioService = new UsuarioServiceImpl();
        chamadoService = new ChamadoServiceImpl();
    }

    // 🔧 CRIA AS TABELAS ANTES DE CADA TESTE
    private void prepararBanco() throws SQLException {
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS Chamado");
            stmt.execute("DROP TABLE IF EXISTS Usuario");

            stmt.execute("""
                CREATE TABLE Usuario (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL,
                    setor VARCHAR(100),
                    ativo BOOLEAN
                )
            """);

            stmt.execute("""
                CREATE TABLE Chamado (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    usuarioId INT NOT NULL,
                    descricao VARCHAR(255) NOT NULL,
                    prioridade VARCHAR(20),
                    status VARCHAR(20),
                    dataAbertura TIMESTAMP,
                    FOREIGN KEY (usuarioId) REFERENCES Usuario(id)
                )
            """);
        }
    }

    // --------------------------
    //          TESTES
    // --------------------------

    @Test
    void naoDeveAbrirChamadoComObjetoNulo() {
        assertThrows(RuntimeException.class,
                () -> chamadoService.abrirChamado(null));
    }

    @Test
    void naoDeveAbrirChamadoSemUsuario() throws SQLException {
        Chamado ch = new Chamado();
        ch.setDescricao("Monitor queimado");
        ch.setPrioridade("ALTA");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> chamadoService.abrirChamado(ch)
        );

        assertEquals("Usuário não encontrado!", ex.getMessage());
    }

    @Test
    void naoDeveAbrirChamadoSemDescricao() throws SQLException {
        // Cadastra usuário válido
        Usuario u = new Usuario();
        u.setNome("Carlos");
        u.setEmail("carlos@gmail.com");
        usuarioService.cadastrarUsuario(u);

        Chamado ch = new Chamado();
        ch.setUsuarioId(u.getId());
        ch.setPrioridade("MEDIA");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> chamadoService.abrirChamado(ch)
        );

        assertEquals("Descrição é obrigatória!", ex.getMessage());
    }

    @Test
    void deveAbrirChamadoComSucesso() throws SQLException {
        Usuario u = new Usuario();
        u.setNome("Ana");
        u.setEmail("ana@gmail.com");
        usuarioService.cadastrarUsuario(u);

        Chamado ch = new Chamado();
        ch.setUsuarioId(u.getId());
        ch.setDescricao("Computador não liga");
        ch.setPrioridade("ALTA");

        Chamado criado = chamadoService.abrirChamado(ch);

        assertNotNull(criado);
        assertEquals("ABERTO", criado.getStatus());
        assertNotNull(criado.getDataAbertura());
    }

    @Test
    void deveRetornarErroSeChamadoNaoExistirAoBuscar() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> chamadoService.buscarChamadoPorId(999)
        );

        assertEquals("Chamado não encontrado!", ex.getMessage());
    }
}
