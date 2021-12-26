package com.example.appparking.Model;

public class Estacionamento {
    private String id, sede;
    private  int image;

    public Estacionamento(String id, String sede) {
        this.id = id;
        this.sede = sede;
    }

    public String getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSede() {
        return sede;
    }
}
