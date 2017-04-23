package starkiller.exa_platii_prac_2;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Usuario HP on 06/04/2017.
 */
public class ArchivoNuevo extends AppCompatActivity {
    SQLiteDatabase sqldbConnect;
    ContentValues cv = new ContentValues();
    Cursor c1;
    String sRutaSDCard = null;
    boolean permisos = false;
    EditText edtTxtTexto;
    Intent i;
    int usuario_id;
    long archivo_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archivo_nuevo);
        edtTxtTexto = (EditText) findViewById(R.id.edtTxtTexto);
        sRutaSDCard = Environment.getExternalStorageDirectory().getAbsolutePath();
        sqldbConnect = openOrCreateDatabase("angie", MODE_PRIVATE, null);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//Aqui nos negaron los permisos
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            permisos = true;
        }
        i = getIntent();
        usuario_id = i.getIntExtra("id", -1);
        archivo_id = i.getLongExtra("archivo_id", -1);
        if (archivo_id != -1 && permisos) {
            c1 = sqldbConnect.rawQuery("SELECT * FROM archivos WHERE _id = ?", new String[]{String.valueOf(archivo_id)});
            c1.moveToFirst();
            try {
                //        InputStream
                FileInputStream fisAbrir = new FileInputStream(c1.getString(c1.getColumnIndex("ruta")));
                //        InputStreamReader
                InputStreamReader isrLeer = new InputStreamReader(fisAbrir);
                //        BufferedReader
                BufferedReader brLeerTexto = new BufferedReader(isrLeer);
                String sCade = null;
                while ((sCade = brLeerTexto.readLine()) != null) {
                    edtTxtTexto.append("\n");
                    edtTxtTexto.append(sCade);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            permisos = true;
        }
    }

    public void nuevo(View v) {
        edtTxtTexto.setText("");
        archivo_id = -1;
    }

    public void guardar(View v) {
        if (permisos) {
            FileOutputStream osAbrir = null;
            String ruta = null;
            if (archivo_id != -1) {
                c1 = sqldbConnect.rawQuery("SELECT * FROM archivos WHERE _id = ?", new String[]{"" + archivo_id});
                c1.moveToNext();
                ruta = c1.getString(c1.getColumnIndex("ruta"));
                try {
                    osAbrir = new FileOutputStream(ruta);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                c1 = sqldbConnect.rawQuery("SELECT * FROM archivos", null);
                File osCrear = new File(sRutaSDCard, c1.getCount() + ".txt");
                ruta = osCrear.getAbsolutePath();
                try {
                    osAbrir = new FileOutputStream(osCrear);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            cv.put("usuario_id", usuario_id);
            cv.put("ruta", ruta);
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
                    if (archivo_id == -1) {
                        sqldbConnect.insert("archivos", null, cv);
                        Toast.makeText(getApplicationContext(), "Archivo Guardado", Toast.LENGTH_SHORT).show();
                        edtTxtTexto.setText("");
                    } else {
                        i.putExtra("result", "Archivo Actualizado");
                        setResult(RESULT_OK, i);
                        finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No tienes permisos de escritura", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void abrir(View v) {
        i = new Intent(getApplicationContext(), Archivos.class);
        i.putExtra("usuario_id", usuario_id);
        startActivity(i);
    }

}