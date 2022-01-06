package com.example.appparking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.example.appparking.R;

public class OptarPorVagaActivity extends AppCompatActivity {
    private Button buttonVaga;
    private Button buttonVagasPorLoja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optar_vaga);

        buttonVaga = findViewById(R.id.buttonVaga);
        buttonVagasPorLoja = findViewById(R.id.buttonVagaLoja);

        buttonVaga.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), ShowVacanceActivity.class);
            v.getContext().startActivity(intent);
        });

        buttonVagasPorLoja.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), LojaActivity.class);
            SharedPreferences prefs;
            prefs = PreferenceManager.
                    getDefaultSharedPreferences(getApplicationContext());
            String idEstacionamento = prefs.getString("idEstacionamento", "");
            intent.putExtra("idEstacionamento", idEstacionamento);
            v.getContext().startActivity(intent);
        });

    }
}