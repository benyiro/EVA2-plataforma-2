package starkiller.eva2_3_cursor_adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Usuario HP on 14/03/2017.
 */
public class CursorAdaper extends CursorAdapter {

    public CursorAdaper(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.mis_datos, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtNom, txtApe, txteda;
        txtNom = (TextView)view.findViewById(R.id.txtNom);
        txtApe = (TextView)view.findViewById(R.id.txtApe);
        txteda = (TextView)view.findViewById(R.id.txteda);
        //enlazamos los datos del cursor a los textview
        String sNom = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
        String sApe = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
        String sEdad = cursor.getString(cursor.getColumnIndexOrThrow("edad"));
        //
        txtNom.setText(sNom);
        txtApe.setText(sApe);
        txteda.setText(sEdad);
    }
}
