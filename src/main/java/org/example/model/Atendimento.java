package org.example.model;

import java.time.LocalDateTime;

public class Atendimento {

    private long id;
    private long chamadoId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String tecnicoResponsavel;
    private String solucao;

    public Atendimento() {
    }

    public Atendimento(long id, long chamadoId, LocalDateTime dataInicio,
                       LocalDateTime dataFim, String tecnicoResponsavel, String solucao) {
        this.id = id;
        this.chamadoId = chamadoId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tecnicoResponsavel = tecnicoResponsavel;
        this.solucao = solucao;
    }

    public long getId() {
        return id;
    }

    public long getChamadoId() {
        return chamadoId;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public String getTecnicoResponsavel() {
        return tecnicoResponsavel;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setChamadoId(long chamadoId) {
        this.chamadoId = chamadoId;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public void setTecnicoResponsavel(String tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }
}
