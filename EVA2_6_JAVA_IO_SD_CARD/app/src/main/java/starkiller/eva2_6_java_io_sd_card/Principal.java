package starkiller.eva2_6_java_io_sd_card;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Principal extends AppCompatActivity {
    TextView txtMen;
    String sRutaSD;//ruta a la tarjeta de memoria
    final static String ARCHIVO = "/miarchivo.txt";//archivo a leer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        txtMen = (TextView)findViewById(R.id.txtMen);
        sRutaSD = Environment.getExternalStorageDirectory().getAbsolutePath();
        //Toast.makeText(this,sRutaSD,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            OutputStream osEscribir = new FileOutputStream(sRutaSD + ARCHIVO);
            OutputStreamWriter oswEscribe = new OutputStreamWriter(osEscribir);
            oswEscribe.write("lectura y escritura de la sdcard");
            oswEscribe.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            InputStream isAbrir = new FileInputStream(sRutaSD + ARCHIVO);
            InputStreamReader isLeerBytes = new InputStreamReader(isAbrir);
            BufferedReader brLeerTexto = new BufferedReader(isLeerBytes);
            String sCade;
            while ((sCade= brLeerTexto.readLine())!= null){
                txtMen.append(sCade);
                txtMen.append("\n");
            };
            brLeerTexto.close();
            isLeerBytes.close();
        }catch (Exception e){
            e.printStackTrace();
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
