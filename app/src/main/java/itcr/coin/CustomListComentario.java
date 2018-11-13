package itcr.coin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListComentario extends ArrayAdapter {
    private final Activity context;
    ClaseComentario[] comentarios;


    public CustomListComentario(Activity context,
                                 ClaseComentario[] comentarios) {
        super(context, R.layout.item_lista_comentario, comentarios);
        this.context = context;
        this.comentarios=comentarios;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_lista_comentario, null, true);
        TextView lbNombre = rowView.findViewById(R.id.lbUsuario);
        TextView lbComentario = rowView.findViewById(R.id.lbComentario);

        lbNombre.setText(comentarios[position].NombreUsuario);
        lbComentario.setText(comentarios[position].Comentario);

        return rowView;
    }
}
