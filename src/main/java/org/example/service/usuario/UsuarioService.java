package org.example.service.usuario;

import org.example.model.Usuario;

import java.sql.SQLException;

public interface UsuarioService {

    Usuario cadastrarUsuario(Usuario usuario) throws SQLException;

    Usuario buscarUsuarioPorId(long id);

}
