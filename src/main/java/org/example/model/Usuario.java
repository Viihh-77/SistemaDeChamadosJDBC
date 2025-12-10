package org.example.model;

public class Usuario {

    private long id;
    private String nome;
    private String email;
    private String setor;
    private boolean ativo;

    public Usuario() {
    }

    public Usuario(long id, String nome, String email, String setor, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.setor = setor;
        this.ativo = ativo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
