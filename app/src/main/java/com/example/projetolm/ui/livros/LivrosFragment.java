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



        /*
        Connection connection = ConexaoMySQL.conectar();

        String[] listaIds = new String[8];


        try {
            String id1 = "select id_livro from livros where capa_img = ?";
            String imagemLivro = "select capa_img from livros order by rand()";
            String nomeLivro = "select titulo from livros where capa_img = ?";
            String descricaoLivro = "select categoria from livros where capa_img = ?";

            PreparedStatement stmtId = connection.prepareStatement(id1);
            PreparedStatement stmt = connection.prepareStatement(imagemLivro);
            PreparedStatement stmt1 = connection.prepareStatement(nomeLivro);
            PreparedStatement stmt2 = connection.prepareStatement(descricaoLivro);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                imageUrl[0] = rs.getString("capa_img");
                stmt1.setString(1, imageUrl[0]);
                stmt2.setString(1, imageUrl[0]);
                stmtId.setString(1, imageUrl[0]);
                ResultSet rs1 = stmt1.executeQuery();
                if(rs1.next()){
                    nomeLivro1.setText(rs1.getString("titulo"));
                    ResultSet rs2 = stmt2.executeQuery();
                    if(rs2.next()){
                        descricaoLivro1.setText(rs2.getString("categoria"));
                        ResultSet rs3 = stmtId.executeQuery();
                        if(rs3.next()) {
                            listaIds[0] = rs3.getString("id_livro");

                        }
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
            String id2 = "select id_livro from livros where capa_img = ?";
            String imagem2Livro = "select capa_img from livros where id_livro not in (?) order by rand()";
            String nome2Livro = "select titulo from livros where capa_img = ?";
            String descricao2Livro = "select categoria from livros where capa_img = ?";
            PreparedStatement stmtId = connection.prepareStatement(id2);
            PreparedStatement stmt = connection.prepareStatement(imagem2Livro);
            PreparedStatement stmt1 = connection.prepareStatement(nome2Livro);
            PreparedStatement stmt2 = connection.prepareStatement(descricao2Livro);
            stmt.setString(1, listaIds[0]);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                image2Url[0] = rs.getString("capa_img");
                stmt1.setString(1, image2Url[0]);
                stmt2.setString(1, image2Url[0]);
                stmtId.setString(1, image2Url[0]);
                ResultSet rs1 = stmt1.executeQuery();

                if(rs1.next()){
                    nomeLivro2.setText(rs1.getString("titulo"));
                    ResultSet rs2 = stmt2.executeQuery();

                    if(rs2.next()){
                        descricaoLivro2.setText(rs2.getString("categoria"));
                        ResultSet rs3 = stmtId.executeQuery();

                        if(rs3.next()) {
                            listaIds[1] = rs3.getString("id_livro");
                        }
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
            String id3 = "select id_livro from livros where capa_img = ?";
            String imagem3Livro = "select capa_img from livros where id_livro not in (?,?) order by rand()";
            String nome3Livro = "select titulo from livros where capa_img = ?";
            String descricao3Livro = "select categoria from livros where capa_img = ?";
            PreparedStatement stmtId = connection.prepareStatement(id3);
            PreparedStatement stmt = connection.prepareStatement(imagem3Livro);
            PreparedStatement stmt1 = connection.prepareStatement(nome3Livro);
            PreparedStatement stmt2 = connection.prepareStatement(descricao3Livro);
            stmt.setString(1, listaIds[0]);
            stmt.setString(2, listaIds[1]);

            ResultSet rs = stmt.executeQuery();


            if(rs.next()){
                image3Url[0] = rs.getString("capa_img");
                stmt1.setString(1, image3Url[0]);
                stmt2.setString(1, image3Url[0]);
                stmtId.setString(1, image3Url[0]);
                ResultSet rs1 = stmt1.executeQuery();


                if(rs1.next()){
                    nomeLivro3.setText(rs1.getString("titulo"));
                    ResultSet rs2 = stmt2.executeQuery();
                    if(rs2.next()){
                        descricaoLivro3.setText(rs2.getString("categoria"));
                        ResultSet rs3 = stmtId.executeQuery();
                        if(rs3.next()) {
                            listaIds[2] = rs3.getString("id_livro");
                        }
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
            String id4 = "select id_livro from livros where capa_img = ?";
            String imagem4Livro = "select capa_img from livros where id_livro not in (?,?,?) order by rand()";
            String nome4Livro = "select titulo from livros where capa_img = ?";
            String descricao4Livro = "select categoria from livros where capa_img = ?";
            PreparedStatement stmtId = connection.prepareStatement(id4);
            PreparedStatement stmt = connection.prepareStatement(imagem4Livro);
            PreparedStatement stmt1 = connection.prepareStatement(nome4Livro);
            PreparedStatement stmt2 = connection.prepareStatement(descricao4Livro);
            stmt.setString(1, listaIds[0]);
            stmt.setString(2, listaIds[1]);
            stmt.setString(3, listaIds[2]);
            ResultSet rs = stmt.executeQuery();


            if(rs.next()){
                image4Url[0] = rs.getString("capa_img");
                stmtId.setString(1, imageUrl[0]);
                stmt.setString(1, listaIds[2]);
                stmt1.setString(1, image4Url[0]);
                stmt2.setString(1, image4Url[0]);
                stmtId.setString(1, image4Url[0]);
                ResultSet rs1 = stmt1.executeQuery();

                if(rs1.next()){
                    nomeLivro4.setText(rs1.getString("titulo"));
                    ResultSet rs2 = stmt2.executeQuery();
                    if(rs2.next()){
                        descricaoLivro4.setText(rs2.getString("categoria"));
                        ResultSet rs3 = stmtId.executeQuery();
                        if(rs3.next()) {
                            listaIds[3] = rs3.getString("id_livro");
                        }
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
            final String[] listaIds = new String[4];
            public void onClick(View v) {
                try {
                    pesquisarInput.setText("");
                    String id1 = "select id_livro from livros where capa_img = ?";
                    String imagemLivro = "select capa_img from livros order by rand()";
                    String nomeLivro = "select titulo from livros where capa_img = ?";
                    String descricaoLivro = "select categoria from livros where capa_img = ?";

                    PreparedStatement stmtId = connection.prepareStatement(id1);
                    PreparedStatement stmt = connection.prepareStatement(imagemLivro);
                    PreparedStatement stmt1 = connection.prepareStatement(nomeLivro);
                    PreparedStatement stmt2 = connection.prepareStatement(descricaoLivro);
                    ResultSet rs = stmt.executeQuery();


                    if(rs.next()){
                        imageUrl[0] = rs.getString("capa_img");
                        stmt1.setString(1, imageUrl[0]);
                        stmt2.setString(1, imageUrl[0]);
                        stmtId.setString(1, imageUrl[0]);
                        ResultSet rs1 = stmt1.executeQuery();
                        if(rs1.next()){
                            nomeLivro1.setText(rs1.getString("titulo"));
                            ResultSet rs2 = stmt2.executeQuery();
                            if(rs2.next()){
                                descricaoLivro1.setText(rs2.getString("categoria"));
                                ResultSet rs3 = stmtId.executeQuery();
                                if(rs3.next()) {
                                    listaIds[0] = rs3.getString("id_livro");

                                }
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
                    String id2 = "select id_livro from livros where capa_img = ?";
                    String imagem2Livro = "select capa_img from livros where id_livro not in (?) order by rand()";
                    String nome2Livro = "select titulo from livros where capa_img = ?";
                    String descricao2Livro = "select categoria from livros where capa_img = ?";
                    PreparedStatement stmtId = connection.prepareStatement(id2);
                    PreparedStatement stmt = connection.prepareStatement(imagem2Livro);
                    PreparedStatement stmt1 = connection.prepareStatement(nome2Livro);
                    PreparedStatement stmt2 = connection.prepareStatement(descricao2Livro);
                    stmt.setString(1, listaIds[0]);
                    ResultSet rs = stmt.executeQuery();

                    if(rs.next()){
                        image2Url[0] = rs.getString("capa_img");
                        stmt1.setString(1, image2Url[0]);
                        stmt2.setString(1, image2Url[0]);
                        stmtId.setString(1, image2Url[0]);
                        ResultSet rs1 = stmt1.executeQuery();

                        if(rs1.next()){
                            nomeLivro2.setText(rs1.getString("titulo"));
                            ResultSet rs2 = stmt2.executeQuery();

                            if(rs2.next()){
                                descricaoLivro2.setText(rs2.getString("categoria"));
                                ResultSet rs3 = stmtId.executeQuery();

                                if(rs3.next()) {
                                    listaIds[1] = rs3.getString("id_livro");
                                }
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
                    String id3 = "select id_livro from livros where capa_img = ?";
                    String imagem3Livro = "select capa_img from livros where id_livro not in (?,?) order by rand()";
                    String nome3Livro = "select titulo from livros where capa_img = ?";
                    String descricao3Livro = "select categoria from livros where capa_img = ?";

                    PreparedStatement stmtId = connection.prepareStatement(id3);
                    PreparedStatement stmt = connection.prepareStatement(imagem3Livro);
                    PreparedStatement stmt1 = connection.prepareStatement(nome3Livro);
                    PreparedStatement stmt2 = connection.prepareStatement(descricao3Livro);

                    stmt.setString(1, listaIds[0]);
                    stmt.setString(2, listaIds[1]);
                    ResultSet rs = stmt.executeQuery();

                    if(rs.next()){
                        image3Url[0] = rs.getString("capa_img");
                        stmt1.setString(1, image3Url[0]);
                        stmt2.setString(1, image3Url[0]);
                        stmtId.setString(1, image3Url[0]);
                        ResultSet rs1 = stmt1.executeQuery();

                        if(rs1.next()){
                            nomeLivro3.setText(rs1.getString("titulo"));
                            ResultSet rs2 = stmt2.executeQuery();
                            if(rs2.next()){
                                descricaoLivro3.setText(rs2.getString("categoria"));
                                ResultSet rs3 = stmtId.executeQuery();
                                if(rs3.next()){
                                    listaIds[2] = rs3.getString("id_livro");
                                }

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
                    String imagem4Livro = "select capa_img from livros where id_livro not in (?,?,?) order by rand()";
                    String nome4Livro = "select titulo from livros where capa_img = ?";
                    String descricao4Livro = "select categoria from livros where capa_img = ?";
                    PreparedStatement stmt = connection.prepareStatement(imagem4Livro);
                    PreparedStatement stmt1 = connection.prepareStatement(nome4Livro);
                    PreparedStatement stmt2 = connection.prepareStatement(descricao4Livro);
                    stmt.setString(1, listaIds[0]);
                    stmt.setString(2, listaIds[1]);
                    stmt.setString(3, listaIds[2]);
                    ResultSet rs = stmt.executeQuery();


                    if(rs.next()){
                        image4Url[0] = rs.getString("capa_img");
                        stmt1.setString(1, image4Url[0]);
                        stmt2.setString(1, image4Url[0]);
                        ResultSet rs1 = stmt1.executeQuery();

                        if(rs1.next()){
                            nomeLivro4.setText(rs1.getString("titulo"));
                            ResultSet rs2 = stmt2.executeQuery();
                            if(rs2.next()){
                                descricaoLivro4.setText(rs2.getString("categoria"));
                            }
                        }
                        Glide.with(requireContext())
                                .load(image4Url[0])
                                .into(imgLivro4);
                    }


                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        });
*/
        Connection connection = ConexaoMySQL.conectar();

        String[] listaIds = new String[4];
        String[] imageUrls = new String[4];
        String[] titulos = new String[4];
        String[] categorias = new String[4];

        try {
            String query = "SELECT id_livro, titulo, categoria, capa_img FROM livros ORDER BY RAND() LIMIT 4";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int i = 0;
            while (rs.next() && i < 4) {
                listaIds[i] = rs.getString("id_livro");
                titulos[i] = rs.getString("titulo");
                categorias[i] = rs.getString("categoria");
                imageUrls[i] = rs.getString("capa_img");
                i++;
            }

            // Exibição nos elementos da interface
            nomeLivro1.setText(titulos[0]);
            descricaoLivro1.setText(categorias[0]);
            Glide.with(requireContext()).load(imageUrls[0]).into(imgLivro1);

            nomeLivro2.setText(titulos[1]);
            descricaoLivro2.setText(categorias[1]);
            Glide.with(requireContext()).load(imageUrls[1]).into(imgLivro2);

            nomeLivro3.setText(titulos[2]);
            descricaoLivro3.setText(categorias[2]);
            Glide.with(requireContext()).load(imageUrls[2]).into(imgLivro3);

            nomeLivro4.setText(titulos[3]);
            descricaoLivro4.setText(categorias[3]);
            Glide.with(requireContext()).load(imageUrls[3]).into(imgLivro4);

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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



        btPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!pesquisarInput.getText().toString().isEmpty()){
                        String pesquisa = "select * from livros where titulo like ?";
                        String buscaIds = "select id_livro from livros where capa_img = ?";
                        PreparedStatement stmt = connection.prepareStatement(pesquisa);
                        PreparedStatement stmt1 = connection.prepareStatement(buscaIds);

                        stmt.setString(1, "%" + pesquisarInput.getText().toString() + "%");

                        ResultSet rs = stmt.executeQuery();

                        if(rs.next()){

                            String imagemUrl = rs.getString("capa_img");
                            String nome = rs.getString("titula");
                            String descricao = rs.getString("categoria");

                            stmt1.setString(1, imagemUrl);
                            ResultSet rs1 = stmt1.executeQuery();
                            if(rs1.next()){
                                listaIds[0] = rs1.getString("id_livro");
                            }

                            nomeLivro1.setText(nome);
                            descricaoLivro1.setText(descricao);
                            Glide.with(requireContext())
                                    .load(imagemUrl)
                                    .into(imgLivro1);
                        }
                    }



                }catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String pesquisa = "select * from livros where id_livro not in (?) and titulo like ? ";
                    String buscaIds = "select id_livro from livros where capa_img = ?";
                    PreparedStatement stmt = connection.prepareStatement(pesquisa);
                    PreparedStatement stmt1 = connection.prepareStatement(buscaIds);

                    stmt.setString(1, listaIds[0]);
                    stmt.setString(2, "%" + pesquisarInput.getText().toString() + "%");

                    ResultSet rs = stmt.executeQuery();

                    if(rs.next()){

                        String imagemUrl = rs.getString("capa_img");
                        String nome = rs.getString("titulo");
                        String descricao = rs.getString("categoria");

                        stmt1.setString(1, imagemUrl);
                        ResultSet rs1 = stmt1.executeQuery();
                        if(rs1.next()){
                            listaIds[1] = rs1.getString("id_livro");
                        }

                        nomeLivro2.setText(nome);
                        descricaoLivro2.setText(descricao);                       /* arthurestak@gmail.com
                                                                                     Arthur2007-          */
                        Glide.with(requireContext())
                                .load(imagemUrl)
                                .into(imgLivro2);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String pesquisa = "select * from livros where id_livro not in (?,?) and titulo like ?";
                    String buscaIds = "select id_livro from livros where capa_img = ?";
                    PreparedStatement stmt = connection.prepareStatement(pesquisa);
                    PreparedStatement stmt1 = connection.prepareStatement(buscaIds);

                    stmt.setString(1, listaIds[0]);
                    stmt.setString(2, listaIds[1]);
                    stmt.setString(3, "%" + pesquisarInput.getText().toString() + "%");

                    ResultSet rs = stmt.executeQuery();

                    if(rs.next()){

                        String imagemUrl = rs.getString("capa_img");
                        String nome = rs.getString("titulo");
                        String descricao = rs.getString("categoria");

                        stmt1.setString(1, imagemUrl);
                        ResultSet rs1 = stmt1.executeQuery();
                        if(rs1.next()){
                            listaIds[2] = rs1.getString("id_livro");
                        }

                        nomeLivro3.setText(nome);
                        descricaoLivro3.setText(descricao);
                        Glide.with(requireContext())
                                .load(imagemUrl)
                                .into(imgLivro3);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String pesquisa = "select * from livros where id_livro not in (?,?,?) and titulo like ?";
                    String buscaIds = "select id_livro from livros where capa_img = ?";
                    PreparedStatement stmt = connection.prepareStatement(pesquisa);
                    PreparedStatement stmt1 = connection.prepareStatement(buscaIds);

                    stmt.setString(1, listaIds[0]);
                    stmt.setString(2, listaIds[1]);
                    stmt.setString(3, listaIds[2]);
                    stmt.setString(4, "%" + pesquisarInput.getText().toString() + "%");

                    ResultSet rs = stmt.executeQuery();

                    if(rs.next()){

                        String imagemUrl = rs.getString("nome_arquivos");
                        String nome = rs.getString("nome_livro");
                        String descricao = rs.getString("categoria_livro");

                        stmt1.setString(1, imagemUrl);
                        ResultSet rs1 = stmt1.executeQuery();
                        if(rs1.next()){
                            listaIds[3] = rs1.getString("id_livros");
                        }

                        nomeLivro4.setText(nome);
                        descricaoLivro4.setText(descricao);
                        Glide.with(requireContext())
                                .load(imagemUrl)
                                .into(imgLivro4);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
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