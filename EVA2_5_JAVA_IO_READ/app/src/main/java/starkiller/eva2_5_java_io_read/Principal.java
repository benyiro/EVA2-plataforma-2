package starkiller.eva2_5_java_io_read;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Principal extends AppCompatActivity {
    TextView txtMen;
    final static  String Archivo = "miarchivo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        txtMen = (TextView)findViewById(R.id.txtMen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //abrimos el archivo
        try {
            //inputstream
            InputStream isOpen = openFileInput(Archivo);
            //isreader
            InputStreamReader isrLeerBytes = new InputStreamReader(isOpen);
            //buffer
            BufferedReader brLeerTxt = new BufferedReader(isrLeerBytes);
            //leemos
            try {
                String sCade;
                //readline lee linea po rlinea, al llegar al final
                //del archivo regresa null
                while ((sCade = brLeerTxt.readLine()) != null) {
                    txtMen.append(sCade);
                    txtMen.append("\n");
                }
                brLeerTxt.close();//siempre debemos de cerrar el recurso
                isrLeerBytes.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch (Exception e) {
            e.printStackTrace();
    }

    }
    @Override
    protected void onPause() {
        super.onPause();
        //escribir en el archivo
        try{
            OutputStream usEscribir = openFileOutput(Archivo,0);
            OutputStreamWriter oswEscribir = new OutputStreamWriter(usEscribir);
            oswEscribir.write("GUARDANDO LOS DATOS");
            oswEscribir.close();
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
