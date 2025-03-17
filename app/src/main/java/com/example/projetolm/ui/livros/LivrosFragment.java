package com.example.projetolm.ui.livros;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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



        try {
            String imagemLivro = "select nome_arquivos from livros order by rand()";
            String nomeLivro = "select nome_livro from livros where nome_arquivos = ?";
            String descricaoLivro = "select categoria_livro from livros where nome_arquivos = ?";
             PreparedStatement stmt = connection.prepareStatement(imagemLivro);
             PreparedStatement stmt1 = connection.prepareStatement(nomeLivro);
             PreparedStatement stmt2 = connection.prepareStatement(descricaoLivro);
             ResultSet rs = stmt.executeQuery();


             if(rs.next()){
                 imageUrl[0] = rs.getString("nome_arquivos");
                 stmt1.setString(1, imageUrl[0]);
                 stmt2.setString(1, imageUrl[0]);
                 ResultSet rs1 = stmt1.executeQuery();
                 if(rs1.next()){
                     nomeLivro1.setText(rs1.getString("nome_livro"));
                     ResultSet rs2 = stmt2.executeQuery();
                     if(rs2.next()){
                         descricaoLivro1.setText(rs2.getString("categoria_livro"));
                     }

                 }
                 Glide.with(requireContext())
                         .load(imageUrl[0])
                         .into(imgLivro1);
             }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        try {
            String imagem2Livro = "select nome_arquivos from livros order by rand()";
            String nome2Livro = "select nome_livro from livros where nome_arquivos = ?";
            String descricao2Livro = "select categoria_livro from livros where nome_arquivos = ?";

            PreparedStatement stmt = connection.prepareStatement(imagem2Livro);
            PreparedStatement stmt1 = connection.prepareStatement(nome2Livro);
            PreparedStatement stmt2 = connection.prepareStatement(descricao2Livro);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                image2Url[0] = rs.getString("nome_arquivos");
                stmt1.setString(1, image2Url[0]);
                stmt2.setString(1, image2Url[0]);

                ResultSet rs1 = stmt1.executeQuery();
                if(rs1.next()){
                    nomeLivro2.setText(rs1.getString("nome_livro"));
                    ResultSet rs2 = stmt2.executeQuery();
                    if(rs2.next()){
                        descricaoLivro2.setText(rs2.getString("categoria_livro"));
                    }
                }
                Glide.with(requireContext())
                        .load(image2Url[0])
                        .into(imgLivro2);
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        try {
            String imagem3Livro = "select nome_arquivos from livros order by rand()";
            String nome3Livro = "select nome_livro from livros where nome_arquivos = ?";
            String descricao3Livro = "select categoria_livro from livros where nome_arquivos = ?";

            PreparedStatement stmt = connection.prepareStatement(imagem3Livro);
            PreparedStatement stmt1 = connection.prepareStatement(nome3Livro);
            PreparedStatement stmt2 = connection.prepareStatement(descricao3Livro);
            ResultSet rs = stmt.executeQuery();


            if(rs.next()){
                image3Url[0] = rs.getString("nome_arquivos");
                stmt1.setString(1, image3Url[0]);
                stmt2.setString(1, image3Url[0]);
                ResultSet rs1 = stmt1.executeQuery();
                if(rs1.next()){
                    nomeLivro3.setText(rs1.getString("nome_livro"));
                    ResultSet rs2 = stmt2.executeQuery();
                    if(rs2.next()){
                        descricaoLivro3.setText(rs2.getString("categoria_livro"));
                    }

                }
                Glide.with(requireContext())
                        .load(image3Url[0])
                        .into(imgLivro3);
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        try {
            String imagem4Livro = "select nome_arquivos from livros order by rand()";
            String nome4Livro = "select nome_livro from livros where nome_arquivos = ?";
            String descricao4Livro = "select categoria_livro from livros where nome_arquivos = ?";
            PreparedStatement stmt = connection.prepareStatement(imagem4Livro);
            PreparedStatement stmt1 = connection.prepareStatement(nome4Livro);
            PreparedStatement stmt2 = connection.prepareStatement(descricao4Livro);
            ResultSet rs = stmt.executeQuery();


            if(rs.next()){
                image4Url[0] = rs.getString("nome_arquivos");
                stmt1.setString(1, image4Url[0]);
                stmt2.setString(1, image4Url[0]);

                ResultSet rs1 = stmt1.executeQuery();
                if(rs1.next()){
                    nomeLivro4.setText(rs1.getString("nome_livro"));
                    ResultSet rs2 = stmt2.executeQuery();
                    if(rs2.next()){
                        descricaoLivro4.setText(rs2.getString("categoria_livro"));
                    }
                }
                Glide.with(requireContext())
                        .load(image4Url[0])
                        .into(imgLivro4);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String imagemLivro = "select nome_arquivos from livros order by rand()";
                    String nomeLivro = "select nome_livro from livros where nome_arquivos = ?";
                    String descricaoLivro = "select categoria_livro from livros where nome_arquivos = ?";
                    PreparedStatement stmt = connection.prepareStatement(imagemLivro);
                    PreparedStatement stmt1 = connection.prepareStatement(nomeLivro);
                    PreparedStatement stmt2 = connection.prepareStatement(descricaoLivro);
                    ResultSet rs = stmt.executeQuery();


                    if(rs.next()){
                        imageUrl[0] = rs.getString("nome_arquivos");
                        stmt1.setString(1, imageUrl[0]);
                        stmt2.setString(1, imageUrl[0]);
                        ResultSet rs1 = stmt1.executeQuery();
                        if(rs1.next()){
                            nomeLivro1.setText(rs1.getString("nome_livro"));
                            ResultSet rs2 = stmt2.executeQuery();
                            if(rs2.next()){
                                descricaoLivro1.setText(rs2.getString("categoria_livro"));
                            }

                        }
                        Glide.with(requireContext())
                                .load(imageUrl[0])
                                .into(imgLivro1);
                    }
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
                try {
                    String imagem2Livro = "select nome_arquivos from livros order by rand()";
                    String nome2Livro = "select nome_livro from livros where nome_arquivos = ?";
                    String descricao2Livro = "select categoria_livro from livros where nome_arquivos = ?";

                    PreparedStatement stmt = connection.prepareStatement(imagem2Livro);
                    PreparedStatement stmt1 = connection.prepareStatement(nome2Livro);
                    PreparedStatement stmt2 = connection.prepareStatement(descricao2Livro);
                    ResultSet rs = stmt.executeQuery();

                    if(rs.next()){
                        image2Url[0] = rs.getString("nome_arquivos");
                        stmt1.setString(1, image2Url[0]);
                        stmt2.setString(1, image2Url[0]);

                        ResultSet rs1 = stmt1.executeQuery();
                        if(rs1.next()){
                            nomeLivro2.setText(rs1.getString("nome_livro"));
                            ResultSet rs2 = stmt2.executeQuery();
                            if(rs2.next()){
                                descricaoLivro2.setText(rs2.getString("categoria_livro"));
                            }
                        }
                        Glide.with(requireContext())
                                .load(image2Url[0])
                                .into(imgLivro2);
                    }


                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
                try {
                    String imagem3Livro = "select nome_arquivos from livros order by rand()";
                    String nome3Livro = "select nome_livro from livros where nome_arquivos = ?";
                    String descricao3Livro = "select categoria_livro from livros where nome_arquivos = ?";

                    PreparedStatement stmt = connection.prepareStatement(imagem3Livro);
                    PreparedStatement stmt1 = connection.prepareStatement(nome3Livro);
                    PreparedStatement stmt2 = connection.prepareStatement(descricao3Livro);
                    ResultSet rs = stmt.executeQuery();


                    if(rs.next()){
                        image3Url[0] = rs.getString("nome_arquivos");
                        stmt1.setString(1, image3Url[0]);
                        stmt2.setString(1, image3Url[0]);
                        ResultSet rs1 = stmt1.executeQuery();
                        if(rs1.next()){
                            nomeLivro3.setText(rs1.getString("nome_livro"));
                            ResultSet rs2 = stmt2.executeQuery();
                            if(rs2.next()){
                                descricaoLivro3.setText(rs2.getString("categoria_livro"));
                            }

                        }
                        Glide.with(requireContext())
                                .load(image3Url[0])
                                .into(imgLivro3);
                    }


                }catch (SQLException e){
                    throw new RuntimeException(e);
                }

                try {
                    String imagem4Livro = "select nome_arquivos from livros order by rand()";
                    String nome4Livro = "select nome_livro from livros where nome_arquivos = ?";
                    String descricao4Livro = "select categoria_livro from livros where nome_arquivos = ?";
                    PreparedStatement stmt = connection.prepareStatement(imagem4Livro);
                    PreparedStatement stmt1 = connection.prepareStatement(nome4Livro);
                    PreparedStatement stmt2 = connection.prepareStatement(descricao4Livro);
                    ResultSet rs = stmt.executeQuery();


                    if(rs.next()){
                        image4Url[0] = rs.getString("nome_arquivos");
                        stmt1.setString(1, image4Url[0]);
                        stmt2.setString(1, image4Url[0]);

                        ResultSet rs1 = stmt1.executeQuery();
                        if(rs1.next()){
                            nomeLivro4.setText(rs1.getString("nome_livro"));
                            ResultSet rs2 = stmt2.executeQuery();
                            if(rs2.next()){
                                descricaoLivro4.setText(rs2.getString("categoria_livro"));
                            }
                        }
                        Glide.with(requireContext())
                                .load(image4Url[0])
                                .into(imgLivro4);
                    }

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


                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        });

        btPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String pesquisa = "select * from livros where nome_livro like ?";
                    PreparedStatement stmt = connection.prepareStatement(pesquisa);
                    stmt.setString(1, "%" + pesquisarInput.getText().toString() + "%");
                    ResultSet rs = stmt.executeQuery();

                    TextView[] nomeLivros = {nomeLivro1,nomeLivro2,nomeLivro3,nomeLivro4};
                    TextView[] descricaoLivros = {descricaoLivro1,descricaoLivro2,descricaoLivro3,descricaoLivro4};
                    ImageView[] imgLivros = {imgLivro1,imgLivro2,imgLivro3,imgLivro4};



                    if(rs.next()){
                        String imagemUrl = rs.getString("nome_arquivos");
                        String nome = rs.getString("nome_livro");
                        String descricao = rs.getString("categoria_livro");

                        nomeLivro1.setText(nome);
                        descricaoLivro1.setText(descricao);
                        Glide.with(requireContext())
                                .load(imagemUrl)
                                .into(imgLivro1);

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        return view;
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}