package org.example.service.relatorio;

import org.example.dto.ChamadoResumoDTO;
import org.example.dto.UsuarioContagemChamadosDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RelatorioService {

    List<ChamadoResumoDTO> listarChamadosAbertosPorPrioridade(String prioridade) throws SQLException;

    List<UsuarioContagemChamadosDTO> gerarRelatorioUsuariosComMaisChamados(int minimo) throws SQLException;

    Optional<ChamadoResumoDTO> buscarDetalhesChamado(long chamadoId) throws SQLException;

}
