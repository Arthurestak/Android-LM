package com.example.projetolm.ui.formulario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetolm.ConexaoMySQL;
import com.example.projetolm.Login;
import com.example.projetolm.MainActivity;
import com.example.projetolm.ProjetoLM;
import com.example.projetolm.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FormularioFragment extends Fragment {

    private static final int PICK_FILE_REQUEST_CODE_ARQUIVO = 1;
    private static final int PICK_FILE_REQUEST_CODE_CAPA = 2;
    ImageView btCapaLivro, btArquivoLivro, btEnviarLivro;
    EditText nomeLivroInput, generoLivroInput, autorLivroInput, capaLivroInput, arquivoLivroInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();

            // Obter o nome do arquivo
            String fileName = getFileName(requireContext(), uri);

            // Copiar o arquivo para o armazenamento interno do app
            File savedFile = saveFileToInternalStorage(uri, fileName);

            switch(requestCode){
                case PICK_FILE_REQUEST_CODE_ARQUIVO:
                    arquivoLivroInput.setText(fileName);
                    break;
                case PICK_FILE_REQUEST_CODE_CAPA:
                    capaLivroInput.setText(fileName);
                    break;
            }
        }
    }
    private String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) cursor.close();
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    private File saveFileToInternalStorage(Uri uri, String fileName) {
        File file = new File(requireContext().getFilesDir(), fileName);
        try (InputStream in = requireContext().getContentResolver().openInputStream(uri);
             OutputStream out = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflando o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_formulario, container, false);

        // Agora sim é seguro buscar os elementos dentro da View inflada
        btCapaLivro = view.findViewById(R.id.btCapaLivro);
        btArquivoLivro = view.findViewById(R.id.btArquivoLivro);
        btEnviarLivro = view.findViewById(R.id.btEnviarLivro);
        nomeLivroInput = view.findViewById(R.id.nomeLivroInput);
        generoLivroInput = view.findViewById(R.id.generoLivroInput);
        capaLivroInput = view.findViewById(R.id.capaLivroInput);
        arquivoLivroInput = view.findViewById(R.id.arquivoLivroInput);
        autorLivroInput = view.findViewById(R.id.autorLivroInput);

        // Listener do botão para escolher o arquivo
        btArquivoLivro.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // qualquer tipo de arquivo
            startActivityForResult(Intent.createChooser(intent, "Selecione um arquivo"), PICK_FILE_REQUEST_CODE_ARQUIVO);
        });
        btCapaLivro.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // qualquer tipo de arquivo
            startActivityForResult(Intent.createChooser(intent, "Selecione um arquivo"), PICK_FILE_REQUEST_CODE_CAPA);
        });

        btEnviarLivro.setOnClickListener(v -> {
            Connection connection = ConexaoMySQL.conectar();

            if (connection == null) {
                Toast.makeText(getContext(), "Erro na conexão", Toast.LENGTH_SHORT).show();
                return;
            }
            ProjetoLM app = (ProjetoLM) getActivity().getApplicationContext();
            String idPessoaJava = app.getIdPessoaJava();
            try {


                String idAutorSql = "select id_assinante from assinante a inner join pessoas p on a.id_pessoa = p.id_pessoa where a.id_pessoa = ?";
                PreparedStatement stmtIdAutor = connection.prepareStatement(idAutorSql);
                stmtIdAutor.setString(1, idPessoaJava);
                ResultSet rsIdAutor = stmtIdAutor.executeQuery();


                int idAssinante = -1;
                if (rsIdAutor.next()) {
                    idAssinante = rsIdAutor.getInt("id_assinante");
                } else {
                    Toast.makeText(getContext(), "Autor não encontrado no banco de dados.", Toast.LENGTH_LONG).show();
                    return; // Não tenta continuar se não encontrou o autor!
                }
                String sql = "INSERT INTO livros_enviados (situacao, titulo, categoria, autor, capa_img, livro_file,id_assinante) " +
                        "VALUES ('Pendente', ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, nomeLivroInput.getText().toString());
                stmt.setString(2, generoLivroInput.getText().toString());
                stmt.setString(3, autorLivroInput.getText().toString());

                File capaFile = new File(requireContext().getFilesDir(), capaLivroInput.getText().toString());
                File arquivoFile = new File(requireContext().getFilesDir(), arquivoLivroInput.getText().toString());

                stmt.setBinaryStream(4, new FileInputStream(capaFile), (int) capaFile.length()); // arquivo
                stmt.setBinaryStream(5, new FileInputStream(arquivoFile), (int) arquivoFile.length()); // arquivo

                stmt.setInt(6, idAssinante);


                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    Toast.makeText(getContext(), "Livro enviado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Erro ao enviar livro.", Toast.LENGTH_SHORT).show();
                }

                stmtIdAutor.close();
                stmt.close();
                connection.close();

                nomeLivroInput.setText("");
                generoLivroInput.setText("");
                capaLivroInput.setText("");
                arquivoLivroInput.setText("");
                autorLivroInput.setText("");

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view; // <<<<<<<< RETORNE A VIEW INFLADA
    }
}