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
    private static final int PICK_FILE_REQUEST_CODE_DESCRICAO = 3;
    ImageView btCapaLivro, btArquivoLivro, btEnviarLivro, btDescricaoLivro;
    EditText nomeLivroInput, generoLivroInput, autorLivroInput, capaLivroInput, arquivoLivroInput, descricaoLivroInput;

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
                case PICK_FILE_REQUEST_CODE_DESCRICAO:
                    descricaoLivroInput.setText(fileName);
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
        btDescricaoLivro = view.findViewById(R.id.btDescricaoLivro);
        btArquivoLivro = view.findViewById(R.id.btArquivoLivro);
        btEnviarLivro = view.findViewById(R.id.btEnviarLivro);
        nomeLivroInput = view.findViewById(R.id.nomeLivroInput);
        generoLivroInput = view.findViewById(R.id.generoLivroInput);
        capaLivroInput = view.findViewById(R.id.capaLivroInput);
        arquivoLivroInput = view.findViewById(R.id.arquivoLivroInput);
        autorLivroInput = view.findViewById(R.id.autorLivroInput);
        descricaoLivroInput = view.findViewById(R.id.descricaoLivroInput);

        // Listener do botão para escolher o arquivo
        btArquivoLivro.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf"); // apenas arquivos PDF
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Selecione um arquivo PDF"), PICK_FILE_REQUEST_CODE_ARQUIVO);
        });
        btCapaLivro.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*"); // apenas imagens (JPEG, PNG, WEBP, etc)
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_FILE_REQUEST_CODE_CAPA);
        });
        btDescricaoLivro.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("text/plain"); // qualquer tipo de arquivo
            startActivityForResult(Intent.createChooser(intent, "Selecione um arquivo"), PICK_FILE_REQUEST_CODE_DESCRICAO);
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
                // Verifica se os campos estão preenchidos
                String titulo = nomeLivroInput.getText().toString().trim();
                String categoria = generoLivroInput.getText().toString().trim();
                String autor = autorLivroInput.getText().toString().trim();
                String nomeCapa = capaLivroInput.getText().toString().trim();
                String nomeArquivo = arquivoLivroInput.getText().toString().trim();
                String nomeDescricao = descricaoLivroInput.getText().toString().trim();

                if (titulo.isEmpty() || categoria.isEmpty() || autor.isEmpty()
                        || nomeCapa.isEmpty() || nomeArquivo.isEmpty() || nomeDescricao.isEmpty()) {
                    Toast.makeText(getContext(), "Preencha todos os campos e selecione os arquivos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica se os arquivos existem
                File capaFile = new File(requireContext().getFilesDir(), nomeCapa);
                File arquivoFile = new File(requireContext().getFilesDir(), nomeArquivo);
                File descricaoFile = new File(requireContext().getFilesDir(), nomeDescricao);

                if (!capaFile.exists() || !arquivoFile.exists() || !descricaoFile.exists()) {
                    Toast.makeText(getContext(), "Um ou mais arquivos não foram encontrados no armazenamento interno.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Busca o ID do autor
                String idAutorSql = "SELECT id_autor FROM autor WHERE id_pessoa = ?";
                PreparedStatement stmtIdAutor = connection.prepareStatement(idAutorSql);
                stmtIdAutor.setString(1, idPessoaJava);
                ResultSet rsIdAutor = stmtIdAutor.executeQuery();

                int idAutor = -1;
                if (rsIdAutor.next()) {
                    idAutor = rsIdAutor.getInt("id_autor");
                } else {
                    Toast.makeText(getContext(), "Autor não encontrado no banco de dados.", Toast.LENGTH_LONG).show();
                    stmtIdAutor.close();
                    return;
                }

                // Prepara e executa o INSERT
                String sql = "INSERT INTO livros_enviados (situacao, titulo, categoria, autor, capa_img, livro_file, descricao, id_autor) " +
                        "VALUES ('Pendente', ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, titulo);
                stmt.setString(2, categoria);
                stmt.setString(3, autor);

                stmt.setBinaryStream(4, new FileInputStream(capaFile), (int) capaFile.length());
                stmt.setBinaryStream(5, new FileInputStream(arquivoFile), (int) arquivoFile.length());
                stmt.setBinaryStream(6, new FileInputStream(descricaoFile), (int) descricaoFile.length());

                stmt.setInt(7, idAutor);

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    Toast.makeText(getContext(), "Livro enviado com sucesso!", Toast.LENGTH_SHORT).show();
                    // Limpa os campos
                    nomeLivroInput.setText("");
                    generoLivroInput.setText("");
                    capaLivroInput.setText("");
                    arquivoLivroInput.setText("");
                    autorLivroInput.setText("");
                    descricaoLivroInput.setText("");
                } else {
                    Toast.makeText(getContext(), "Erro ao enviar livro.", Toast.LENGTH_SHORT).show();
                }

                // Fecha conexões
                stmtIdAutor.close();
                stmt.close();
                connection.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view; // <<<<<<<< RETORNE A VIEW INFLADA
    }
}