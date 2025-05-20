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

import com.example.projetolm.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FormularioFragment extends Fragment {

    private static final int PICK_FILE_REQUEST_CODE = 1;
    ImageView btCapaLivro, btArquivoLivro, btEnviarLivro;
    EditText nomeLivroInput, generoLivroInput, autorLivroInput, capaLivroInput, arquivoLivroInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();

            // Obter o nome do arquivo
            String fileName = getFileName(requireContext(), uri);

            // Copiar o arquivo para o armazenamento interno do app
            File savedFile = saveFileToInternalStorage(uri, fileName);

            // Exibir o nome no campo
            EditText arquivoLivroinput = getView().findViewById(R.id.arquivoLivroInput);
            arquivoLivroinput.setText(fileName);
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

        // Listener do botão para escolher o arquivo
        btArquivoLivro.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // qualquer tipo de arquivo
            startActivityForResult(Intent.createChooser(intent, "Selecione um arquivo"), PICK_FILE_REQUEST_CODE);
        });
        

        return view; // <<<<<<<< RETORNE A VIEW INFLADA
    }
}