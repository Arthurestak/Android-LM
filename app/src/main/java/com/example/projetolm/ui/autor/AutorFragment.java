package com.example.projetolm.ui.autor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projetolm.Cadastro;
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

        String[] listaIds = new String[4];
        String[] titulos = new String[4];
        String[] categorias = new String[4];
        Bitmap[] imagens = new Bitmap[4];


        try {
            String sql = "SELECT * FROM livros_enviados WHERE id_autor = (SELECT id_autor FROM autor WHERE id_pessoa = ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, idPessoaJava);
            ResultSet rs = stmt.executeQuery();


            int i = 0;
            while (rs.next() && i < 3) {
                listaIds[i] = rs.getString("id_livro_enviado");
                String titulo = rs.getString("titulo");
                String situacao = rs.getString("situacao");

                // Obtém a imagem como byte[]
                byte[] capaBytes = rs.getBytes("capa_img");
                if (capaBytes == null) continue;

                if (i == 0) {
                    nomeLivroAutor1.setText(titulo);
                    situacaoLivro1.setText(situacao);
                    Glide.with(requireContext())
                            .load(capaBytes)
                            .into(imgArquivo1);
                } else if (i == 1) {
                    nomeLivroAutor2.setText(titulo);
                    situacaoLivro2.setText(situacao);
                    Glide.with(requireContext())
                            .load(capaBytes)
                            .into(imgArquivo2);
                } else if (i == 2) {
                    nomeLivroAutor3.setText(titulo);
                    situacaoLivro3.setText(situacao);
                    Glide.with(requireContext())
                            .load(capaBytes)
                            .into(imgArquivo3);
                }

                i++;
            }

            rs.close();
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


        btPesquisarAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Limpa os campos
                nomeLivroAutor1.setText("");
                situacaoLivro1.setText("");
                imgArquivo1.setImageDrawable(null); // limpa imagem

                nomeLivroAutor2.setText("");
                situacaoLivro2.setText("");
                imgArquivo2.setImageDrawable(null);

                nomeLivroAutor3.setText("");
                situacaoLivro3.setText("");
                imgArquivo3.setImageDrawable(null);

                // 2. Obtém o termo de busca
                String termoBusca = pesquisarInputAutor.getText().toString().trim();

                if (!termoBusca.isEmpty()) {
                    Connection connection = ConexaoMySQL.conectar();

                    String[] listaIds = new String[4];
                    String[] titulos = new String[4];
                    String[] categorias = new String[4];
                    Bitmap[] imagens = new Bitmap[4];

                    try {
                        String query = "SELECT * FROM livros_enviados WHERE titulo like ? and id_autor = (SELECT id_autor FROM autor WHERE id_pessoa = ?)";
                        PreparedStatement stmt = connection.prepareStatement(query);
                        stmt.setString(1, "%" + termoBusca + "%"); // ✅ Aqui corrigimos
                        stmt.setString(2, idPessoaJava);
                        ResultSet rs = stmt.executeQuery();

                        int i = 0;
                        while (rs.next() && i < 3) {
                            listaIds[i] = rs.getString("id_livro_enviado");
                            String titulo = rs.getString("titulo");
                            String situacao = rs.getString("situacao");

                            // Obtém a imagem como byte[]
                            byte[] capaBytes = rs.getBytes("capa_img");
                            if (capaBytes == null) continue;

                            if (i == 0) {
                                nomeLivroAutor1.setText(titulo);
                                situacaoLivro1.setText(situacao);
                                Glide.with(requireContext())
                                        .load(capaBytes)
                                        .into(imgArquivo1);
                            } else if (i == 1) {
                                nomeLivroAutor2.setText(titulo);
                                situacaoLivro2.setText(situacao);
                                Glide.with(requireContext())
                                        .load(capaBytes)
                                        .into(imgArquivo2);
                            } else if (i == 2) {
                                nomeLivroAutor3.setText(titulo);
                                situacaoLivro3.setText(situacao);
                                Glide.with(requireContext())
                                        .load(capaBytes)
                                        .into(imgArquivo3);
                            }

                            i++;
                        }

                        rs.close();
                        stmt.close();


                    } catch (SQLException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Erro ao carregar livros", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Digite um título para buscar.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btRefreshAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] listaIds = new String[4];
                String[] titulos = new String[4];
                String[] situacao = new String[4];
                Bitmap[] imagens = new Bitmap[4];

                try {
                    String sql = "SELECT * FROM livros_enviados WHERE id_autor = (SELECT id_autor FROM autor WHERE id_pessoa = ?)";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1, idPessoaJava);
                    ResultSet rs = stmt.executeQuery();

                    int i = 0;
                    while (rs.next() && i < 3) {
                        listaIds[i] = rs.getString("id_livro_enviado");
                        titulos[i] = rs.getString("titulo");
                        situacao[i] = rs.getString("situacao");


                        // Recupera os bytes da imagem e converte em Bitmap
                        byte[] imageBytes = rs.getBytes("capa_img");
                        if (imageBytes != null) {
                            imagens[i] = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        } else {
                            imagens[i] = null; // imagem não encontrada
                        }

                        i++;
                    }


                    // Exibir na interface
                    nomeLivroAutor1.setText(titulos[0]);
                    situacaoLivro1.setText(situacao[0]);
                    if (imagens[0] != null) imgArquivo1.setImageBitmap(imagens[0]);

                    nomeLivroAutor2.setText(titulos[1]);
                    situacaoLivro2.setText(situacao[1]);
                    if (imagens[1] != null) imgArquivo2.setImageBitmap(imagens[1]);

                    nomeLivroAutor3.setText(titulos[2]);
                    situacaoLivro3.setText(situacao[2]);
                    if (imagens[2] != null) imgArquivo3.setImageBitmap(imagens[2]);

                    rs.close();
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "Erro ao carregar livros", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}