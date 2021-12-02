package com.example.appparking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private Boolean IsInternetPresence = false;
    private Boolean WifiConnected = false;
    private Boolean DataPhoneConnected = false;

    private Button buttonNew;
    private TextView hasAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!checkNetworkConnection().equals("wifi")) {
            System.out.println();
            OpenDialogForWifi();
        } else if (!checkNetworkConnection().equals("dados")) {
            OpenDialogForDados();
        }

        buttonNew = findViewById(R.id.buttonEnd);
        hasAcc = findViewById(R.id.textHasAccount);

        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        hasAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent2);
            }
        });
    }

    public String checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            WifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            DataPhoneConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (WifiConnected) {
                return "wifi";
            } else if (DataPhoneConnected) {
                return "dados";
            }
        }
        return "Sem conexão com a Internet";
    }

    public void OpenDialogForWifi() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sem Conexão com a internet");
        dialog.setMessage("Por favor, conecte-se a uma rede");

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent turnWifiOn = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(turnWifiOn);
            }
        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Necessário conectar-se à uma rede.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        dialog.create().show();
    }

    public void OpenDialogForDados() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sem Conexão com a internet");
        dialog.setMessage("Por favor, conecte-se a uma rede");

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent turnWifiOn = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(turnWifiOn);
            }
        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Necessário conectar-se à uma rede.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        dialog.create().show();
    }
}