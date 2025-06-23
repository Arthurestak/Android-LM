package com.example.projetolm.ui.autor;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projetolm.ConexaoMySQL;
import com.example.projetolm.ProjetoLM;
import com.example.projetolm.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class
AutorFragment extends Fragment {

    public AutorFragment() {
        // Construtor vazio obrigatório
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_autor, container, false);

        // Referências dos elementos da UI
        TextView nomeLivroAutor1 = view.findViewById(R.id.nomeLivroAutor1);
        TextView nomeLivroAutor2 = view.findViewById(R.id.nomeLivroAutor2);
        TextView nomeLivroAutor3 = view.findViewById(R.id.nomeLivroAutor3);

        TextView situacaoLivro1 = view.findViewById(R.id.situacaoLivro1);
        TextView situacaoLivro2 = view.findViewById(R.id.situacaoLivro2);
        TextView situacaoLivro3 = view.findViewById(R.id.situacaoLivro3);

        ImageView imgArquivo1 = view.findViewById(R.id.imgArquivo1);
        ImageView imgArquivo2 = view.findViewById(R.id.imgArquivo2);
        ImageView imgArquivo3 = view.findViewById(R.id.imgArquivo3);
        ImageView btRefreshAutor = view.findViewById(R.id.btRefreshAutor);
        ImageView btPesquisarAutor = view.findViewById(R.id.btPesquisarAutor);


        EditText pesquisarInputAutor = view.findViewById(R.id.pesquisarInputAutor);

        // Pega o ID da pessoa logada
        ProjetoLM app = (ProjetoLM) getActivity().getApplicationContext();
        String idPessoaJava = app.getIdPessoaJava();

        Connection connection = ConexaoMySQL.conectar();

        try {
            String sql = "SELECT * FROM livros_enviados WHERE id_autor = (SELECT id_autor FROM autor WHERE id_pessoa = ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, idPessoaJava);
            ResultSet rs = stmt.executeQuery();

            int contador = 0;
            while (rs.next() && contador < 3) {
                String titulo = rs.getString("titulo");
                String situacao = rs.getString("situacao");

                // Obtém a imagem como byte[]
                byte[] capaBytes = rs.getBytes("capa_img");
                if (capaBytes == null) continue;

                if (contador == 0) {
                    nomeLivroAutor1.setText(titulo);
                    situacaoLivro1.setText(situacao);
                    Glide.with(requireContext())
                            .load(capaBytes)
                            .into(imgArquivo1);
                } else if (contador == 1) {
                    nomeLivroAutor2.setText(titulo);
                    situacaoLivro2.setText(situacao);
                    Glide.with(requireContext())
                            .load(capaBytes)
                            .into(imgArquivo2);
                } else if (contador == 2) {
                    nomeLivroAutor3.setText(titulo);
                    situacaoLivro3.setText(situacao);
                    Glide.with(requireContext())
                            .load(capaBytes)
                            .into(imgArquivo3);
                }

                contador++;
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        btPesquisarAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String termoBusca = pesquisarInputAutor.getText().toString().trim();

                try {
                    String sql = "SELECT * FROM livros_enviados WHERE titulo like = ? and id_autor = (SELECT id_autor FROM autor WHERE id_pessoa = ?)";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1, '%'+termoBusca+'%');
                    stmt.setString(2, idPessoaJava);
                    ResultSet rs = stmt.executeQuery();

                    int contador = 0;
                    while (rs.next() && contador < 3) {
                        String titulo = rs.getString("titulo");
                        String situacao = rs.getString("situacao");

                        // Obtém a imagem como byte[]
                        byte[] capaBytes = rs.getBytes("capa_img");
                        if (capaBytes == null) continue;

                        if (contador == 0) {
                            nomeLivroAutor1.setText(titulo);
                            situacaoLivro1.setText(situacao);
                            Glide.with(requireContext())
                                    .load(capaBytes)
                                    .into(imgArquivo1);
                        } else if (contador == 1) {
                            nomeLivroAutor2.setText(titulo);
                            situacaoLivro2.setText(situacao);
                            Glide.with(requireContext())
                                    .load(capaBytes)
                                    .into(imgArquivo2);
                        } else if (contador == 2) {
                            nomeLivroAutor3.setText(titulo);
                            situacaoLivro3.setText(situacao);
                            Glide.with(requireContext())
                                    .load(capaBytes)
                                    .into(imgArquivo3);
                        }

                        contador++;
                    }

                    rs.close();
                    stmt.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        btRefreshAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String sql = "SELECT * FROM livros_enviados WHERE id_autor = (SELECT id_autor FROM autor WHERE id_pessoa = ?)";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1, idPessoaJava);
                    ResultSet rs = stmt.executeQuery();

                    int contador = 0;
                    while (rs.next() && contador < 3) {
                        String titulo = rs.getString("titulo");
                        String situacao = rs.getString("situacao");

                        // Obtém a imagem como byte[]
                        byte[] capaBytes = rs.getBytes("capa_img");
                        if (capaBytes == null) continue;

                        if (contador == 0) {
                            nomeLivroAutor1.setText(titulo);
                            situacaoLivro1.setText(situacao);
                            Glide.with(requireContext())
                                    .load(capaBytes)
                                    .into(imgArquivo1);
                        } else if (contador == 1) {
                            nomeLivroAutor2.setText(titulo);
                            situacaoLivro2.setText(situacao);
                            Glide.with(requireContext())
                                    .load(capaBytes)
                                    .into(imgArquivo2);
                        } else if (contador == 2) {
                            nomeLivroAutor3.setText(titulo);
                            situacaoLivro3.setText(situacao);
                            Glide.with(requireContext())
                                    .load(capaBytes)
                                    .into(imgArquivo3);
                        }

                        contador++;
                    }

                    rs.close();
                    stmt.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}