package com.example.appparking.API;

import com.example.appparking.Model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DataService {
    @POST("/login")
    Call<Login>RealizarLogin(@Body Login login);
}
