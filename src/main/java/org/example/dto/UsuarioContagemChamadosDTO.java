package org.example.dto;

public class UsuarioContagemChamadosDTO {

    private long usuarioId;
    private String nomeUsuario;
    private int quantidadeChamados;

    public UsuarioContagemChamadosDTO() {
    }

    public UsuarioContagemChamadosDTO(long usuarioId, String nomeUsuario, int quantidadeChamados) {
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
        this.quantidadeChamados = quantidadeChamados;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public int getQuantidadeChamados() {
        return quantidadeChamados;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setQuantidadeChamados(int quantidadeChamados) {
        this.quantidadeChamados = quantidadeChamados;
    }
}
