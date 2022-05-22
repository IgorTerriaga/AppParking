package com.example.appparking.API;

import com.example.appparking.Model.Estacionamento;
import com.example.appparking.Model.Login;
import com.example.appparking.Model.Loja;
import com.example.appparking.Model.Motorista;
import com.example.appparking.Model.Vaga;
import com.example.appparking.Model.Veiculo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {

    @GET("/estacionamento/{id}/loja")
    Call<List<Loja>> recuperarLojas(@Path("id") String id);


    @GET("/estacionamento")
    Call<List<Estacionamento>> recuperarEstacionamentos();


    @GET("/vaga/{id}")
    Call<Vaga> pegarVagas(@Path("id") String id);

    @GET("/vagas")
    Call<List<Vaga>> listAll();


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


    @GET("/motorista")
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    Call<Motorista> GetMotorista(@Header("AUTHORIZATION") String token);


    @POST("/veiculo")
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    Call<Veiculo> RegisterFirst(@Body Veiculo veiculo);

    @GET("/veiculo")
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    Call<List<Veiculo>> ConsultarVeiculos(@Header("AUTHORIZATION") String token);

    @POST("/motorista/{idMotorista}/recomendarvaga")
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    Call<Vaga> RecomendarVaga(@Path("idMotorista") String id, @Body Vaga vaga);


    @PUT("/motorista/{id}/{latitude}/{longitude}")
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    Call<Motorista> AtualizarLongLati(@Path("id") String id, @Path("latitude") String Latitude, @Path("longitude") String Longitude);


}
