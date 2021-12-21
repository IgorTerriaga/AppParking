package com.example.appparking.Model;

public class Motorista {
    public String nome;
    public String email;
    public String senha;
    public Boolean deficiente;
    public Boolean idoso;
    public String token;
    public String error;

    public Motorista(String nome, String email, String senha, Boolean deficiente, Boolean idoso) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.deficiente = deficiente;
        this.idoso = idoso;
    }


    public String getError() {
        return error;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getToken() {
        return token;
    }

    public Boolean getDeficiente() {
        return deficiente;
    }

    public void setDeficiente(Boolean deficiente) {
        this.deficiente = deficiente;
    }

    public Boolean getIdoso() {
        return idoso;
    }

    public void setIdoso(Boolean idoso) {
        this.idoso = idoso;
    }

}
