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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetolm.ConexaoMySQL;
import com.example.projetolm.Login;
import com.example.projetolm.ProjetoLM;
import com.example.projetolm.R;
import com.example.projetolm.databinding.FragmentPerfilBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PerfilFragment extends Fragment {


    ImageView btSalvar, btAssine;
    EditText campoNome, campoEmail, campoCpf;

    private FragmentPerfilBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout do fragmento corretamente
        PerfilViewModel perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        ProjetoLM app = (ProjetoLM) getActivity().getApplicationContext();
        String idPessoaJava = app.getIdPessoaJava();

        // Acessa os elementos da interface usando a view inflada
        ImageView btSalvar = view.findViewById(R.id.btSalvar);
        ImageView btAssine = view.findViewById(R.id.btAssine);
        EditText campoNome = view.findViewById(R.id.campoNome);
        EditText campoEmail = view.findViewById(R.id.campoEmail);
        EditText campoCpf = view.findViewById(R.id.campoCpf);

        // Garante que o botão foi encontrado
        if (btSalvar != null) {
            btSalvar.setOnClickListener(v -> {
                String nome = campoNome.getText().toString().trim();
                String email = campoEmail.getText().toString().trim();
                String cpf = campoCpf.getText().toString().trim();

                // Aqui você pode salvar os dados, chamar ViewModel, etc.
                Toast.makeText(getContext(), "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
            });
        } else {
            Log.e("PerfilFragment", "btSalvar é null — ID incorreto ou layout não inflado?");
        }


        try {
            Connection connection = ConexaoMySQL.conectar();
            String selectPessoa = "select * from pessoa where id_pessoa = ?";
            PreparedStatement stmt = connection.prepareStatement(selectPessoa);
            stmt.setString(1, idPessoaJava);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                campoNome.setText(rs.getString("nome"));
                campoEmail.setText(rs.getString("email"));
                campoCpf.setText(rs.getString("cpf"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




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

                    Toast.makeText(getContext(), "Mudanças concluídas!", Toast.LENGTH_SHORT).show();

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