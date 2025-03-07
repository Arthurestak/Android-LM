package com.example.projetolm;
import java.sql.Connection;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.projetolm.ui.livros.LivrosFragment;
import com.example.projetolm.ui.perfil.PerfilFragment;

import java.sql.Statement;

import javax.xml.transform.Result;

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

btRecuperarSenhaLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
        startActivity(intent);
    }
});

btCadastreSeLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Login.this,Cadastro.class);
        startActivity(intent);
        finish();
    }
});

btEntrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Connection connection = ConexaoMySQL.conectar();
        try {
            String selectLogin = "select * from pessoa where email = ? and senha = ?";
            PreparedStatement stmt = connection.prepareStatement(selectLogin);
            stmt.setString(1, emailInput.getText().toString());
            stmt.setString(2, senhaInput.getText().toString());
            ResultSet rs = stmt.executeQuery();

            String selectSenha = "select * from pessoa where senha = ?";
            PreparedStatement stmt2 = connection.prepareStatement(selectSenha);
            stmt2.setString(1, senhaInput.getText().toString());
            ResultSet rs2 = stmt2.executeQuery();



            if (rs.next()) {
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(Login.this, "Email ou Senha incorretos!", Toast.LENGTH_SHORT).show();
            }


            rs.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
});







    }
}