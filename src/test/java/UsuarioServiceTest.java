import org.example.model.Usuario;
import org.example.service.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        usuarioService = new UsuarioService() {
            @Override
            public Usuario cadastrarUsuario(Usuario usuario) {
                return null;
            }

            @Override
            public Usuario buscarUsuarioPorId(long id) {
                return null;
            }
        };
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
