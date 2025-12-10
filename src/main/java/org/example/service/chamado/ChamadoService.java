package org.example.service.chamado;

import org.example.model.Chamado;

import java.sql.SQLException;

public interface ChamadoService {

    Chamado abrirChamado(Chamado chamado) throws SQLException;

    Chamado buscarChamadoPorId(long id) throws SQLException;

}
