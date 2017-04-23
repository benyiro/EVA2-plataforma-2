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
public class Archivos extends AppCompatActivity {
    Intent i;
    SQLiteDatabase sqldbConnect;
    ListView lsVwListaArchivos;
    Cursor c1;
    DatabaseAdapter da;
    static final int RESULT = 1001;
    int usuario_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archivos);
        i = getIntent();
        usuario_id = i.getIntExtra("usuario_id", -1);
        sqldbConnect = openOrCreateDatabase("angie", MODE_PRIVATE, null);
        lsVwListaArchivos = (ListView) findViewById(R.id.lsVwListaArchivos);
        c1 = sqldbConnect.rawQuery("SELECT * FROM archivos WHERE usuario_id = ?", new String[]{String.valueOf(usuario_id)});
        da = new DatabaseAdapter(this, c1);
        lsVwListaArchivos.setAdapter(da);
        //Responder al click
        lsVwListaArchivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ArchivoNuevo.class);
                i.putExtra("id", usuario_id);
                i.putExtra("archivo_id", id);
                startActivityForResult(i, RESULT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT) {
            if (resultCode == RESULT_OK) {
                c1 = sqldbConnect.rawQuery("SELECT * FROM archivos WHERE usuario_id = ?", new String[]{String.valueOf(usuario_id)});
                da = new DatabaseAdapter(this, c1);
                lsVwListaArchivos.setAdapter(da);
                Toast.makeText(getApplicationContext(), data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
