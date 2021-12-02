package com.example.appparking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.appparking.API.DataService;
import com.example.appparking.Model.Login;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    //private TextInputLayout textInputLayoutSenha;

    private TextInputEditText email;
    private TextInputEditText senha;
    private Button login;
    private Retrofit retrofit;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        checkBox = findViewById(R.id.checkBoxLembrar);
        email = findViewById(R.id.TextInputEditEmail);
        senha = findViewById(R.id.TextInputEditSenha);

        login = findViewById(R.id.buttonLogin);
        String urlBASE = "http://192.168.31.154:5000/";

        retrofit = new Retrofit.Builder()
                .baseUrl(urlBASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checkbox", "true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_LONG).show();

                } else if (!buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checkbox", "false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "UnChecked", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void logar() {

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("checkbox", "");

        Login login = new Login(email.getText().toString().trim(), senha.getText().toString().trim());

        DataService service = retrofit.create(DataService.class);

        Call<Login> call = service.RealizarLogin(login);


        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    if (checkbox.equals("true")) {
                        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        startActivity(intent);

                    } else if (checkbox.equals("false")) {
                        Toast.makeText(getApplicationContext(), "Por favor, faça o login.", Toast.LENGTH_SHORT).show();
                    }
                    System.out.println(response.code());
                    Toast.makeText(getApplicationContext(), "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    System.out.println("Erro: " + response.message() + " " + " " + response.toString());
                    Toast.makeText(getApplicationContext(), "Erro no Server", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "Email/Senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                System.out.println(t.getMessage() + "\n " + t.getCause());
            }
        });
    }


}