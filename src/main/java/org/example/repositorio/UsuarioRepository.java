package org.example.repositorio;

import org.example.database.Conexao;
import org.example.model.Usuario;

import java.sql.*;

public class UsuarioRepository {

    public Usuario cadastrarUsuario(Usuario usuario) throws SQLException {

        String query = """
                INSERT INTO Usuario
                (nome,email,setor,ativo)
                VALUES
                (?,?,?,?)
                """;

        try (Connection conn  = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSetor());
            stmt.setBoolean(4, usuario.isAtivo());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                usuario.setId(rs.getInt(1));
            }
        }
        return usuario;
    }

    public Usuario buscarUsuarioPorId(long id) throws SQLException {

        String query = """
                SELECT   id
                        ,nome
                        ,email
                        ,setor
                        ,ativo
                FROM Usuario
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("setor"),
                        rs.getBoolean("ativo")
                );
            }
        }
        return null;
    }


    }
