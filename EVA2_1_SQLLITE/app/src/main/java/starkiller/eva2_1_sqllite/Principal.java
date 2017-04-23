package starkiller.eva2_1_sqllite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Principal extends AppCompatActivity {
     SQLiteDatabase sqlconnect;
    TextView txtDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //establecer conexion
        sqlconnect = openOrCreateDatabase("mibasededatos",MODE_PRIVATE,null);
        try{
            sqlconnect.execSQL("create table tblDatos(regID integer PRIMARY KEY autoincrement, nombre text, apellido text);");
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        sqlconnect.execSQL("insert into tblDatos(nombre, apellido) values ('Hector','Rodriguez')");
        sqlconnect.execSQL("insert into tblDatos(nombre, apellido) values ('jhon','snow')");
        sqlconnect.execSQL("insert into tblDatos(nombre, apellido) values ('jaime',':Lanninster')");

        txtDatos= (TextView)findViewById(R.id.txtDatos);

        Cursor cl = sqlconnect.rawQuery("select * from tblDatos", null);
        cl.moveToFirst();
        while (!cl.isAfterLast()){
            txtDatos.append(cl.getInt(cl.getColumnIndex("regID")) + " - ");
            txtDatos.append(cl.getString(cl.getColumnIndex("nombre")) + " - ");
            txtDatos.append(cl.getString(cl.getColumnIndex("apellido")) + " - ");
            cl.moveToNext();
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
