package com.example.appparking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appparking.R;

public class VacanceActivity extends AppCompatActivity {

    private TextView imagemEstabelecimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacance);
        imagemEstabelecimento = findViewById(R.id.textEstabelecimento);

        imagemEstabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EspecialActivity.class);
                startActivity(intent);
            }
        });


    }
}