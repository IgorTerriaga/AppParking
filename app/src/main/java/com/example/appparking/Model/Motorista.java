package com.example.appparking.Model;

public class Motorista {
    public String idDriver;
    public String id;
    public String nome;
    public String email;
    public String senha;
    public Boolean deficiente;
    public String latitude;
    public String longitude;
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



    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return idDriver;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }



    public String getToken() {
        return token;
    }



}
