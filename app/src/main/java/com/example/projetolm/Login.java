package com.example.projetolm;
import static com.example.projetolm.Cadastro.hashPassword;
import static java.security.AccessController.getContext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import java.sql.Struct;

import javax.xml.transform.Result;

public class Login extends AppCompatActivity{

    EditText emailInput;
    EditText senhaInput;
    ImageView btEntrar;
    TextView btRecuperarSenhaLogin, btCadastreSeLogin;
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0'); // garantir 2 dígitos
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    /* ARRUMAR OS REDIRECIONAMENTOS DOS LINKS*/

btRecuperarSenhaLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://localhost:3000/login/esqueci/index"));
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

        if (connection == null) {
            Toast.makeText(Login.this, "Erro ao conectar ao banco de dados.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String selectLogin = "select * from pessoas where email = ?";
            String senhaDigitada = senhaInput.getText().toString();
            String hashDigitado = hashPassword(senhaDigitada);
            PreparedStatement stmt = connection.prepareStatement(selectLogin);
            stmt.setString(1, emailInput.getText().toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (hashDigitado != null && hashDigitado.equals(rs.getString("senha_hash"))) {
                    ProjetoLM app = (ProjetoLM) getApplicationContext();
                    app.setIdPessoaJava(rs.getString("id_pessoa"));
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{Toast.makeText(Login.this, "Email ou Senha incorretos!", Toast.LENGTH_SHORT).show();}
            }
            else{Toast.makeText(Login.this, "Email ou Senha incorretos!", Toast.LENGTH_SHORT).show();}




            rs.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
});







    }
}