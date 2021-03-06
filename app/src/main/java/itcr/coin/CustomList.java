package itcr.coin;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class CustomList extends ArrayAdapter {

    private final Activity context;
    ClasePublicacion[] publicaciones;
    String TipoPublicacion;
    String Usuario,idUsuario;
    int posicion;

    public CustomList(Activity context, ClasePublicacion[] publicaciones, String TipoPublicacion, String Usuario,String idUsuario) {
        super(context, R.layout.item_lista_publicacion, publicaciones);
        this.context = context;
        this.publicaciones = publicaciones;
        this.TipoPublicacion = TipoPublicacion;
        this.Usuario = Usuario;
        this.idUsuario = idUsuario;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
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
                CollectionReference DB;
                if(TipoPublicacion==null)
                    DB=FirebaseFirestore.getInstance().collection(publicaciones[position].getTipo());
                else
                    DB=FirebaseFirestore.getInstance().collection(TipoPublicacion);
                ClasePublicacion publicacion=new ClasePublicacion();
                publicacion.ActivarReporte(DB,publicaciones[position].id);
                Toast.makeText(context,"Se ha reportado la publicación",Toast.LENGTH_SHORT).show();
            }
        });

        btnSuscribirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference DB=FirebaseFirestore.getInstance().collection("Usuario");
                usuarioSuscribir USuscrito=new usuarioSuscribir(publicaciones[position].idUsuario,publicaciones[position].nombreUsuario);
                USuscrito.suscribirse(DB,idUsuario,context);
            }
        });

        return rowView;
    }



    private void AbrirComentarios(){
        Intent i= new Intent(context,ComentariosActivity.class);
        i.putExtra("idPublicacion",publicaciones[posicion].anno+publicaciones[posicion].mes+publicaciones[posicion].dia+publicaciones[posicion].hora+publicaciones[posicion].minuto+publicaciones[posicion].segundos);
        i.putExtra("nombreUsuario",Usuario);
        i.putExtra("idUsuario",idUsuario);
        if(TipoPublicacion==null)
            i.putExtra("TipoPublicacion",publicaciones[posicion].getTipo());
        else
            i.putExtra("TipoPublicacion",TipoPublicacion);
        getContext().startActivity(i);
    }
}
