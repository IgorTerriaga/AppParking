package com.example.appparking.API;

import com.example.appparking.Model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DataService  {

    @POST("/login")
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    Call<Login> RealizarLogin(@Body Login login);
//
//    @POST("/motorista")
//    Call<Motorista>Register(@Body Motorista motorista);

}
