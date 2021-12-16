package com.example.appparking.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Model.Estacionamento;
import com.example.appparking.Model.Loja;
import com.example.appparking.R;
import com.example.appparking.adapter.Adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LojasFragment extends Fragment {

    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private List<Estacionamento> listaEstacionamentos = new ArrayList<>();
    private List<Loja> listaResult = new ArrayList<>();
    private List<Loja> listaLojas = new ArrayList<>();

    String urlBASE = "http://192.168.31.154:5000/";


    public LojasFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lojas, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);


        retrofit = new Conexao().connectAPI(urlBASE);
        DataService service = retrofit.create(DataService.class);
        Call<List<Estacionamento>> call = service.recuperarEstacionamentos();

        call.enqueue(new Callback<List<Estacionamento>>() {
            @Override
            public void onResponse(Call<List<Estacionamento>> call, Response<List<Estacionamento>> response) {
                if (response.isSuccessful()) {
                    listaEstacionamentos = response.body();
                    for (int i = 0; i < listaEstacionamentos.size(); i++) {

                        Estacionamento estacionamento = listaEstacionamentos.get(i);

                        Call<List<Loja>> loja = service.recuperarLojas(estacionamento.getId());


                        loja.enqueue(new Callback<List<Loja>>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResponse(Call<List<Loja>> call, Response<List<Loja>> response) {
                                listaLojas = response.body();
//                                listaResult.clear();
                                for (int i = 0; i < listaLojas.size(); i++) {

                                    listaResult.add(listaLojas.get(i));
                                    listaResult = listaResult.stream().distinct().collect(Collectors.toList());
                                    Log.d("testeeeeeeee", "onResponse: " + listaResult.get(i).getNome());
                                }

                                Adapter adapter = new Adapter(listaResult);

                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(layoutManager);

                                recyclerView.setHasFixedSize(true);
                                //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
                                recyclerView.setAdapter(adapter);

                            }

                            @Override
                            public void onFailure(Call<List<Loja>> call, Throwable t) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Estacionamento>> call, Throwable t) {

            }
        });


        return view;
    }
}