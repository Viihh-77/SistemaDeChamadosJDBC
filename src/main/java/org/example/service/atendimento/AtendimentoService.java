package org.example.service.atendimento;

import org.example.model.Atendimento;

import java.sql.SQLException;

public interface AtendimentoService {

    Atendimento iniciarAtendimento(Atendimento atendimento) throws SQLException;

    Atendimento finalizarAtendimento(long atendimentoId, String solucao) throws SQLException;
}
