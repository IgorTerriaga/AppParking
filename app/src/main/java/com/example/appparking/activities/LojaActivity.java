package com.example.appparking.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Adapters.Adapter;
import com.example.appparking.Model.Estacionamento;
import com.example.appparking.Model.Loja;
import com.example.appparking.R;
import com.example.appparking.Utils.RecyclerItemClickListener;

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
        String urlBASE = "http://10.0.0.158:5000/";

        recyclerView = findViewById(R.id.meuRecycler);


        retrofit = new Conexao().connectAPI(urlBASE);
        DataService service = retrofit.create(DataService.class);


        Bundle extras = getIntent().getExtras();
        String idEstacionamento = extras.getString("idEstacionamento");

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("idEstacionamento", idEstacionamento);
        editor.apply();


        Call<List<Loja>> loja = service.recuperarLojas(idEstacionamento);


        loja.enqueue(new Callback<List<Loja>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Loja>> call, Response<List<Loja>> response) {
                listaLojas = response.body();
                for (int i = 0; i < listaLojas.size(); i++) {
                    listaResult.add(listaLojas.get(i));

                    listaResult = listaResult.stream().distinct().collect(Collectors.toList());
                }

                Adapter adapter = new Adapter(listaResult);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                recyclerView.setAdapter(adapter);

                //evento de Click
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Loja loja1 = listaResult.get(position);
                        //System.out.println("Olha o id da loja" + loja1.getId());

                        Intent intent = new Intent(view.getContext(), ShowVacanceLojaActivity.class);
                        intent.putExtra("id", loja1.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));

            }

            @Override
            public void onFailure(Call<List<Loja>> call, Throwable t) {

            }
        });
    }
}
