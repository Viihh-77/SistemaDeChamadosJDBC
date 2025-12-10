package org.example.service.relatorio;

import org.example.dto.ChamadoResumoDTO;
import org.example.dto.UsuarioContagemChamadosDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RelatorioServiceImpl implements  RelatorioService {

    @Override
    public List<ChamadoResumoDTO> listarChamadosAbertosPorPrioridade(String prioridade) throws SQLException {
        return List.of();
    }

    @Override
    public List<UsuarioContagemChamadosDTO> gerarRelatorioUsuariosComMaisChamados(int minimo) throws SQLException {
        return List.of();
    }

    @Override
    public Optional<ChamadoResumoDTO> buscarDetalhesChamado(long chamadoId) throws SQLException {
        return Optional.empty();
    }
}
