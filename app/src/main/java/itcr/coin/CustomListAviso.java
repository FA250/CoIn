package itcr.coin;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomListAviso extends ArrayAdapter {

    private final Activity context;
    ClaseAviso[] publicaciones;
    String Usuario,idUsuario;
    int posicion;

    public CustomListAviso(Activity context, ClaseAviso[] publicaciones, String Usuario,String idUsuario) {
        super(context, R.layout.item_lista_aviso, publicaciones);
        this.context = context;
        this.publicaciones = publicaciones;
        this.Usuario = Usuario;
        this.idUsuario = idUsuario;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_lista_aviso, null, true);

        TextView txtTitulo= rowView.findViewById(R.id.txtTitulo);
        TextView txtUbicacion= rowView.findViewById(R.id.txtUbicacion);
        TextView txtDescripcion= rowView.findViewById(R.id.txtDescripcion);
        TextView txtFechaInicio= rowView.findViewById(R.id.txtFechaInicio);
        TextView txtFechaFinal= rowView.findViewById(R.id.txtFechaFinal);
        TextView txtServicio= rowView.findViewById(R.id.txtServicio);

        ImageButton btnSuscribirse= rowView.findViewById(R.id.btnSuscribirse);
        ImageButton btnComentar= rowView.findViewById(R.id.btnComentar);
        ImageButton btnReportar= rowView.findViewById(R.id.btnReportar);

        btnSuscribirse.setTag(position);
        btnComentar.setTag(position);
        btnReportar.setTag(position);


        txtTitulo.setText(publicaciones[position].Titulo);
        txtUbicacion.setText(publicaciones[position].Ubicacion);
        txtDescripcion.setText(publicaciones[position].Descripcion);
        txtFechaFinal.setText(publicaciones[position].FechaFin);
        txtFechaInicio.setText(publicaciones[position].FechaInicio);
        txtServicio.setText(publicaciones[position].Servicio);

        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion=(Integer) v.getTag();
                AbrirComentarios();
            }
        });

        btnReportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference DB=FirebaseFirestore.getInstance().collection("Anuncio");
                ClasePublicacion publicacion=new ClasePublicacion();
                publicacion.ActivarReporte(DB,publicaciones[position].id);
                Toast.makeText(context,"Se ha reportado la publicación",Toast.LENGTH_SHORT);
            }
        });

        return rowView;
    }

    private void AbrirComentarios(){
        Intent i= new Intent(context,ComentariosActivity.class);
        i.putExtra("idPublicacion",publicaciones[posicion].id);
        i.putExtra("nombreUsuario",Usuario);
        i.putExtra("idUsuario",idUsuario);
        i.putExtra("TipoPublicacion", "Anuncio");
        getContext().startActivity(i);
    }
}
