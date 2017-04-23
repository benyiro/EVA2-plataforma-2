package starkiller.eva2_2_sqlite_2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    EditText edtNom, edtApe, edtEdad;
    SQLiteDatabase sqlMIDB;
    long Lreg;
    final int SECUNDARIA = 1000;
    Intent inSecun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        edtNom = (EditText)findViewById(R.id.edtNom);
        edtApe = (EditText)findViewById(R.id.edtApe);
        edtEdad = (EditText)findViewById(R.id.edtEdad);
        //creamos la base de datos(si no existe) o la abrimos si existe
        sqlMIDB = openOrCreateDatabase("misdatos",MODE_PRIVATE,null);
        try{
            sqlMIDB.execSQL("create table tblDatos(miID integer PRIMARY KEY autoincrement, " +
                    "nombre text, "+
                    "apellido text, "+
                    "edad integer);");
        }catch (SQLiteException e){
            e.printStackTrace();
        }

    }
    //insertar un registro en la bd
    public void onClickIns(View v){
        String sNom = edtNom.getText().toString();
        String sApe = edtApe.getText().toString();
        int iEdad = Integer.parseInt(edtEdad.getText().toString());
        ContentValues cvVal = new ContentValues();
        //clave--->columna, valor (tipo primitivo)
        cvVal.put("nombre",sNom);
        cvVal.put("apellido",sApe);
        cvVal.put("edad",iEdad);
        //    tabla, campos nulos, valores
        Lreg =  sqlMIDB.insert("tblDatos",null,cvVal);
        Toast.makeText(this, "Registro: " + Lreg, Toast.LENGTH_SHORT).show();


    }
    //limpiar los componentes de la actividad
    public void onClickLimp(View v){

    }
    public void onClickactua(View v){
        String sNom = edtNom.getText().toString();
        String sApe = edtApe.getText().toString();
        int iEdad = Integer.parseInt(edtEdad.getText().toString());
        ContentValues cvVal = new ContentValues();
        //clave--->columna, valor (tipo primitivo)
        cvVal.put("nombre",sNom);
        cvVal.put("apellido",sApe);
        cvVal.put("edad", iEdad);
        //    actualizar los valores
        String sVal = "" + Lreg;
        String [] sArgs = {"" + Lreg};
        sqlMIDB.update("tblDatos",cvVal, "rowID = ?", sArgs );
    }
    public void onClickBorrar(View v){
        String sNom = edtNom.getText().toString();
        String sApe = edtApe.getText().toString();
        int iEdad = Integer.parseInt(edtEdad.getText().toString());
        ContentValues cvVal = new ContentValues();
        //clave--->columna, valor (tipo primitivo)
        cvVal.put("nombre",sNom);
        cvVal.put("apellido",sApe);
        cvVal.put("edad", iEdad);
        //    borrar los valores
        String sVal = "" + Lreg;
        String[] sArgs = {"" + Lreg};
        sqlMIDB.delete("tblDatos", "rowID = ?", sArgs );
    }
    public void click(View v){
        startActivityForResult(inSecun, SECUNDARIA);
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
