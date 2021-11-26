package com.example.appparking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appparking.API.DataService;
import com.example.appparking.Model.Login;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText senha;
    private Button login;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.TextInputEditModelo);
        senha = findViewById(R.id.TextInputEditSenha);
        login = findViewById(R.id.buttonLogin);

        retrofit = new Retrofit.Builder().baseUrl("192.168.31.154").addConverterFactory(GsonConverterFactory.create()).build();


       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            logar();
           }
       });
    }
    private  void logar(){
        Login login = new Login("email@igor", "123456");
        DataService service = retrofit.create(DataService.class);
        Call<Login> call = service.RealizarLogin(login);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }


}