package com.example.appparking.activities;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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

public class ShowVacanceLojaActivity extends AppCompatActivity {


    private TextView textVaga, textTitulo;

    private Retrofit retrofit;
    private Button botaoVagaAceitar, botaoVagaRejeitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vacance);
        textTitulo = findViewById(R.id.Titulo);
        textVaga = findViewById(R.id.textVaga);
        botaoVagaAceitar = findViewById(R.id.buttonAceitar);
        botaoVagaRejeitar = findViewById(R.id.buttonRejeitar);
        String urlBASE = "http://10.0.0.158:5000/";

        retrofit = new Conexao().connectAPI(urlBASE);
        DataService service = retrofit.create(DataService.class);

        Bundle extras = getIntent().getExtras();
        String idLoja = extras.getString("id");
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
                    SharedPreferences prefs2;
                    prefs2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor ed4 = prefs2.edit();
                    ed4.putString("LatitudeMotorista", response.body().getLatitude());
                    ed4.apply();

                    SharedPreferences prefs3;
                    prefs3 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor ed3 = prefs3.edit();
                    ed3.putString("LongitudeMotorista", response.body().getLongitude());
                    ed3.apply();

                    Vaga vaga = new Vaga(idEstacionamento, idLoja, response.body().getLongitude(), response.body().getLatitude());

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
        //VAGA
        SharedPreferences prefs2 = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String idVaga = prefs2.getString("idVaga", null);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String idMotorista = prefs.getString("idMotorista", null);


        SharedPreferences prefs3 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String idLatitude = prefs3.getString("idLatitude", null);


        SharedPreferences prefs4 = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String idLongitude = prefs4.getString("idLongitude", null);


        //MOTORISTA
        SharedPreferences prefs5 = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String LatitudeMotorista = prefs5.getString("LatitudeMotorista", null);

        SharedPreferences prefs6 = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String LongitudeMotorista = prefs5.getString("LongitudeMotorista", null);

        MyThread myThread = new MyThread(idMotorista, idLatitude, idLongitude, idVaga, LatitudeMotorista, LongitudeMotorista);
        new Thread(myThread).start();
        botaoVagaRejeitar.setOnClickListener(v -> {
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
        private String LatitudeMotorista, LongitudeMotorista;
        private String ref;


        public MyThread(String id, String LatitudeVaga, String LongitudeVaga, String idVaga, String LongitudeMotorista, String LatitudeMotorista) {
            this.id = id;
            this.LatitudeVaga = LatitudeVaga;
            this.LongitudeVaga = LongitudeVaga;
            this.idVaga = idVaga;
            this.LatitudeMotorista = LatitudeMotorista;
            this.LongitudeMotorista = LongitudeMotorista;
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {


            String urlBASE = "http://10.0.0.158:5000/";
            retrofit = new Conexao().connectAPI(urlBASE);
            DataService service = retrofit.create(DataService.class);
            ArrayList<Localizacao> myLocations = new ArrayList<>();


            Call<List<Vaga>> allVagas = service.listAll();

            allVagas.enqueue(new Callback<List<Vaga>>() {
                @Override
                public void onResponse(Call<List<Vaga>> call, Response<List<Vaga>> response) {


                    if (response.isSuccessful()) {
                        response.body().forEach((n) -> myLocations.add(new Localizacao(n.getLatitude(), n.getLongitude())));


                    } else {
                        System.out.println("Olha  o erro " + response.body());
                    }

                }

                @Override
                public void onFailure(Call<List<Vaga>> call, Throwable t) {

                }
            });

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
            int v;

            Localizacao localizacao;
            myLocations.add(new Localizacao(this.LatitudeMotorista, this.LongitudeMotorista));
            //Percurso com localização com a vaga
            myLocations.add(new Localizacao("-1111111.001", "-1111111.002"));
            myLocations.add(new Localizacao("-1111111.003", "-1111111.004"));
            myLocations.add(new Localizacao("-1111111.005", "-1111111.006"));
            myLocations.add(new Localizacao("-1111111.007", "-1111111.008"));
            myLocations.add(new Localizacao("-1111111.009", "-1111111.0010"));
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
                                textVaga.setText(ref);
                                flag = false;
                            } else {
                                //Recomendada
                                if (Latitude.equals(LatitudeVaga) && Longitude.equals(LongitudeVaga)) {
                                    ref = "A Vaga recomendada foi ocupada com sucesso!";
                                    botaoVagaRejeitar.setVisibility(View.INVISIBLE);
                                    botaoVagaAceitar.setVisibility(View.INVISIBLE);
                                    textTitulo.setVisibility(View.INVISIBLE);
                                    flag = false;
                                } else if (Latitude.startsWith("-10")) {
                                    textVaga.setText("Você ocupou a vaga não recomendada");
                                    botaoVagaRejeitar.setVisibility(View.INVISIBLE);
                                    botaoVagaAceitar.setVisibility(View.INVISIBLE);
                                    textTitulo.setVisibility(View.INVISIBLE);

                                } else {

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
                if (ref.equals("Outro Motorista ocupou sua vaga :(")) {
                    textTitulo.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ShowVacanceLojaActivity.this);
                    dialog.setTitle("Vaga já indisponível!");
                    dialog.setMessage("Deseja receber outra recomendação?");
                    dialog.setCancelable(false);
                    dialog.setIcon(R.drawable.parkingicon);
                    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            botaoVagaAceitar.setVisibility(View.INVISIBLE);
                            botaoVagaRejeitar.setVisibility(View.INVISIBLE);
                            textTitulo.setVisibility(View.INVISIBLE);
                            textTitulo.setVisibility(View.INVISIBLE);
                            textVaga.setText("Até a próxima!");

                        }
                    });
                    dialog.create();
                    dialog.show();
                }

            });

        }
    }
}
