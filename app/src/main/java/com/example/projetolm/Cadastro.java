package com.example.projetolm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cadastro extends AppCompatActivity {

    EditText nomeInputCadastro, emailInputCadastro, cpfInputCadastro, confirmaSenhaInputCadastro, senhaInputCadastro;
    ImageView btCadastrar, btVoltar;

    public static boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nomeInputCadastro = findViewById(R.id.nomeInputCadastro);
        emailInputCadastro = findViewById(R.id.emailInputCadastro);
        cpfInputCadastro = findViewById(R.id.cpfInputCadastro);
        senhaInputCadastro = findViewById(R.id.senhaInputCadastro);
        confirmaSenhaInputCadastro = findViewById(R.id.confirmaSenhaInputCadastro);
        btCadastrar = findViewById(R.id.btCadastrar);
        btVoltar = findViewById(R.id.btVoltar);



        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Connection connection = ConexaoMySQL.conectar();

                if (connection == null) {
                    Toast.makeText(Cadastro.this, "Erro ao conectar ao banco de dados.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidEmail(emailInputCadastro.getText().toString())){
                    Toast.makeText(Cadastro.this, "Formato de email inválido!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(nomeInputCadastro.getText().isEmpty() || emailInputCadastro.getText().isEmpty() || cpfInputCadastro.getText().isEmpty() || senhaInputCadastro.getText().isEmpty()){
                    Toast.makeText(Cadastro.this, "Um ou mais campos estão vazios!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!senhaInputCadastro.getText().toString().equals(confirmaSenhaInputCadastro.getText().toString())){
                    Toast.makeText(Cadastro.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(senhaInputCadastro.getText().toString().length() >= 21){
                    Toast.makeText(Cadastro.this, "A senha deve conter no máximo 20 caracteres!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(senhaInputCadastro.getText().toString().length() <= 7){
                    Toast.makeText(Cadastro.this, "A senha deve conter no mínimo 8 caracteres!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(cpfInputCadastro.getText().toString().length() >= 16){
                    Toast.makeText(Cadastro.this, "O CPF deve conter no máximo 15 caracteres!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{

                    String insertCadastro = "INSERT INTO pessoas (nome,email,cpf, situacao, senha_hash) VALUES (?,?,?,'A',?)";
                    PreparedStatement stmt = connection.prepareStatement(insertCadastro, PreparedStatement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, nomeInputCadastro.getText().toString());
                    stmt.setString(2, emailInputCadastro.getText().toString());
                    stmt.setString(3, cpfInputCadastro.getText().toString());
                    stmt.setString(4, hashPassword(senhaInputCadastro.getText().toString()));
                    stmt.execute();

                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    int idPessoaGerada = -1;
                    if (generatedKeys.next()) {
                        idPessoaGerada = generatedKeys.getInt(1);
                    } else {
                        Toast.makeText(Cadastro.this, "Erro ao obter o ID da pessoa!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String addAutor = "INSERT INTO assinante (pagamento, id_pessoa) VALUES ('Atualizado', ?)";
                    PreparedStatement stmtAddAutor = connection.prepareStatement(addAutor);
                    stmtAddAutor.setInt(1, idPessoaGerada);
                    stmtAddAutor.execute();

                    Toast.makeText(Cadastro.this, "Usuário cadastrado!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Cadastro.this,Login.class);
                    startActivity(intent);
                    nomeInputCadastro.setText("");
                    emailInputCadastro.setText("");
                    cpfInputCadastro.setText("");
                    senhaInputCadastro.setText("");
                    confirmaSenhaInputCadastro.setText("");

                }catch (SQLException e) {
                    Toast.makeText(Cadastro.this, "Já existe um usuário com este cpf cadastrado!", Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cadastro.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
