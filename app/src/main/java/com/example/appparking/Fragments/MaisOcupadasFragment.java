package com.example.appparking.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appparking.R;


public class MaisOcupadasFragment extends Fragment {
    private TextView textVagas;

    public MaisOcupadasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mais_ocupadas, container, false);
        textVagas = view.findViewById(R.id.textOcupadas);
        textVagas.setText("Alterado");
        return view;
    }
}