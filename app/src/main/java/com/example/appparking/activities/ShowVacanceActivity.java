package com.example.appparking.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowVacanceActivity extends AppCompatActivity {


    private TextView textVaga;

    private Retrofit retrofit;
    private Button botaoVagaAceitar, botaoVagaRejeitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vacance);

        //portao = findViewById(R.id.textPortao);
        textVaga = findViewById(R.id.textVaga);
        botaoVagaAceitar = findViewById(R.id.buttonAceitar);
        botaoVagaRejeitar = findViewById(R.id.buttonRejeitar);
        String urlBASE = "http://10.0.0.158:5000/";

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
                                textVaga.setText(response.body() != null ? response.body().getVaga() : null);

                                SharedPreferences prefs2;
                                prefs2 = PreferenceManager.
                                        getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = prefs2.edit();
                                ed.putString("idVaga", response.body().getId());
                                ed.apply();

                                SharedPreferences prefsLatitude;
                                prefsLatitude = PreferenceManager.
                                        getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed2 = prefsLatitude.edit();
                                ed2.putString("idLatitude", response.body().getLatitude());
                                ed2.apply();

                                SharedPreferences prefsLongitude;
                                prefsLongitude = PreferenceManager.
                                        getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed3 = prefsLongitude.edit();
                                ed3.putString("idLongitude", response.body().getLongitude());
                                ed3.apply();


                            } else {
                                JSONObject jObjError = null;
                                try {
                                    jObjError = new JSONObject(response.errorBody().string());
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    assert jObjError != null;
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
        SharedPreferences prefs2 = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String idVaga = prefs2.getString("idVaga", null);
        //System.out.println("Verificando o Id da Vaga" + idVaga);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String idMotorista = prefs.getString("idMotorista", null);
        //System.out.println("verificando o id do motorista" + idMotorista);

        SharedPreferences prefs3 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String idLatitude = prefs3.getString("idLatitude", null);
        //System.out.println("verificando o id da Latitude" + idLatitude);

        SharedPreferences prefs4 = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String idLongitude = prefs4.getString("idLongitude", null);
        //System.out.println("verificando o id da Longitude" + idLongitude);

        MyThread myThread = new MyThread(idMotorista, idLatitude, idLongitude, idVaga);
        new Thread(myThread).start();
        botaoVagaRejeitar.setOnClickListener(v -> {
//            Intent intent = new Intent(this.getApplicationContext(), ShowVacanceActivity.class);
//            startActivity(intent);
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            startActivity(intent);
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


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {


            String urlBASE = "http://10.0.0.158:5000/";
            retrofit = new Conexao().connectAPI(urlBASE);
            DataService service = retrofit.create(DataService.class);
            ArrayList<Localizacao> myLocations = new ArrayList<>();
            //ArrayList<Localizacao> test = new ArrayList<>();

            System.out.println("Aqui........");
            Call<List<Vaga>> allVagas = service.listAll();

            allVagas.enqueue(new Callback<List<Vaga>>() {
                @Override
                public void onResponse(Call<List<Vaga>> call, Response<List<Vaga>> response) {

                    System.out.println("Response " + response);
                    //response.body().forEach((n)-> System.out.println(n.getLatitude() + " ----  " + n.getLongitude() ))
                    // ;
                    if (response.isSuccessful()) {
                        System.out.println("Deu bom" + response.body());
                        response.body().forEach((n) -> myLocations.add(new Localizacao(n.getLatitude(), n.getLongitude())));


                    } else {
                        System.out.println("Olha  o erro " + response.body());
                    }

                    //myLocations.forEach((n)-> System.out.println(n.getLatitude() + " -----" + n.getLongitude()));

                }


                @Override
                public void onFailure(Call<List<Vaga>> call, Throwable t) {

                }
            });

//            myLocations.add(new Localizacao("-1111111.001", "-1111111.002"));
//            myLocations.add(new Localizacao("-1111111.003", "-1111111.004"));
//            myLocations.add(new Localizacao("-1111111.005", "-1111111.006"));
//            myLocations.add(new Localizacao("-1111111.007", "-1111111.008"));
//            myLocations.add(new Localizacao("-1111111.009", "-1111111.0010"));
//
//
//            myLocations.add(new Localizacao(LatitudeVaga, LongitudeVaga));
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (myLocations.isEmpty()) {
                System.out.println("Está vazio");
            } else {
                myLocations.forEach((n) -> System.out.println(n.getLatitude() + " -----" + n.getLongitude()));
            }
            //myLocations.forEach((n)-> System.out.println(n.getLatitude() + " -----" + n.getLongitude()));
            int v;

            //System.out.println("Depois aqui..");
            Localizacao localizacao;

            while (flag) {
                v = new Random().nextInt(myLocations.size());

                localizacao = myLocations.get(v);
                Latitude = localizacao.getLatitude();
                Longitude = localizacao.getLongitude();

                Call<Vaga> vaga = service.pegarVagas(idVaga);
                vaga.enqueue(new Callback<Vaga>() {
                    @Override
                    public void onResponse(Call<Vaga> call, Response<Vaga> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus()) {
                                ref = "Outro Motorista ocupou sua vaga :(";
                                flag = false;
                            } else {
                                if (Latitude.equals(LatitudeVaga) && Longitude.equals(LongitudeVaga)) {
                                    ref = "A Vaga recomendada foi ocupada com sucesso!";
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
                    Thread.sleep(2000);
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
