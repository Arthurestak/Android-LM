package com.example.projetolm.ui.assinatura;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.projetolm.Login;
import com.example.projetolm.R;

public class AssinaturaFragment extends Fragment {

    public AssinaturaFragment() {
        // Construtor vazio obrigatório para o Fragment
    }

    public static AssinaturaFragment newInstance(String param1, String param2) {
        AssinaturaFragment fragment = new AssinaturaFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assinatura, container, false);


        ImageView btAssinar = view.findViewById(R.id.btAssinar);

        // Adicionar um OnClickListener para navegar para a tela de Login
        btAssinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        return view;
    }
}