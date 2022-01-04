package com.example.appparking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Model.Motorista;
import com.example.appparking.Model.Vaga;
import com.example.appparking.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowVacanceActivity extends AppCompatActivity {


    private TextView portao, textVaga;
    private Retrofit retrofit;
    private Button botaoVagaAceitar, botaoVagaRejeitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vacance);

        portao = findViewById(R.id.textPortao);
        textVaga = findViewById(R.id.textVaga);
        String urlBASE = "http://192.168.31.154:5000/";

        retrofit = new Conexao().connectAPI(urlBASE);
        DataService service = retrofit.create(DataService.class);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("my_prefs_data", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        Call<Motorista> motorista = service.GetMotorista("Bearer " + token);
        motorista.enqueue(new Callback<Motorista>() {
            @Override
            public void onResponse(Call<Motorista> call, Response<Motorista> response) {
                if (response.isSuccessful()) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String idEstacionamento = prefs.getString("idEstacionamento", null);
                    //String idEstacionamento = extras.getString("idEstacionamento");
                    assert response.body() != null;
                    Vaga vaga = new Vaga(idEstacionamento, response.body().getLongitude(), response.body().getLatitude());

                    Call<Vaga> requestVaga = service.RecomendarVaga(response.body().id, vaga);
                    requestVaga.enqueue(new Callback<Vaga>() {
                        @Override
                        public void onResponse(Call<Vaga> call, Response<Vaga> response) {
                            if (response.isSuccessful()){
                                textVaga.setText(response.body().getVaga());

                            }else {
                                JSONObject jObjError = null;
                                try {
                                    jObjError = new JSONObject(response.errorBody().string());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    //Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                                    textVaga.setText(jObjError.getString("error"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Vaga> call, Throwable t) {

                        }
                    });

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
            public void onFailure(Call<Motorista> call, Throwable t) {

            }
        });

    }
}