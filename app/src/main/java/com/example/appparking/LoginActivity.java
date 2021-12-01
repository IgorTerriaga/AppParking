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

import org.json.JSONObject;

import okhttp3.RequestBody;
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
        String urlBASE = "http://192.168.31.154:5000/";

        retrofit = new Retrofit.Builder()
                .baseUrl(urlBASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });
    }

    private void logar() {

        Login login = new Login(email.getText().toString().trim(), senha.getText().toString().trim());

        DataService service = retrofit.create(DataService.class);

        Call<Login> call = service.RealizarLogin(login);

        //System.out.println(retrofit.baseUrl());
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.code());
                    Toast.makeText(getApplicationContext(), "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                } else if (response.code()==500){
                    System.out.println("Erro: " + response.message() + " " + " " + response.toString());
                    Toast.makeText(getApplicationContext(), "Erro no Server", Toast.LENGTH_SHORT).show();
                }else if(response.code()==400){
                    Toast.makeText(getApplicationContext(), "Email/Senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                System.out.println(t.getMessage() + "\n " + t.getCause());
            }
        });
    }


}