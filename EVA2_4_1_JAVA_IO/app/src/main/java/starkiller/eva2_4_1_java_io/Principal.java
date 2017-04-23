package starkiller.eva2_4_1_java_io;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Principal extends AppCompatActivity {
 TextView txtMen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        txtMen = (TextView) findViewById(R.id.txtMen);
        //inputstream --> abre el recurso
        InputStream isOpen = getResources().openRawResource(R.raw.datos);
        //inputstreamreader-->(inputstream)
        InputStreamReader isrLeeBytes = new InputStreamReader(isOpen);
        //bufferedreader (inputstream)
        BufferedReader brLeerTxt = new BufferedReader(isrLeeBytes);
        //aqui leemos
        try {
            String sCade;
            //readline lee linea po rlinea, al llegar al final
            //del archivo regresa null
           while ((sCade = brLeerTxt.readLine())!=null){
               txtMen.append(sCade);
               txtMen.append("\n");
           }
            brLeerTxt.close();//siempre debemos de cerrar el recurso
            isrLeeBytes.close();
        } catch (IOException e) {
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
