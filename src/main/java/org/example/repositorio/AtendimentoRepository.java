package org.example.repositorio;

import org.example.database.Conexao;
import org.example.model.Atendimento;

import java.sql.*;

public class AtendimentoRepository {

    public Atendimento iniciarAtendimento(Atendimento atendimento) throws SQLException {

        String query = """
                INSERT INTO Atendimento
                (chamadoId, tecnicoResponsavel, dataInicio, solucao, dataFim)
                VALUES
                (?, ?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, atendimento.getChamadoId());
            stmt.setString(2, atendimento.getTecnicoResponsavel());
            stmt.setTimestamp(3, Timestamp.valueOf(atendimento.getDataInicio()));
            stmt.setString(4, atendimento.getSolucao());
            stmt.setTimestamp(5, Timestamp.valueOf(atendimento.getDataFim()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                atendimento.setId(rs.getLong(1));
            }
        }
        return atendimento;
    }

    public Atendimento buscarAtendimentoPorId(long id) throws SQLException {

        String query = """
                SELECT  id
                        ,chamadoId
                        ,tecnicoResponsavel
                        ,dataInicio
                        ,solucao
                        ,dataFim
                FROM Atendimento
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Atendimento(
                        rs.getLong("id"),
                        rs.getLong("chamadoId"),
                        rs.getTimestamp("dataInicio").toLocalDateTime(),
                        rs.getTimestamp("dataFim").toLocalDateTime(),
                        rs.getString("tecnicoResponsavel"),
                        rs.getString("solucao")
                );
            }
        }
        return null;
    }

    public Atendimento atualizarAtendimento(Atendimento atendimento) throws SQLException {

        String query = """
                UPDATE Atendimento
                SET chamadoId = ?, tecnicoResponsavel = ?, dataInicio = ?, dataFim = ?, solucao = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, atendimento.getChamadoId());
            stmt.setString(2, atendimento.getTecnicoResponsavel());
            stmt.setTimestamp(3, Timestamp.valueOf(atendimento.getDataInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(atendimento.getDataFim()));
            stmt.setString(5, atendimento.getSolucao());
            stmt.setLong(6, atendimento.getId());
            stmt.executeUpdate();
        }
        return atendimento;
    }

}
