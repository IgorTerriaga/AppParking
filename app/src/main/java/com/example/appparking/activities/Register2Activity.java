package com.example.appparking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Model.Motorista;
import com.example.appparking.Model.Veiculo;
import com.example.appparking.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Register2Activity extends AppCompatActivity {


    private Button button;
    private TextInputEditText placa, modelo, cor;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        button = findViewById(R.id.buttonEnd);

        placa = findViewById(R.id.TextInputEditPlaca);
        modelo = findViewById(R.id.TextInputEditModelo);
        cor = findViewById(R.id.TextInputEditCor);

        String urlBASE = "http://10.0.0.158:5000/";
        retrofit = new Conexao().connectAPI(urlBASE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                String motorista_id = extras.getString("idDriver");

                Veiculo veiculo = new Veiculo(modelo.getText().toString(), placa.getText().toString(), cor.getText().toString(), motorista_id);

                DataService service = retrofit.create(DataService.class);

                Call<Veiculo> call = service.RegisterFirst(veiculo);
                call.enqueue(new Callback<Veiculo>() {
                    @Override
                    public void onResponse(Call<Veiculo> call, Response<Veiculo> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            JSONObject jObjError = null;
                            try {
                                jObjError = new JSONObject(response.errorBody().string());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Veiculo> call, Throwable t) {

                    }
                });
            }
        });
    }
}