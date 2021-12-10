package com.example.appparking.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Model.Estacionamento;
import com.example.appparking.Model.Loja;
import com.example.appparking.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LojasFragment extends Fragment {

    private ListView l;
    private String[] itens;
    private Retrofit retrofit;
    private List<Estacionamento> listaEstacionamentos = new ArrayList<>();
    private List<Loja> listaLojas = new ArrayList<>();

    String urlBASE = "http://192.168.31.154:5000/";

    public LojasFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lojas, container, false);
        l = (ListView) view.findViewById(R.id.listLojas);

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

                        Log.d("Estacionamentos", "onResponse: " + estacionamento.getSede());

                        Call<List<Loja>> loja = service.recuperarLojas(estacionamento.getId());

                        loja.enqueue(new Callback<List<Loja>>() {
                            @Override
                            public void onResponse(Call<List<Loja>> call, Response<List<Loja>> response) {

                                listaLojas = response.body();

                                for (int i = 0; i < listaLojas.size(); i++) {
                                    Loja loja1 = listaLojas.get(i);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                            getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Collections.singletonList(listaLojas.get(i).getNome()));
                                    l.setAdapter(adapter);


                                    Log.d("Lojas", "onResponse: " + loja1.getNome());

                                }

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