package com.example.projetolm;

import android.app.Application;

public class ProjetoLM extends Application {

    private String nomePessoa;
    private String idPessoaJava;

    public String getNomePessoa(){
        return nomePessoa;
    }

    public String getIdPessoaJava(){
        return idPessoaJava;
    }

    public void setIdPessoaJava(String idPessoaJava){
        this.idPessoaJava = idPessoaJava;
    }

    public void setNomePessoa(String nomePessoa){
        this.nomePessoa = nomePessoa;
    }

}
