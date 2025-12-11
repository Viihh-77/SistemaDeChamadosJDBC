package org.example.service.chamado;

import org.example.model.Chamado;
import org.example.model.Usuario;
import org.example.repositorio.ChamadoRepository;
import org.example.repositorio.UsuarioRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class ChamadoServiceImpl implements ChamadoService {

    ChamadoRepository chamadoRepository = new ChamadoRepository();
    UsuarioRepository usuarioRepository = new UsuarioRepository();

    @Override
    public Chamado abrirChamado(Chamado chamado) throws SQLException {

        if (chamado == null) {
            throw new RuntimeException("Chamado inválido!");
        }

        Usuario usuario = usuarioRepository.buscarUsuarioPorId(chamado.getUsuarioId());

        if (usuario == null) {
            throw  new RuntimeException("Usuário não encontrado!");
        }

        if (chamado.getDescricao() == null || chamado.getDescricao().isEmpty()) {
            throw new RuntimeException("Descrição é obrigatória!");
        }

        chamado.setStatus("ABERTO");
        chamado.setDataAbertura(LocalDateTime.now());

        Chamado chamadoSalvo = chamadoRepository.abrirChamado(chamado);

        return chamadoSalvo;
    }

    @Override
    public Chamado buscarChamadoPorId(long id) throws SQLException {

        Chamado chamado = chamadoRepository.buscarChamadoPorId(id);

        if (chamado == null) {
            throw new RuntimeException("Chamado não encontrado!");
        }

        return chamado;
    }
}
