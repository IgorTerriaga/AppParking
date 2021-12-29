package com.example.appparking.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Adapters.Adapter;
import com.example.appparking.Model.Estacionamento;
import com.example.appparking.Model.Loja;
import com.example.appparking.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LojaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private List<Estacionamento> listaEstacionamentos = new ArrayList<>();
    private List<Loja> listaResult = new ArrayList<>();
    private List<Loja> listaLojas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);
        String urlBASE = "http://192.168.31.154:5000/";

        recyclerView = findViewById(R.id.meuRecycler);


        retrofit = new Conexao().connectAPI(urlBASE);
        DataService service = retrofit.create(DataService.class);


        Bundle extras = getIntent().getExtras();
        String idEstacionamento = extras.getString("idEstacionamento");

        Call<List<Loja>> loja = service.recuperarLojas(idEstacionamento);

        loja.enqueue(new Callback<List<Loja>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Loja>> call, Response<List<Loja>> response) {
                listaLojas = response.body();
                for (int i = 0; i < listaLojas.size(); i++) {
                    listaResult.add(listaLojas.get(i));
                    listaResult = listaResult.stream().distinct().collect(Collectors.toList());
                    Log.d("testeeeeeeee", "onResponse: " + listaResult.get(i).getNome());
                }

                Adapter adapter = new Adapter(listaResult);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Loja>> call, Throwable t) {

            }
        });
    }
}
