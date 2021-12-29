package com.example.appparking.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appparking.R;
import com.example.appparking.activities.Register2Activity;
import com.example.appparking.activities.VacanceActivity;


public class SearchFragment extends Fragment {

    private ImageView image;
    private Button verVagas;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        verVagas = view.findViewById(R.id.buttonVagas);
        //image = view.findViewById(R.id.imageSearch);
        verVagas.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VacanceActivity.class);
            startActivity(intent);
        });
        return view;
    }
}