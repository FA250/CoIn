package itcr.coin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomList extends ArrayAdapter {

    private final Activity context;
    ClasePublicacion[] publicaciones;

    public CustomList(Activity context, ClasePublicacion[] publicaciones) {
        super(context, R.layout.item_lista_publicacion, publicaciones);
        this.context = context;
        this.publicaciones = publicaciones;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_lista_publicacion, null, true);

        TextView txtNombre= rowView.findViewById(R.id.txtUsuario);
        TextView txtTitulo= rowView.findViewById(R.id.txtTitulo);
        TextView txtUbicacion= rowView.findViewById(R.id.txtUbicacion);
        TextView txtDescripcion= rowView.findViewById(R.id.txtDescripcion);
        TextView txtHorario= rowView.findViewById(R.id.txtHorario);

        ImageButton btnSuscribirse= rowView.findViewById(R.id.btnSuscribirse);
        ImageButton btnComentar= rowView.findViewById(R.id.btnComentar);
        ImageButton btnReportar= rowView.findViewById(R.id.btnReportar);

        btnSuscribirse.setTag(position);
        btnComentar.setTag(position);
        btnReportar.setTag(position);

        txtNombre.setText(publicaciones[position].nombreUsuario);
        txtTitulo.setText(publicaciones[position].Titulo);
        txtUbicacion.setText(publicaciones[position].Ubicacion);
        txtDescripcion.setText(publicaciones[position].Descripcion);
        txtHorario.setText(publicaciones[position].Horario);


        return rowView;
    }
}
