package com.example.projetolm;
//import com.mysql.cj.jdbc.ConnectionImpl;
import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://biblioteca-db.cf6wmsg6kibo.sa-east-1.rds.amazonaws.com:3306/Biblioteca";
    private static final String USUARIO = "admin"; //super usuario do banco de dados
    private static final String SENHA = "bIbR1073ca";

    public static Connection conectar(){
        try{
            try{
                Class.forName("com.mysql.jdbc.Driver");
                StrictMode.ThreadPolicy policy = new
                        StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        }catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }

    public static void fecharConexao(Connection conexao) {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}

