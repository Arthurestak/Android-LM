package com.example.projetolm.ui.livros;

import android.annotation.SuppressLint;
import android.content.Intent;
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

    private int clicado = 0;

    private FragmentLivrosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LivrosViewModel livrosViewModel =
                new ViewModelProvider(this).get(LivrosViewModel.class);

        binding = FragmentLivrosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_livros, container, false);

        ImageView imgLivro1 = view.findViewById(R.id.imgLivro1);
        ImageView imgLivro2 = view.findViewById(R.id.imgLivro2);
        ImageView imgLivro3 = view.findViewById(R.id.imgLivro3);
        ImageView imgLivro4 = view.findViewById(R.id.imgLivro4);

        TextView nomeLivro1 = view.findViewById(R.id.nomeLivro1);
        TextView nomeLivro2 = view.findViewById(R.id.nomeLivro2);
        TextView nomeLivro3 = view.findViewById(R.id.nomeLivro3);
        TextView nomeLivro4 = view.findViewById(R.id.nomeLivro4);

        TextView descricaoLivro1 = view.findViewById(R.id.descricaoLivro1);
        TextView descricaoLivro2 = view.findViewById(R.id.descricaoLivro2);
        TextView descricaoLivro3 = view.findViewById(R.id.descricaoLivro3);
        TextView descricaoLivro4 = view.findViewById(R.id.descricaoLivro4);

        ImageView btRefresh = view.findViewById(R.id.btRefresh);

        String imageUrl;
        String image2Url;
        String image3Url;
        String image4Url;



        Connection connection = ConexaoMySQL.conectar();
        while (true){

            try {
                String imagemLivro = "select nome_arquivos from livros order by rand()";
                String nomeLivro = "select nome_livro from livros where nome_arquivos = ?";
                String descricaoLivro = "select categoria_livro from livros where nome_arquivos = ?";

                PreparedStatement stmt = connection.prepareStatement(imagemLivro);
                PreparedStatement stmt1 = connection.prepareStatement(nomeLivro);
                PreparedStatement stmt2 = connection.prepareStatement(descricaoLivro);
                ResultSet rs = stmt.executeQuery();


                if(rs.next()){
                    imageUrl = rs.getString("nome_arquivos");
                    stmt1.setString(1,imageUrl);
                    stmt2.setString(1,imageUrl);

                    ResultSet rs1 = stmt1.executeQuery();
                    if(rs1.next()){
                        nomeLivro1.setText(rs1.getString("nome_livro"));
                        ResultSet rs2 = stmt2.executeQuery();
                        if(rs2.next()){
                            descricaoLivro1.setText(rs2.getString("categoria_livro"));
                        }

                    }
                    Glide.with(requireContext())
                            .load(imageUrl)
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
                    image2Url = rs.getString("nome_arquivos");
                    stmt1.setString(1,image2Url);
                    stmt2.setString(1,image2Url);

                    ResultSet rs1 = stmt1.executeQuery();
                    if(rs1.next()){
                        nomeLivro2.setText(rs1.getString("nome_livro"));
                        ResultSet rs2 = stmt2.executeQuery();
                        if(rs2.next()){
                            descricaoLivro2.setText(rs2.getString("categoria_livro"));
                        }

                    }
                    Glide.with(requireContext())
                            .load(image2Url)
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
                    image3Url = rs.getString("nome_arquivos");
                    stmt1.setString(1,image3Url);
                    stmt2.setString(1,image3Url);

                    ResultSet rs1 = stmt1.executeQuery();
                    if(rs1.next()){
                        nomeLivro3.setText(rs1.getString("nome_livro"));
                        ResultSet rs2 = stmt2.executeQuery();
                        if(rs2.next()){
                            descricaoLivro3.setText(rs2.getString("categoria_livro"));
                        }

                    }
                    Glide.with(requireContext())
                            .load(image3Url)
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
                    image4Url = rs.getString("nome_arquivos");
                    stmt1.setString(1,image4Url);
                    stmt2.setString(1,image4Url);

                    ResultSet rs1 = stmt1.executeQuery();
                    if(rs1.next()){
                        nomeLivro4.setText(rs1.getString("nome_livro"));
                        ResultSet rs2 = stmt2.executeQuery();
                        if(rs2.next()){
                            descricaoLivro4.setText(rs2.getString("categoria_livro"));
                        }

                    }
                    Glide.with(requireContext())
                            .load(image4Url)
                            .into(imgLivro4);
                }



            }catch (SQLException e){
                throw new RuntimeException(e);
            }


            btRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicado++;
                }
            });
            if (clicado > 0){
                continue;
            }
            break;
        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}