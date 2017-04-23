package starkiller.exa_platii_prac_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Principal extends AppCompatActivity {
    SQLiteDatabase sqldbConnect;
    Cursor c1;
    static final int RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //crear la base de datos
        sqldbConnect = openOrCreateDatabase("angie", MODE_PRIVATE, null);
        try {
            sqldbConnect.execSQL("CREATE TABLE IF NOT EXISTS usuarios(" +
                    "_id        integer PRIMARY KEY autoincrement," +
                    "nombre     text," +
                    "apellido   text," +
                    "usuario    text," +
                    "password   text);");
            sqldbConnect.execSQL("CREATE TABLE IF NOT EXISTS archivos(" +
                    "_id        integer PRIMARY KEY autoincrement," +
                    "usuario_id integer," +
                    "ruta       text);");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }
   //
     public void usuarios(View v) {
        startActivity(new Intent(getApplicationContext(), UsuarioNuevo.class));
    }
    //
    public void salir(View v) {
        System.exit(0);
    }

    public void login(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View prompt = li.inflate(R.layout.message, null);
        builder.setTitle("Iniciar Sesión");
        builder.setView(prompt);

        final EditText usuario = (EditText) prompt.findViewById(R.id.edtTxtUsuario);
        final EditText password = (EditText) prompt.findViewById(R.id.edtTxtPassword);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String eUsuario = usuario.getText().toString();
                String ePassword = password.getText().toString();
                int id = checarUsuario(eUsuario, ePassword);
                if (id != -1) {
                    Intent i = new Intent(getApplicationContext(), ArchivoNuevo.class);
                    i.putExtra("id", id);
                    startActivityForResult(i, RESULT);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public int checarUsuario(String usuario, String password) {
        c1 = sqldbConnect.rawQuery("SELECT * FROM usuarios WHERE usuario = ? and password = ?", new String[]{usuario, password});
        if (c1.getCount() > 0) {
            c1.moveToFirst();
            int id = c1.getInt(c1.getColumnIndex("_id"));
            return id;
        }
        return -1;
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
