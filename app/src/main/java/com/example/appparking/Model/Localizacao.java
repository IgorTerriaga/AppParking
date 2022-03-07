package com.example.appparking.Model;

public class Localizacao {

    public  String Latitude;
    public String Longitude;

    public Localizacao(String latitude, String longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }




}
