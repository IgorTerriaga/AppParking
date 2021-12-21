package com.example.appparking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Model.Motorista;
import com.example.appparking.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private Button next;
    private TextInputEditText nome, email, senha;
    private CheckBox checkBoxIdoso, checkBoxEspecial;
    private Boolean idoso, especial = false;

    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nome = findViewById(R.id.TextInputEditNome);
        email = findViewById(R.id.TextInputEditEmail);
        senha = findViewById(R.id.TextInputEditSenha);
        checkBoxIdoso = findViewById(R.id.checkBoxIdoso);
        checkBoxEspecial = findViewById(R.id.checkBoxEspecial);
        next = findViewById(R.id.buttonEnd);


        String urlBASE = "http://192.168.31.154:5000/";
        retrofit = new Conexao().connectAPI(urlBASE);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkBoxIdoso.isChecked()) {
                    idoso = true;
                }
                if (checkBoxEspecial.isChecked()) {
                    especial = true;
                }

                Motorista motorista = new Motorista(nome.getText().toString(), email.getText().toString(), senha.getText().toString().trim(), idoso, especial);
                Log.d("xxxxxxxxxxxxxxxxxxxxxx", "onClick: " + nome.toString().trim());
                Log.d("xxxxxxxxxxxxxxxxxxxxxx", "onClick: " + email.toString().trim());
                Log.d("xxxxxxxxxxxxxxxxxxxxxx", "onClick: " + senha.toString().trim());
                DataService service = retrofit.create(DataService.class);

                Call<Motorista> call = service.RegisterFirst(motorista);

                call.enqueue(new Callback<Motorista>() {
                    @Override
                    public void onResponse(Call<Motorista> call, Response<Motorista> response) {
                        if (response.isSuccessful()) {


                            Intent intent = new Intent(getApplicationContext(), Register2Activity.class);
                            startActivity(intent);
                        } else {
                            String res = null;
                            try {
                                res = response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onFailure(Call<Motorista> call, Throwable t) {

                    }
                });


            }
        });
    }

}