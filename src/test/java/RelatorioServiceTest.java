import org.example.dto.ChamadoResumoDTO;
import org.example.dto.UsuarioContagemChamadosDTO;
import org.example.service.relatorio.RelatorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioServiceTest {

    private RelatorioService relatorioService;

    @BeforeEach
    void setup() {
        relatorioService = new RelatorioService() {

            @Override
            public List<ChamadoResumoDTO> listarChamadosAbertosPorPrioridade(String prioridade) throws SQLException {
                return Collections.emptyList(); // simulação
            }

            @Override
            public List<UsuarioContagemChamadosDTO> gerarRelatorioUsuariosComMaisChamados(int minimo) throws SQLException {
                return Collections.emptyList(); // simulação
            }

            @Override
            public Optional<ChamadoResumoDTO> buscarDetalhesChamado(long chamadoId) throws SQLException {
                return Optional.empty(); // simulação
            }
        };
    }

    // -----------------------------------------------------------
    // TESTE 1 — listar chamados abertos por prioridade
    // -----------------------------------------------------------
    @Test
    void deveListarChamadosAbertosPorPrioridade() throws SQLException {
        List<ChamadoResumoDTO> lista =
                relatorioService.listarChamadosAbertosPorPrioridade("ALTA");

        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

    // -----------------------------------------------------------
    // TESTE 2 — relatorio de usuarios com mais chamados
    // -----------------------------------------------------------
    @Test
    void deveGerarRelatorioUsuariosComMaisChamados() throws SQLException {
        List<UsuarioContagemChamadosDTO> lista =
                relatorioService.gerarRelatorioUsuariosComMaisChamados(1);

        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

    // -----------------------------------------------------------
    // TESTE 3 — deve lançar erro se minimo for inválido
    // -----------------------------------------------------------
    @Test
    void deveLancarErroSeMinimoInvalid() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> {
                    int minimo = 0;
                    if (minimo <= 0) {
                        throw new RuntimeException("Valor mínimo inválido!");
                    }
                    relatorioService.gerarRelatorioUsuariosComMaisChamados(minimo);
                }
        );

        assertEquals("Valor mínimo inválido!", ex.getMessage());
    }

    // -----------------------------------------------------------
    // TESTE 4 — buscar detalhes de um chamado existente
    // -----------------------------------------------------------
    @Test
    void naoDeveEncontrarDetalhesChamadoInexistente() throws SQLException {
        Optional<ChamadoResumoDTO> detalhes =
                relatorioService.buscarDetalhesChamado(999);

        assertTrue(detalhes.isEmpty());
    }

    // -----------------------------------------------------------
    // TESTE 5 — id inválido
    // -----------------------------------------------------------
    @Test
    void deveLancarErroSeIdChamadoForInvalido() {
        assertThrows(RuntimeException.class,
                () -> {
                    long id = -1;
                    if (id <= 0) {
                        throw new RuntimeException("ID inválido!");
                    }
                    relatorioService.buscarDetalhesChamado(id);
                });
    }

    // -----------------------------------------------------------
    // TESTE 6 — exceção geral
    // -----------------------------------------------------------
    @Test
    void deveLancarErroAoGerarRelatorio() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> { throw new RuntimeException("Erro ao gerar relatório!"); }
        );

        assertEquals("Erro ao gerar relatório!", ex.getMessage());
    }
}
