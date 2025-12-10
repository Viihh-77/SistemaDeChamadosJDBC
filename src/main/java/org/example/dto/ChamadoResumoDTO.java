package org.example.dto;

public class ChamadoResumoDTO {

    private long chamadoId;
    private String nomeUsuario;
    private String setor;
    private String prioridade;
    private String status;

    public ChamadoResumoDTO() {
    }

    public ChamadoResumoDTO(long chamadoId, String nomeUsuario, String setor,
                            String prioridade, String status) {
        this.chamadoId = chamadoId;
        this.nomeUsuario = nomeUsuario;
        this.setor = setor;
        this.prioridade = prioridade;
        this.status = status;
    }

    public long getChamadoId() {
        return chamadoId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSetor() {
        return setor;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setChamadoId(long chamadoId) {
        this.chamadoId = chamadoId;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
