package com.example.appparking.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appparking.R;


public class MeuCarroFragment extends Fragment {
    private TextView textCarro;

    public MeuCarroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meu_carro, container, false);
        textCarro = view.findViewById(R.id.textCarros);
        textCarro.setText("Alterado");

        return view;
    }
}