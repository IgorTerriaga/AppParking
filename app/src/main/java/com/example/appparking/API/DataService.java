package com.example.appparking.API;

import com.example.appparking.Model.Estacionamento;
import com.example.appparking.Model.Login;
import com.example.appparking.Model.Loja;
import com.example.appparking.Model.Motorista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DataService {

    @GET("/estacionamento/{id}/loja")
    Call<List<Loja>> recuperarLojas(@Path("id") String id);


    @GET("/estacionamento")
    Call<List<Estacionamento>> recuperarEstacionamentos();

    @POST("/login")
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    Call<Login> RealizarLogin(@Body Login login);

    @POST("/motorista")
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    Call<Motorista> RegisterFirst(@Body Motorista motorista);

}
