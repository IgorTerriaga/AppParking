package com.example.appparking.Model;

public class Vaga {

    public String Id;
    public String idEstacionamento;
    public String idLoja;
    public String longitude;
    public String latitude;
    public String vaga;
    public String portao;

    public Vaga(String Id, String idEstacionamento, String idLoja, String longitude, String latitude, String vaga, String portao) {
        Id = Id;
        this.idEstacionamento = idEstacionamento;
        this.idLoja = idLoja;
        this.longitude = longitude;
        this.latitude = latitude;
        this.vaga = vaga;
        this.portao = portao;
    }

    public Vaga(String idEstacionamento, String idLoja, String longitude, String latitude) {
        this.idEstacionamento = idEstacionamento;
        this.idLoja = idLoja;
        this.longitude = longitude;
        this.latitude = latitude;
}
    public Vaga(String idEstacionamento,  String longitude, String latitude) {
        this.idEstacionamento = idEstacionamento;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    public String getIdEstacionamento() {
        return idEstacionamento;
    }

    public void setIdEstacionamento(String idEstacionamento) {
        this.idEstacionamento = idEstacionamento;
    }

    public String getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(String idLoja) {
        this.idLoja = idLoja;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
    }

    public String getPortao() {
        return portao;
    }

    public void setPortao(String portao) {
        this.portao = portao;
    }
}
