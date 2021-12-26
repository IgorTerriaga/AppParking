package com.example.appparking.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appparking.Model.Veiculo;
import com.example.appparking.R;

import java.util.List;

public class VeiculoAdapter extends RecyclerView.Adapter<VeiculoAdapter.MyViewHolder> {
    private List<Veiculo> listVeiculo;

    public VeiculoAdapter(List<Veiculo> listaVeiculo) {
        this.listVeiculo = listaVeiculo;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_veiculo, null);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Veiculo veiculo = listVeiculo.get(position);
        holder.meuVeiculo.setText(veiculo.getModelo() + "" + veiculo.getCor());
        holder.imageVeiculo.setImageResource(R.drawable.newcar);

    }

    @Override
    public int getItemCount() {
        return listVeiculo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView meuVeiculo;
        private ImageView imageVeiculo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            meuVeiculo = itemView.findViewById(R.id.meuVeiculo);
            imageVeiculo = itemView.findViewById(R.id.imageVeiculo);


        }
    }
}
