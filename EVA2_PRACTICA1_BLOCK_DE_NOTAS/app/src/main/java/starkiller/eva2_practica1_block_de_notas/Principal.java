package starkiller.eva2_practica1_block_de_notas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Principal extends AppCompatActivity implements View.OnClickListener {
    EditText edtTxtTexto;
    Button btnLimpiar, btnGuardar, btnAbrir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //vincular los widgets
        edtTxtTexto = (EditText) findViewById(R.id.edtTxtTexto);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnAbrir = (Button) findViewById(R.id.btnAbrir);
        //eventos onclick
        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream isAbrir = null;
                try {
                    isAbrir = openFileInput("prueba.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (isAbrir != null) {
                    InputStreamReader isrBytes = new InputStreamReader(isAbrir);
                    BufferedReader brLeer = new BufferedReader(isrBytes);
                    String sCade;
                    try {
                        while ((sCade = brLeer.readLine()) != null) {
                            edtTxtTexto.append(sCade);
                            edtTxtTexto.append("\n");
                        }
                        brLeer.close();
                        isAbrir.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void limpiar(View v) {
        edtTxtTexto.setText("");
    }

    @Override
    public void onClick(View view) {
        OutputStream osAbrir = null;
        try {
            osAbrir = openFileOutput("prueba.txt", 0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (osAbrir != null) {
            //OutputStreamWriter
            OutputStreamWriter oswGuardarBytes = new OutputStreamWriter(osAbrir);
            //BufferedWriter
            BufferedWriter bwEscribe = new BufferedWriter(oswGuardarBytes);
            try {
                bwEscribe.write(edtTxtTexto.getText().toString());
                bwEscribe.flush();
                bwEscribe.close();
                osAbrir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

