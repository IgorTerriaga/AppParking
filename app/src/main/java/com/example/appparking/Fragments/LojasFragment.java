package com.example.appparking.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appparking.R;

public class LojasFragment extends Fragment {

    private TextView textLojas;

    public LojasFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lojas, container, false);
        textLojas = view.findViewById(R.id.textLojas);
        textLojas.setText("Alterado");
        return view;
    }
}