package com.example.appparking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Model.Estacionamento;
import com.example.appparking.R;
import com.example.appparking.Adapters.EstacionamentoAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VacanceActivity extends AppCompatActivity {


    private Retrofit retrofit;
    private RecyclerView recycler;
    private List<Estacionamento> listaEstacionamentos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacance);


        //String urlBASE = "http://192.168.2.125:5000/";
        String urlBASE = "http://192.168.2.75:5000/";

        retrofit = new Conexao().connectAPI(urlBASE);


        recycler = findViewById(R.id.recyclerViewEstacionamento);

        DataService service = retrofit.create(DataService.class);
        Call<List<Estacionamento>> estacionamentos = service.recuperarEstacionamentos();
        estacionamentos.enqueue(new Callback<List<Estacionamento>>() {
            @Override
            public void onResponse(Call<List<Estacionamento>> call, Response<List<Estacionamento>> response) {
                if (response.isSuccessful()) {
                    listaEstacionamentos = response.body();

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recycler.setLayoutManager(layoutManager);

                    EstacionamentoAdapter adapter = new EstacionamentoAdapter(listaEstacionamentos);
                    recycler.setHasFixedSize(true);
                    recycler.setAdapter(adapter);

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
            public void onFailure(Call<List<Estacionamento>> call, Throwable t) {

            }
        });


    }
}