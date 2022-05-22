package com.example.appparking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appparking.API.Conexao;
import com.example.appparking.API.DataService;
import com.example.appparking.Model.Login;
import com.example.appparking.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    //private TextInputLayout textInputLayoutSenha;

    private TextInputEditText email;
    private TextInputEditText senha;
    private Button login;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.TextInputEditModelo);
        senha = findViewById(R.id.TextInputEditSenha);

        login = findViewById(R.id.buttonLogin);
        //String urlBASE = "http://192.168.2.125:5000/";
        String urlBASE = "http://192.168.2.75:5000/";

        retrofit = new Conexao().connectAPI(urlBASE);


        login.setOnClickListener(view -> logar());
    }

    private void logar() {

        Login login = new Login(email.getText().toString().trim(), senha.getText().toString().trim());

        DataService service = retrofit.create(DataService.class);

        Call<Login> call = service.RealizarLogin(login);


        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    intent.putExtra("email", email.getText().toString().trim());

                    // Recupera a instância do preferences
                    SharedPreferences preferences = getSharedPreferences("my_prefs_data", MODE_PRIVATE);
                    // Para gravar os dados, pegar a instância do editor
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("token", response.body().getToken());
                    editor.commit();
                    startActivity(intent);

                    System.out.println(response.code());
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