package com.example.appparking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Model.Motorista;
import com.example.appparking.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private Button next;
    private TextInputEditText nome, email, senha;
    private Chip checkBoxIdoso, checkBoxEspecial;
    private Boolean idoso, especial = false;

    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nome = findViewById(R.id.TextInputEditPlaca);
        email = findViewById(R.id.TextInputEditModelo);
        senha = findViewById(R.id.TextInputEditSenha);
        checkBoxIdoso = findViewById(R.id.checkBoxIdoso);
        checkBoxEspecial = findViewById(R.id.checkBoxEspecial);
        next = findViewById(R.id.buttonEnd);


        String urlBASE = "http://10.0.0.158:5000/";
        retrofit = new Conexao().connectAPI(urlBASE);


        next.setOnClickListener(v -> {

            if (checkBoxIdoso.isChecked()) {
                idoso = true;
            }
            if (checkBoxEspecial.isChecked()) {
                especial = true;
            }

            Motorista motorista = new Motorista(nome.getText().toString(), email.getText().toString(), senha.getText().toString().trim(), idoso, especial);

            DataService service = retrofit.create(DataService.class);

            Call<Motorista> call = service.RegisterFirst(motorista);

            call.enqueue(new Callback<Motorista>() {
                @Override
                public void onResponse(Call<Motorista> call, Response<Motorista> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), Register2Activity.class);
                        //System.out.println("xxxxxxxxxxxxx " + response.body().getId());
                        intent.putExtra("idDriver", response.body().getId());
                        startActivity(intent);

                    } else {
                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Motorista> call, Throwable t) {

                }
            });
        });
    }
}