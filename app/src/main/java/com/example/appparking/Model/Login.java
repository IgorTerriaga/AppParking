package com.example.appparking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("email")
    @Expose(serialize = true)
    public String email;
    @SerializedName("senha")
    @Expose(serialize = true)
    public String senha;
    public String token;

    public Login(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getToken() {
        return token;
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
}
