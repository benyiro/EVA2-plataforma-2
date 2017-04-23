package starkiller.exa_platii_prac_2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Usuario HP on 06/04/2017.
 */
public class Usuarios extends AppCompatActivity {
    SQLiteDatabase sqldbConnect;
    ListView lsVwListaUsuarios;
    Cursor c1;
    DatabaseAdapter da;
    static final int RESULT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarios);
        sqldbConnect = openOrCreateDatabase("angie", MODE_PRIVATE, null);
        lsVwListaUsuarios = (ListView) findViewById(R.id.lsVwListaUsuarios);
        c1 = sqldbConnect.rawQuery("SELECT * FROM usuarios", null);
        da = new DatabaseAdapter(this, c1);
        lsVwListaUsuarios.setAdapter(da);
        //Responder al click
        lsVwListaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), UsuarioNuevo.class);
                i.putExtra("id", id);
                startActivityForResult(i, RESULT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT) {
            if (resultCode == RESULT_OK) {
                c1 = sqldbConnect.rawQuery("SELECT * FROM usuarios", null);
                da = new DatabaseAdapter(this, c1);
                lsVwListaUsuarios.setAdapter(da);
                Toast.makeText(getApplicationContext(), data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

