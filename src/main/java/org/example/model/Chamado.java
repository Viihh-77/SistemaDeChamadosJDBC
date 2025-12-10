package org.example.model;

import java.time.LocalDateTime;

public class Chamado {

    private long id;
    private long usuarioId;
    private LocalDateTime dataAbertura;
    private String descricao;
    private String prioridade; // BAIXA, MEDIA, ALTA, URGENTE
    private String status;     // ABERTO, EM_ATENDIMENTO, FECHADO

    public Chamado() {
    }

    public Chamado(long id, long usuarioId, LocalDateTime dataAbertura,
                   String descricao, String prioridade, String status) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.dataAbertura = dataAbertura;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
