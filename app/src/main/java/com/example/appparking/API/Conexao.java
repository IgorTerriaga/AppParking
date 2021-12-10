package com.example.appparking.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Conexao {
    private Retrofit retrofit;

    public Conexao() {
    }


    public Retrofit connectAPI(String URI){
        return new Retrofit.Builder().baseUrl(URI).addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
