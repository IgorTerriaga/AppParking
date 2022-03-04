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
import com.example.appparking.Model.Localizacao;
import com.example.appparking.Model.Motorista;
import com.example.appparking.Model.Vaga;
import com.example.appparking.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowVacanceActivity extends AppCompatActivity {


    private TextView textVaga;

    private Retrofit retrofit;
    private Button botaoVagaAceitar, botaoVagaRejeitar;
    private String idVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vacance);

        //portao = findViewById(R.id.textPortao);
        textVaga = findViewById(R.id.textVaga);
        botaoVagaAceitar = findViewById(R.id.buttonAceitar);
        botaoVagaRejeitar = findViewById(R.id.buttonRejeitar);
        String urlBASE = "http://192.168.2.128:5000/";

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

                    assert response.body() != null;
                    Vaga vaga = new Vaga(idEstacionamento, response.body().getLongitude(), response.body().getLatitude());

                    Call<Vaga> requestVaga = service.RecomendarVaga(response.body().id, vaga);


                    SharedPreferences prefs1;
                    prefs1 = PreferenceManager.
                            getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor ed = prefs1.edit();
                    ed.putString("idMotorista", response.body().id);
                    ed.apply();


                    requestVaga.enqueue(new Callback<Vaga>() {
                        @Override
                        public void onResponse(Call<Vaga> call, Response<Vaga> response) {
                            if (response.isSuccessful()) {
                                textVaga.setText(response.body().getVaga());

                                idVaga = response.body().getId();


                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String idMotorista = prefs.getString("idMotorista", null);
                                MyThread myThread = new MyThread(idMotorista, response.body().getLatitude(), response.body().getLongitude(), idVaga);
                                new Thread(myThread).start();

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

    class MyThread implements Runnable {


        private String id;

        Boolean flag = true;
        private String idVaga;
        private String Latitude, Longitude;
        private String LatitudeVaga, LongitudeVaga;
        private String ref;


        public MyThread(String id, String LatitudeVaga, String LongitudeVaga, String idVaga) {
            this.id = id;
            this.LatitudeVaga = LatitudeVaga;
            this.LongitudeVaga = LongitudeVaga;
            this.idVaga = idVaga;
        }

        @Override
        public void run() {


            String urlBASE = "http://192.168.2.128:5000/";
            retrofit = new Conexao().connectAPI(urlBASE);
            DataService service = retrofit.create(DataService.class);

            ArrayList<Localizacao> myLocations = new ArrayList<>();

            myLocations.add(new Localizacao("-1111111.001", "-1111111.002"));
            myLocations.add(new Localizacao("-1111111.003", "-1111111.004"));
            myLocations.add(new Localizacao("-1111111.005", "-1111111.006"));
            myLocations.add(new Localizacao("-1111111.007", "-1111111.008"));
            myLocations.add(new Localizacao("-1111111.009", "-1111111.0010"));
            myLocations.add(new Localizacao("-10.6999841", "-37.4266416"));
            int v = 0;

            Localizacao localizacao;

            while (flag) {
                v = new Random().nextInt(myLocations.size());

                localizacao = myLocations.get(v);
                Latitude = localizacao.getLatitude();
                Longitude = localizacao.getLongitude();

                System.out.println(Latitude + "------------------" + Longitude);

                Call<Vaga> vaga = service.pegarVagas(idVaga);
                vaga.enqueue(new Callback<Vaga>() {
                    @Override
                    public void onResponse(Call<Vaga> call, Response<Vaga> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus()) {
                                ref = "Outro";
                                flag = false;
                            } else {
                                if (Latitude.equals(LatitudeVaga) && Longitude.equals(LongitudeVaga)) {
                                    ref = "Vaga ocupada!!!";
                                    flag = false;
                                }
                            }
                        } else {
                            System.out.println(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Vaga> call, Throwable t) {

                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            runOnUiThread(() -> {
                textVaga.setText(ref);
            });

            }
        }
    }
