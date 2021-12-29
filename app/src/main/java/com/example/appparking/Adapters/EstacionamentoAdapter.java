package com.example.appparking.Adapters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appparking.Model.Estacionamento;
import com.example.appparking.R;
import com.example.appparking.activities.LojaActivity;
import com.example.appparking.activities.OptarPorVagaActivity;

import java.util.List;

public class EstacionamentoAdapter extends RecyclerView.Adapter<EstacionamentoAdapter.MyViewHolder> {

    private List<Estacionamento> listEstacionamentos;


    public EstacionamentoAdapter(List<Estacionamento> estacionamentos) {
        this.listEstacionamentos = estacionamentos;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estacionamento_detalhe, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Estacionamento estacionamento = listEstacionamentos.get(position);
        holder.sede.setText(estacionamento.getSede());
        if (estacionamento.getSede().contains("Arena")) {
            holder.imagem.setImageResource(R.drawable.estadio);
        } else {
            holder.imagem.setImageResource(R.drawable.shopping);
        }
        holder.idEstacionamento.setText(estacionamento.getId());

    }

    @Override
    public int getItemCount() {
        return listEstacionamentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView sede, idEstacionamento;
        private ImageView imagem;
        private Button botaoLojas;
        private Button botaoProcurarVaga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sede = itemView.findViewById(R.id.TituloEstacionamento);
            imagem = itemView.findViewById(R.id.imageEstacionamento);
            botaoLojas = itemView.findViewById(R.id.buttonFavoritar);
            botaoProcurarVaga = itemView.findViewById(R.id.buttonProcurarVaga);
            idEstacionamento = itemView.findViewById(R.id.idEstacionamento);



            botaoLojas.setOnClickListener(v -> {
                SharedPreferences prefs;
                prefs = PreferenceManager.
                        getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("idEstacionamento", idEstacionamento.getText().toString());
                ed.apply();

                Intent intent = new Intent(v.getContext(), LojaActivity.class);

                intent.putExtra("idEstacionamento", idEstacionamento.getText());
                v.getContext().startActivity(intent);
            });

            botaoProcurarVaga.setOnClickListener(v1 -> {
                Intent intent1 = new Intent(v1.getContext(), OptarPorVagaActivity.class);
                Toast.makeText(v1.getContext(), "Teste", Toast.LENGTH_SHORT).show();
                v1.getContext().startActivity(intent1);
            });

        }
    }
}
