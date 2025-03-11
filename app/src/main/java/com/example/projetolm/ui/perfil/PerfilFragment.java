package com.example.projetolm.ui.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetolm.ConexaoMySQL;
import com.example.projetolm.ProjetoLM;
import com.example.projetolm.R;
import com.example.projetolm.databinding.FragmentPerfilBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PerfilFragment extends Fragment {

    String idPessoaJava;

    public String getIdPessoaJava() {
        return idPessoaJava;
    }

    public void setIdPessoaJava(String idPessoaJava) {
        this.idPessoaJava = idPessoaJava;
    }

    ImageView btSalvar, btAssine;
    EditText campoNome, campoEmail, campoCpf;

    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);



        ImageView btAssine = view.findViewById(R.id.btAssine);
        ImageView btSalvar = view.findViewById(R.id.btSalvar);

        EditText campoNome = view.findViewById(R.id.campoNome);
        EditText campoEmail = view.findViewById(R.id.campoEmail);
        EditText campoCpf = view.findViewById(R.id.campoCpf);

        btAssine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
                    startActivity(intent);
            }
        });
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection connection = ConexaoMySQL.conectar();
                    String updatePessoa = "update pessoa set nome = ?, email = ?, cpf = ? where id_pessoa = ?";
                    PreparedStatement stmt = connection.prepareStatement(updatePessoa);
                    stmt.setString(1, campoNome.getText().toString());
                    stmt.setString(2, campoEmail.getText().toString());
                    stmt.setString(3, campoCpf.getText().toString());
                    stmt.setString(4, idPessoaJava);

                    stmt.executeUpdate();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}