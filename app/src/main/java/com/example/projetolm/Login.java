package com.example.projetolm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    EditText emailInput;
    EditText senhaInput;
    ImageView btEntrar;
    TextView btRecuperarSenhaLogin, btCadastreSeLogin;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


emailInput = findViewById(R.id.emailInput);
senhaInput = findViewById(R.id.senhaInput);
btEntrar = findViewById(R.id.btEntrar);
btRecuperarSenhaLogin = findViewById(R.id.btRecuperarSenhaLogin);
btCadastreSeLogin = findViewById(R.id.btCadastreSeLogin);


btCadastreSeLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent cadastro = new Intent(Login.this,Cadastro.class);
        startActivity(cadastro);
        finish();
    }
});

btEntrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


    }
});







    }
}