package com.ruansilvarosa.mynotes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText myText;
    private ImageView btnSalvar;
    private static final String NOME_ARQUIVO = "myNotes.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = (EditText) findViewById(R.id.notesEditorID);
        btnSalvar = (ImageView) findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(this);
        verificaArquivo();
    }

    @Override
    public void onClick(View view){
        String textoDigitado = myText.getText().toString();
        gravarNotas(textoDigitado);
        Toast.makeText(getApplicationContext(),"Anotação Salva com Sucesso!",Toast.LENGTH_SHORT).show();
    }
    private void gravarNotas(String texto){
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO,Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.close();
        }catch(IOException e){
            Log.v("MainActivity", e.toString());
        }
    }

    private String lerArquivo(){
        String resultado = "";
        try{
            //Ler o arquivo
            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            //Verifica se arquivo não é nulo.
            if(arquivo != null){
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //Gerar Buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //Recupera texto do arquivo.
                String linhaArquivo;
                while ((linhaArquivo = bufferedReader.readLine()) != null){
                    resultado += linhaArquivo;
                }

                arquivo.close();
            }



        }catch(IOException e){
            Log.v("Falha ao ler arquivo.",e.toString());
        }
        return resultado;
    }

    private void verificaArquivo(){
        if(lerArquivo() != null){
            myText.setText(lerArquivo());
        }
    }
}
