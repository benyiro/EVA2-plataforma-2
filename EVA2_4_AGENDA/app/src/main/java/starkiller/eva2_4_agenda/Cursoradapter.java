package starkiller.eva2_4_agenda;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Usuario HP on 15/03/2017.
 */
public class Cursoradapter extends CursorAdapter {

    public Cursoradapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.mis_datos, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtNom = (TextView)view.findViewById(R.id.txtNom);
        TextView txtTel = (TextView)view.findViewById(R.id.txtTel);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));
        txtNom.setText(name);
        txtTel.setText(phone);
    }
}
