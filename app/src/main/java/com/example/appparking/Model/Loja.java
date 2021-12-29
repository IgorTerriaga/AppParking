package com.example.appparking.Model;


import java.util.Objects;

public class Loja {
    private String id;
    private String nome;
    private String categoria;
    private String proximo;

    public Loja(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getProximo() {
        return proximo;
    }

    public void setProximo(String proximo) {
        this.proximo = proximo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loja loja = (Loja) o;
        return Objects.equals(nome, loja.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
