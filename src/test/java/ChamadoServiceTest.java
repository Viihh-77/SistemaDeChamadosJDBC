import org.example.model.Chamado;
import org.example.model.Usuario;
import org.example.service.chamado.ChamadoService;
import org.example.service.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ChamadoServiceTest {

    private ChamadoService chamadoService;
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        chamadoService = new ChamadoService() {
            @Override
            public Chamado abrirChamado(Chamado chamado) throws SQLException {
                return null;
            }

            @Override
            public Chamado buscarChamadoPorId(long id) throws SQLException {
                return null;
            }
        };

        usuarioService = new UsuarioService() {
            @Override
            public Usuario cadastrarUsuario(Usuario usuario) {
                return usuario;
            }

            @Override
            public Usuario buscarUsuarioPorId(long id) {
                return null;
            }
        };
    }

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
        Chamado ch = new Chamado();
        ch.setUsuarioId(1);
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
        ch.setUsuarioId(1);
        ch.setDescricao("Computador não liga");
        ch.setPrioridade("ALTA");

        Chamado criado = chamadoService.abrirChamado(ch);

        assertNotNull(criado);
        assertEquals("ABERTO", criado.getStatus());
        assertNotNull(criado.getDataAbertura());
    }

    @Test
    void deveRetornarErroSeChamadoNaoExistirAoBuscar() throws SQLException {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> chamadoService.buscarChamadoPorId(999)
        );

        assertEquals("Chamado não encontrado!", ex.getMessage());
    }
}
