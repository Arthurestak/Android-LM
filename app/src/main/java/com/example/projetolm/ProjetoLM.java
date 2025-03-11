package com.example.projetolm;

import android.app.Application;

public class ProjetoLM extends Application {

    private String nomePessoa;
    private int idPessoa;

    public String getNomePessoa(){
        return nomePessoa;
    }

    public int getIdPessoa(){
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa){
        this.idPessoa = idPessoa;
    }

    public void setNomePessoa(String nomePessoa){
        this.nomePessoa = nomePessoa;
    }
}
