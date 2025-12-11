package org.example.service.usuario;

import org.example.model.Usuario;
import org.example.repositorio.UsuarioRepository;

import java.sql.SQLException;

public class UsuarioServiceImpl implements UsuarioService {

    UsuarioRepository usuarioRepository = new UsuarioRepository();

    @Override
    public Usuario cadastrarUsuario(Usuario usuario) throws SQLException {

        if (usuario == null) {
            throw new RuntimeException("Usuário inválido!");
        }

        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório!");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new RuntimeException("Email é obrigatório!");
        }

        Usuario usuarioSalvo = usuarioRepository.cadastrarUsuario(usuario);

        return usuarioSalvo;
    }

    @Override
    public Usuario buscarUsuarioPorId(long id) throws SQLException {

        Usuario usuario = usuarioRepository.buscarUsuarioPorId(id);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado!");
        }

        return usuario;
    }
}
