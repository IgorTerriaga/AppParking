package com.example.appparking.Model;


public class Loja {
    private String id;
    private String nome;

    public Loja (String id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }
}
