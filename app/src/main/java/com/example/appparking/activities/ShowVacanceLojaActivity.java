package com.example.appparking.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ShowVacanceLojaActivity extends AppCompatActivity {


    private TextView portao, textVaga;
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

        String urlBASE = "http://192.168.2.75:5000/";

        retrofit = new Conexao().connectAPI(urlBASE);
        DataService service = retrofit.create(DataService.class);

        Bundle extras = getIntent().getExtras();
        String idLoja = extras.getString("id");
        System.out.println("Olha o id da loja" + idLoja);


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
                                textVaga.setText(response.body().getVaga());

                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String idMotorista = prefs.getString("idMotorista", null);

                                MyThread myThread = new MyThread(idMotorista, response.body().getLatitude(), response.body().getLongitude() );
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

    class MyThread implements Runnable {

        private String id;
        private String Latitude, Longitude;
        private String LatitudeVaga, LongitudeVaga;

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String longitude) {
            Longitude = longitude;
        }

        public MyThread(String id, String LatitudeVaga, String LongitudeVaga) {
            this.id = id;
            this.LatitudeVaga = LatitudeVaga;
            this.LongitudeVaga = LongitudeVaga;
        }

        @Override
        public void run() {
            //String urlBASE = "http://192.168.2.125:5000/";
            String urlBASE = "http://192.168.2.128:5000/";

            retrofit = new Conexao().connectAPI(urlBASE);
            DataService service = retrofit.create(DataService.class);
            Call<Motorista> motorista = service.AtualizarLongLati(id, LatitudeVaga, LongitudeVaga);
            //System.out.println("Antes de fazer a requisição");
            motorista.enqueue(new Callback<Motorista>() {
                @Override
                public void onResponse(Call<Motorista> call, Response<Motorista> response) {
                    if (response.isSuccessful()) {

                        Latitude = response.body().getLatitude(); //motorista
                        Longitude = response.body().getLongitude(); // motorista
                        if (Latitude.equals(LatitudeVaga) && Longitude.equals(LongitudeVaga)){
                            System.out.println("Ele ta na vaga");
                        }
                        System.out.println(Latitude + " --------------  " + Longitude);
                    }else{
                        System.out.println(response.message());
                        System.out.println(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Motorista> call, Throwable t) {

                }
            });

            runOnUiThread(() -> new Runnable() {
                @Override
                public void run() {
                    System.out.println("ok");
                }
            });

        }
    }
}