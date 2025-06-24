package com.example.projetolm.ui.livros;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.projetolm.ConexaoMySQL;
import com.example.projetolm.Login;
import com.example.projetolm.MainActivity;
import com.example.projetolm.R;
import com.example.projetolm.databinding.FragmentLivrosBinding;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class LivrosFragment extends Fragment {

    private FragmentLivrosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LivrosViewModel livrosViewModel =
                new ViewModelProvider(this).get(LivrosViewModel.class);

        binding = FragmentLivrosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_livros, container, false);

        final ImageView imgLivro1 = view.findViewById(R.id.imgLivro1);
        final ImageView imgLivro2 = view.findViewById(R.id.imgLivro2);
        final ImageView imgLivro3 = view.findViewById(R.id.imgLivro3);
        final ImageView imgLivro4 = view.findViewById(R.id.imgLivro4);

        TextView nomeLivro1 = view.findViewById(R.id.nomeLivro1);
        TextView nomeLivro2 = view.findViewById(R.id.nomeLivro2);
        TextView nomeLivro3 = view.findViewById(R.id.nomeLivro3);
        TextView nomeLivro4 = view.findViewById(R.id.nomeLivro4);
        EditText pesquisarInput = view.findViewById(R.id.pesquisarInput);

        final TextView descricaoLivro1 = view.findViewById(R.id.descricaoLivro1);
        final TextView descricaoLivro2 = view.findViewById(R.id.descricaoLivro2);
        final TextView descricaoLivro3 = view.findViewById(R.id.descricaoLivro3);
        final TextView descricaoLivro4 = view.findViewById(R.id.descricaoLivro4);

        ImageView btRefresh = view.findViewById(R.id.btRefresh);
        ImageView btPesquisar = view.findViewById(R.id.btPesquisar);

        final String[] imageUrl = new String[1];
        final String[] image2Url = new String[1];
        final String[] image3Url = new String[1];
        final String[] image4Url = new String[1];




        Connection connection = ConexaoMySQL.conectar();

        String[] listaIds = new String[4];
        String[] titulos = new String[4];
        String[] categorias = new String[4];
        Bitmap[] imagens = new Bitmap[4]; // armazenar imagens convertidas

        try {
            String query = "SELECT id_livro, titulo, categoria, capa_img FROM livros ORDER BY RAND() LIMIT 4";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int i = 0;
            while (rs.next() && i < 4) {
                listaIds[i] = rs.getString("id_livro");
                titulos[i] = rs.getString("titulo");
                categorias[i] = rs.getString("categoria");

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
            nomeLivro1.setText(titulos[0]);
            descricaoLivro1.setText(categorias[0]);
            if (imagens[0] != null) imgLivro1.setImageBitmap(imagens[0]);

            nomeLivro2.setText(titulos[1]);
            descricaoLivro2.setText(categorias[1]);
            if (imagens[1] != null) imgLivro2.setImageBitmap(imagens[1]);

            nomeLivro3.setText(titulos[2]);
            descricaoLivro3.setText(categorias[2]);
            if (imagens[2] != null) imgLivro3.setImageBitmap(imagens[2]);

            nomeLivro4.setText(titulos[3]);
            descricaoLivro4.setText(categorias[3]);
            if (imagens[3] != null) imgLivro4.setImageBitmap(imagens[3]);

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Erro ao carregar livros", Toast.LENGTH_SHORT).show();
        }

        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection connection = ConexaoMySQL.conectar();

                String[] listaIds = new String[4];
                String[] titulos = new String[4];
                String[] categorias = new String[4];
                Bitmap[] imagens = new Bitmap[4]; // armazenar imagens convertidas

                try {
                    String query = "SELECT id_livro, titulo, categoria, capa_img FROM livros ORDER BY RAND() LIMIT 4";
                    PreparedStatement stmt = connection.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();

                    int i = 0;
                    while (rs.next() && i < 4) {
                        listaIds[i] = rs.getString("id_livro");
                        titulos[i] = rs.getString("titulo");
                        categorias[i] = rs.getString("categoria");

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
                    nomeLivro1.setText(titulos[0]);
                    descricaoLivro1.setText(categorias[0]);
                    if (imagens[0] != null) imgLivro1.setImageBitmap(imagens[0]);

                    nomeLivro2.setText(titulos[1]);
                    descricaoLivro2.setText(categorias[1]);
                    if (imagens[1] != null) imgLivro2.setImageBitmap(imagens[1]);

                    nomeLivro3.setText(titulos[2]);
                    descricaoLivro3.setText(categorias[2]);
                    if (imagens[2] != null) imgLivro3.setImageBitmap(imagens[2]);

                    nomeLivro4.setText(titulos[3]);
                    descricaoLivro4.setText(categorias[3]);
                    if (imagens[3] != null) imgLivro4.setImageBitmap(imagens[3]);

                    rs.close();
                    stmt.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "Erro ao carregar livros", Toast.LENGTH_SHORT).show();
                }
            }
        });



        nomeLivro1.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
            startActivity(intent);
            }
        });
        nomeLivro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
                startActivity(intent);
            }
        });
        nomeLivro3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
                startActivity(intent);
                }
            });
        nomeLivro4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
               startActivity(intent);
            }
        });

        descricaoLivro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
                startActivity(intent);
            }
        });
        descricaoLivro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
                startActivity(intent);
            }
        });
        descricaoLivro3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
                startActivity(intent);
            }
        });
        descricaoLivro4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.com"));
                startActivity(intent);
            }
        });

        btPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (Connection conn = ConexaoMySQL.conectar()) {
                    if (conn == null || conn.isClosed()) {
                        Log.e("Erro", "Conexão não pode ser estabelecida");
                        return;
                    }

                    String termoBusca = pesquisarInput.getText().toString().trim();

                    if (!termoBusca.isEmpty()) {
                        String query = "SELECT id_livro, titulo, categoria, capa_img FROM livros WHERE titulo LIKE ? ORDER BY rand() LIMIT 4";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, "%" + termoBusca + "%");

                        ResultSet rs = stmt.executeQuery();

                        int i = 0;
                        while (rs.next()) {
                            String id = rs.getString("id_livro");
                            String titulo = rs.getString("titulo");
                            String categoria = rs.getString("categoria");
                            String imagemUrl = rs.getString("capa_img");

                            listaIds[i] = id;

                            switch (i) {
                                case 0:
                                    nomeLivro1.setText(titulo);
                                    descricaoLivro1.setText(categoria);
                                    Glide.with(requireContext()).load(imagemUrl).into(imgLivro1);
                                    break;
                                case 1:
                                    nomeLivro2.setText(titulo);
                                    descricaoLivro2.setText(categoria);
                                    Glide.with(requireContext()).load(imagemUrl).into(imgLivro2);
                                    break;
                                case 2:
                                    nomeLivro3.setText(titulo);
                                    descricaoLivro3.setText(categoria);
                                    Glide.with(requireContext()).load(imagemUrl).into(imgLivro3);
                                    break;
                                case 3:
                                    nomeLivro4.setText(titulo);
                                    descricaoLivro4.setText(categoria);
                                    Glide.with(requireContext()).load(imagemUrl).into(imgLivro4);
                                    break;
                            }

                            i++;
                        }

                        rs.close();
                        stmt.close();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}