package com.example.appparking.Model;

public class Veiculo {
    public String modelo, placa, cor, motorista_id;
    public String error;

    public Veiculo(String modelo, String placa, String cor, String motorista_id) {
        this.modelo = modelo;
        this.placa = placa;
        this.cor = cor;
        this.motorista_id = motorista_id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getError() {
        return error;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMotorista_id() {
        return motorista_id;
    }

    public void setMotorista_id(String motorista_id) {
        this.motorista_id = motorista_id;
    }
}
