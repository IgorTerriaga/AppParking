package com.example.appparking.Model;

public class Vaga {

    public String id;
    public String idEstacionamento;
    public String idLoja;
    public String longitude;
    public String latitude;
    public String vaga;
    public String portao;
    public Boolean status;

    public Vaga(String Id, String idEstacionamento, String idLoja, String longitude, String latitude, String vaga, String portao) {
        this.id = Id;
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
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLongitude() {
        return longitude;
    }



    public String getLatitude() {
        return latitude;
    }



    public String getVaga() {
        return vaga;
    }


    public Boolean getStatus() {
        return status;
    }

}
