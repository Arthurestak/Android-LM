package com.example.projetolm.ui.livros;

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

        String imageUrl;

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



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}