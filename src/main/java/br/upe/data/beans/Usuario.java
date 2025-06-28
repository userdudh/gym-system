package br.upe.data.beans;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private TipoUsuario tipo;

    public Usuario(int id, String nome, String email, TipoUsuario tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }
}
