package starkiller.exa_platii_prac_2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Usuario HP on 06/04/2017.
 */
public class UsuarioNuevo extends AppCompatActivity {

    SQLiteDatabase sqldbConnect;
    ContentValues cv = new ContentValues();
    Cursor c1;
    Intent i;
    long id = 0;
    EditText edtTxtNombre, edtTxtApellido, edtTxtUsuario, edtTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_nuevo);
        edtTxtNombre = (EditText) findViewById(R.id.edtTxtNombre);
        edtTxtApellido = (EditText) findViewById(R.id.edtTxtApellido);
        edtTxtUsuario = (EditText) findViewById(R.id.edtTxtUsuario);
        edtTxtPassword = (EditText) findViewById(R.id.edtTxtPassword);
        sqldbConnect = openOrCreateDatabase("angie", MODE_PRIVATE, null);
        i = getIntent();
        id = i.getLongExtra("id", -1);
        if (id != -1) {
            c1 = sqldbConnect.rawQuery("SELECT * FROM usuarios WHERE _id = ?", new String[]{String.valueOf(id)});
            c1.moveToFirst();
            edtTxtNombre.setText(c1.getString(c1.getColumnIndex("nombre")));
            edtTxtApellido.setText(c1.getString(c1.getColumnIndex("apellido")));
            edtTxtUsuario.setText(c1.getString(c1.getColumnIndex("usuario")));
            edtTxtPassword.setText(c1.getString(c1.getColumnIndex("password")));
        }
    }

    public void actualizar() {
        String nombre = edtTxtNombre.getText().toString();
        String apellido = edtTxtApellido.getText().toString();
        String usuario = edtTxtUsuario.getText().toString();
        String password = edtTxtPassword.getText().toString();
        cv.put("nombre", nombre);
        cv.put("apellido", apellido);
        cv.put("usuario", usuario);
        cv.put("password", password);
        sqldbConnect.update("usuarios", cv, "_id = ?", new String[]{"" + id});
        i.putExtra("result", "Datos Actualizados");
        setResult(RESULT_OK, i);
        finish();
    }

    public void insertar() {
        String nombre = edtTxtNombre.getText().toString();
        String apellido = edtTxtApellido.getText().toString();
        String usuario = edtTxtUsuario.getText().toString();
        String password = edtTxtPassword.getText().toString();
        cv.put("nombre", nombre);
        cv.put("apellido", apellido);
        cv.put("usuario", usuario);
        cv.put("password", password);
        sqldbConnect.insert("usuarios", null, cv);
        edtTxtNombre.setText("");
        edtTxtApellido.setText("");
        edtTxtUsuario.setText("");
        edtTxtPassword.setText("");
        Toast.makeText(getApplicationContext(), "Usuario insertado", Toast.LENGTH_SHORT).show();
    }

    public void nuevo(View v) {
        id = -1;
        edtTxtNombre.setText("");
        edtTxtApellido.setText("");
        edtTxtUsuario.setText("");
        edtTxtPassword.setText("");
    }

    public void guardar(View v) {
        if (id != -1)
            actualizar();
        else
            insertar();
    }

    public void abrir(View v) {
        i = new Intent(getApplicationContext(), Usuarios.class);
        startActivity(i);
    }

    public void eliminar(View v) {
        if (id != -1) {
            sqldbConnect.delete("usuarios", "_id = ?", new String[]{"" + id});
            i.putExtra("result", "Datos Eliminados");
            setResult(RESULT_OK, i);
            finish();
        } else
            Toast.makeText(getApplicationContext(), "No hay usuario que eliminar", Toast.LENGTH_SHORT).show();

    }
}

