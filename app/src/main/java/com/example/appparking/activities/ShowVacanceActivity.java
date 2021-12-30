package com.example.appparking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.appparking.R;

public class ShowVacanceActivity extends AppCompatActivity {


    private TextView portao, vaga;
    private Button botaoVagaAceitar, botaoVagaRejeitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vacance);



        portao = findViewById(R.id.textPortao);
        vaga = findViewById(R.id.textVaga);
    }
}