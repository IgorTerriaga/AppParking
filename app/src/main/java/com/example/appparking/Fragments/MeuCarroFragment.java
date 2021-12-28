package com.example.appparking.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Model.Veiculo;
import com.example.appparking.R;
import com.example.appparking.Adapters.VeiculoAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MeuCarroFragment extends Fragment {

    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private List<Veiculo> listaVeiculos = new ArrayList<>();
    private List<Veiculo> listaResult = new ArrayList<>();

    String urlBASE = "http://192.168.2.125:5000/";


    public MeuCarroFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meu_carro, container, false);
        recyclerView = view.findViewById(R.id.recyclerVeiculos);
        retrofit = new Conexao().connectAPI(urlBASE);
        SharedPreferences preferences = getContext().getSharedPreferences("my_prefs_data", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        //Log.d("Olha o token que veio", "onCreateView: " + token);

        DataService service = retrofit.create(DataService.class);
        Call<List<Veiculo>> veiculo = service.ConsultarVeiculos("Bearer " + token);
        veiculo.enqueue(new Callback<List<Veiculo>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Veiculo>> call, Response<List<Veiculo>> response) {
                if (response.isSuccessful()) {
                    listaVeiculos = response.body();
                    for (int i = 0; i < listaVeiculos.size(); i++) {
                        Veiculo veiculo1 = listaVeiculos.get(i);
                        listaResult.add(listaVeiculos.get(i));
                        listaResult = listaResult.stream().distinct().collect(Collectors.toList());
                        Log.d("O veículo  ", "onResponse: " + veiculo1.getModelo() + veiculo1.getCor());
                    }

                    VeiculoAdapter adapter = new VeiculoAdapter(listaResult);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
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
                        Toast.makeText(getContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Veiculo>> call, Throwable t) {

            }
        });

        return view;
    }
}