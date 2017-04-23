package starkiller.exa_platii_prac_2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Usuario HP on 06/04/2017.
 */
public class DatabaseAdapter extends CursorAdapter {
    public DatabaseAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.my_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtVwName = (TextView) view.findViewById(R.id.txtVwUsuario);
        TextView txtVwPhone = (TextView) view.findViewById(R.id.txtVwNombre);
        String nombre = "", usuario = "";
        try {
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            nombre.concat(" " + cursor.getString(cursor.getColumnIndexOrThrow("apellido")));
            usuario = cursor.getString(cursor.getColumnIndexOrThrow("usuario"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("ruta"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtVwName.setText(nombre);
        txtVwPhone.setText(usuario);
    }
}