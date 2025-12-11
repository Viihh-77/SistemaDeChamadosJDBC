import org.example.database.Conexao;
import org.example.model.Atendimento;
import org.example.model.Chamado;
import org.example.model.Usuario;
import org.example.service.atendimento.AtendimentoService;
import org.example.service.atendimento.AtendimentoServiceImpl;
import org.example.service.chamado.ChamadoService;
import org.example.service.chamado.ChamadoServiceImpl;
import org.example.service.usuario.UsuarioService;
import org.example.service.usuario.UsuarioServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AtendimentoServiceTest {

    private static AtendimentoService atendimentoService;
    private static ChamadoService chamadoService;
    private static UsuarioService usuarioService;

    // ---------------------------------------------------
    // SQL CREATE
    // ---------------------------------------------------
    private static final String SQL_CREATE_USUARIO =
            """
            CREATE TABLE IF NOT EXISTS Usuario (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE
            );
            """;

    private static final String SQL_CREATE_CHAMADO =
            """
            CREATE TABLE IF NOT EXISTS Chamado (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                usuarioId BIGINT NOT NULL,
                descricao TEXT NOT NULL,
                prioridade VARCHAR(50) NOT NULL,
                status VARCHAR(50) NOT NULL,
                
                CONSTRAINT fk_chamado_usuario FOREIGN KEY (usuarioId)
                REFERENCES Usuario(id)
                ON DELETE RESTRICT
            );
            """;

    private static final String SQL_CREATE_ATENDIMENTO =
            """
            CREATE TABLE IF NOT EXISTS Atendimento (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                chamadoId BIGINT NOT NULL,
                tecnicoResponsavel VARCHAR(255),
                dataInicio DATETIME,
                dataFim DATETIME,
                solucao TEXT,
                
                CONSTRAINT fk_atendimento_chamado FOREIGN KEY (chamadoId)
                REFERENCES Chamado(id)
                ON DELETE RESTRICT
            );
            """;

    // ---------------------------------------------------
    // SQL DROP
    // ---------------------------------------------------
    private static final String SQL_DROP_ATENDIMENTO = "DROP TABLE IF EXISTS Atendimento;";
    private static final String SQL_DROP_CHAMADO = "DROP TABLE IF EXISTS Chamado;";
    private static final String SQL_DROP_USUARIO = "DROP TABLE IF EXISTS Usuario;";

    // ---------------------------------------------------
    // SQL TRUNCATE
    // ---------------------------------------------------
    private static final String SQL_TRUNCATE_ATENDIMENTO = "TRUNCATE TABLE Atendimento;";
    private static final String SQL_TRUNCATE_CHAMADO = "TRUNCATE TABLE Chamado;";
    private static final String SQL_TRUNCATE_USUARIO = "TRUNCATE TABLE Usuario;";

    // ---------------------------------------------------
    // Setup DATABASE Geral
    // ---------------------------------------------------
    @BeforeAll
    static void setupDatabase() throws Exception {
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(SQL_DROP_ATENDIMENTO);
            stmt.execute(SQL_DROP_CHAMADO);
            stmt.execute(SQL_DROP_USUARIO);

            stmt.execute(SQL_CREATE_USUARIO);
            stmt.execute(SQL_CREATE_CHAMADO);
            stmt.execute(SQL_CREATE_ATENDIMENTO);
        }

        usuarioService = new UsuarioServiceImpl();
        chamadoService = new ChamadoServiceImpl();
        atendimentoService = new AtendimentoServiceImpl();
    }

    @AfterAll
    static void tearDown() throws Exception {
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(SQL_DROP_ATENDIMENTO);
            stmt.execute(SQL_DROP_CHAMADO);
            stmt.execute(SQL_DROP_USUARIO);
        }
    }

    @BeforeEach
    void resetTables() throws Exception {

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            stmt.execute(SQL_TRUNCATE_ATENDIMENTO);
            stmt.execute(SQL_TRUNCATE_CHAMADO);
            stmt.execute(SQL_TRUNCATE_USUARIO);

            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }

        inserirDadosBase();
    }

    // ---------------------------------------------------
    // INSERÇÃO PADRÃO DE TESTE
    // ---------------------------------------------------
    private void inserirDadosBase() throws SQLException {

        Usuario u = new Usuario();
        u.setNome("Gabrielli");
        u.setEmail("gabrielli@gmail.com");
        usuarioService.cadastrarUsuario(u);

        Chamado c = new Chamado();
        c.setUsuarioId(1);
        c.setDescricao("Computador não liga");
        c.setPrioridade("ALTA");
        c.setStatus("ABERTO");

        chamadoService.abrirChamado(c);
    }

    // ---------------------------------------------------
    // TESTE 1 – NÃO DEVE INICIAR ATENDIMENTO COM OBJETO NULO
    // ---------------------------------------------------
    @Test
    @Order(1)
    void naoDeveIniciarAtendimentoComObjetoNulo() {
        assertThrows(RuntimeException.class,
                () -> atendimentoService.iniciarAtendimento(null));
    }

    // ---------------------------------------------------
    // TESTE 2 – CHAMADO NÃO EXISTE
    // ---------------------------------------------------
    @Test
    @Order(2)
    void naoDeveIniciarSeChamadoNaoExistir() {

        Atendimento a = new Atendimento();
        a.setChamadoId(999);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> atendimentoService.iniciarAtendimento(a)
        );

        assertEquals("Chamado não encontrado!", ex.getMessage());
    }

    // ---------------------------------------------------
    // TESTE 3 – CHAMADO NÃO ESTÁ ABERTO
    // ---------------------------------------------------
    @Test
    @Order(3)
    void naoDeveIniciarSeChamadoNaoEstiverAberto() throws SQLException {

        Chamado ch = chamadoService.buscarChamadoPorId(1);
        ch.setStatus("FECHADO");

        // 🔥 CORREÇÃO AQUI
        chamadoService.abrirChamado(ch);

        Atendimento a = new Atendimento();
        a.setChamadoId(1);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> atendimentoService.iniciarAtendimento(a)
        );

        assertEquals("Chamado não está aberto!", ex.getMessage());
    }

    // ---------------------------------------------------
    // TESTE 4 – DEVE INICIAR ATENDIMENTO COM SUCESSO
    // ---------------------------------------------------
    @Test
    @Order(4)
    void deveIniciarAtendimento() throws SQLException {

        Atendimento a = new Atendimento();
        a.setChamadoId(1);
        a.setTecnicoResponsavel("Carlos");

        Atendimento iniciado = atendimentoService.iniciarAtendimento(a);

        assertNotNull(iniciado);
        assertNotNull(iniciado.getDataInicio());
        assertEquals("Carlos", iniciado.getTecnicoResponsavel());
    }

    // ---------------------------------------------------
    // TESTE 5 – FINALIZAR ATENDIMENTO COM SUCESSO
    // ---------------------------------------------------
    @Test
    @Order(5)
    void deveFinalizarAtendimentoComSucesso() throws SQLException {

        Atendimento a = new Atendimento();
        a.setChamadoId(1);
        a.setTecnicoResponsavel("João");

        Atendimento iniciado = atendimentoService.iniciarAtendimento(a);

        Atendimento finalizado =
                atendimentoService.finalizarAtendimento(iniciado.getId(), "Troca da fonte");

        assertNotNull(finalizado.getDataFim());
        assertEquals("Troca da fonte", finalizado.getSolucao());
    }

    // ---------------------------------------------------
    // TESTE 6 – NÃO DEVE FINALIZAR SE ATENDIMENTO NÃO EXISTE
    // ---------------------------------------------------
    @Test
    @Order(6)
    void naoDeveFinalizarSeAtendimentoNaoExistir() {
        assertThrows(RuntimeException.class,
                () -> atendimentoService.finalizarAtendimento(999, "teste"));
    }
}
