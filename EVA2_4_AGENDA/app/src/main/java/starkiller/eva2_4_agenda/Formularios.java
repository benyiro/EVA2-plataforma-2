package starkiller.eva2_4_agenda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Usuario HP on 15/03/2017.
 */
public class Formularios extends AppCompatActivity{
    EditText edtNom, edtTel;
    Button btnActua, btnElim;
    Intent i2;
    SQLiteDatabase sqldbConnect;
    ContentValues cv = new ContentValues();
    String nombre, telefono;
    long id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);
        edtNom = (EditText) findViewById(R.id.edtNom);
        edtTel = (EditText) findViewById(R.id.edtTel);
        btnActua = (Button) findViewById(R.id.btnActua);
        btnElim = (Button) findViewById(R.id.btnElim);
        sqldbConnect = openOrCreateDatabase("miBaseDatos", MODE_PRIVATE, null);
        i2 = getIntent();
        id = i2.getLongExtra("id", 0);
        Cursor c1 = sqldbConnect.rawQuery("SELECT * FROM tblContactos WHERE _id = ?", new String[]{id + ""});
        c1.moveToFirst();
        edtNom.setText(c1.getString(c1.getColumnIndex("nombre")));
        edtTel.setText(c1.getString(c1.getColumnIndex("telefono")));
        btnActua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = edtNom.getText().toString();
                telefono = edtTel.getText().toString();
                cv.put("nombre", nombre);
                cv.put("telefono", telefono);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sqldbConnect.update("tblContactos", cv, "_id = ?", new String[]{"" + id});
                        i2.putExtra("result", "Datos Actualizados");
                        setResult(RESULT_OK, i2);
                        finish();
                    }
                });
                t.start();
            }
        });
        btnElim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sqldbConnect.delete("tblContactos", "_id = ?", new String[]{"" + id});
                        i2.putExtra("result", "Datos Eliminados");
                        setResult(RESULT_OK, i2);
                        finish();
                    }
                });
                t.start();
            }
        });
    }
}
