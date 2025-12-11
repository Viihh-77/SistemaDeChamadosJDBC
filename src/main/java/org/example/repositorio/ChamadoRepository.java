package org.example.repositorio;

import org.example.database.Conexao;
import org.example.model.Chamado;

import java.sql.*;

public class ChamadoRepository {

    public Chamado abrirChamado(Chamado chamado) throws SQLException {

        String query = """
                INSERT INTO Chamado
                (usuarioId,descricao,prioridade,status,dataAbertura)
                VALUES
                (?,?,?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, chamado.getUsuarioId());
            stmt.setString(2, chamado.getDescricao());
            stmt.setString(3, chamado.getPrioridade());
            stmt.setString(4, chamado.getStatus());
            stmt.setTimestamp(5, Timestamp.valueOf(chamado.getDataAbertura()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                chamado.setId(rs.getLong(1));
            }
        }
        return chamado;
    }

    public Chamado buscarChamadoPorId(long id) throws SQLException {

        String query = """
                SELECT  id
                        ,usuarioId
                        ,descricao
                        ,prioridade
                        ,status
                        ,dataAbertura
                FROM Chamado
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Chamado(
                        rs.getLong("id"),
                        rs.getLong("chamadoId"),
                        rs.getTimestamp("dataAbertura").toLocalDateTime(),
                        rs.getString("prioridade"),
                        rs.getString("status"),
                        rs.getString("descricao")
                );
            }
        }
        return null;
    }

    }
