package starkiller.eva2_7_permisos;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.jar.Manifest;

public class Principal extends AppCompatActivity {
    final static int PERMISOS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if (busco si tengo permisos) ! = (tienes permisos)
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //no tenemos permisos
            ActivityCompat.requestPermissions(this, //actividad
            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISOS);//id para la solicitud

        } else{
            //si tenemos permisos
            LeerArchivo();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EscribirArchivo();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISOS) {
            Toast.makeText(this, permissions[0], Toast.LENGTH_SHORT).show();
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "podemos ver la targeta de memoria", Toast.LENGTH_SHORT).show();
                LeerArchivo();
            }else{
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void LeerArchivo(){

    }

    public void EscribirArchivo(){

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

