package com.example.appparking.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appparking.Model.Estacionamento;
import com.example.appparking.R;

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
        if(estacionamento.getSede().contains("Arena")){
            holder.imagem.setImageResource(R.drawable.estadio);
        }else{
            holder.imagem.setImageResource(R.drawable.shopping);
        }

    }

    @Override
    public int getItemCount() {
        return listEstacionamentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView sede;
        private ImageView imagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sede = itemView.findViewById(R.id.TituloEstacionamento);
            imagem = itemView.findViewById(R.id.imageEstacionamento);
        }
    }
}
