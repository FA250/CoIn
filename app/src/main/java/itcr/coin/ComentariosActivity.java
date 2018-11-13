package itcr.coin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;


public class ComentariosActivity extends AppCompatActivity {

    ListView listComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        Intent i =getIntent();
        final String idPublicacion=i.getStringExtra("idPublicacion");
        final String nombreUsuario=i.getStringExtra("nombreUsuario");
        final String idUsuario=i.getStringExtra("idUsuario");
        final String TipoPublicacion=i.getStringExtra("TipoPublicacion");

        ImageView btnAgregarComentario=findViewById(R.id.btnAgregarComentario);
        final EditText ETComentaio=findViewById(R.id.txtComentario);

        final CollectionReference DB= FirebaseFirestore.getInstance().collection(TipoPublicacion);

        listComentarios=findViewById(R.id.listComentarios);

        ActualizarComentarios(DB,idPublicacion);

        btnAgregarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ETComentaio.getText().toString().isEmpty())
                    AgregarComentario(new ClaseComentario(nombreUsuario,ETComentaio.getText().toString(),idUsuario,getSecond(),getMinute(),getHour(),getDay(),getMonth(),getYear()), DB, idPublicacion);
                else
                    Toast.makeText(ComentariosActivity.this, "El comentario no puede estar vacío", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void AgregarComentario(ClaseComentario comentario, CollectionReference DB, String idPublicacion) {
        ProgressBar progressBar=new ProgressBar(this);

        progressBar.setVisibility(View.VISIBLE);

        if(comentario.AgregarComentario(DB,idPublicacion)) {
            Toast.makeText(this,"Se agrego el comentario",Toast.LENGTH_SHORT).show();
            this.finish();
        }
        else
            Toast.makeText(this,"Ocurrió un error al agregar el comentario",Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.GONE);
    }

    private void ActualizarComentarios(CollectionReference DB, String idDocumento){
        DB.document(idDocumento).collection("Comentario")/*.orderBy(FieldPath.documentId(), com.google.firebase.firestore.Query.Direction.DESCENDING)*/.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ClaseComentario[] comentarios=new ClaseComentario[queryDocumentSnapshots.size()];
                TextView NoPublicaciones=findViewById(R.id.labelNoComentarios);
                if(queryDocumentSnapshots.size()>0)
                    NoPublicaciones.setVisibility(View.INVISIBLE);
                else
                    NoPublicaciones.setVisibility(View.VISIBLE);

                int cont=0;
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    comentarios[cont]=new ClaseComentario(documentSnapshot.get("NombreUsuario").toString(),documentSnapshot.get("Comentario").toString(),documentSnapshot.get("idUsuario").toString(),documentSnapshot.get("segundos").toString(),
                            documentSnapshot.get("minuto").toString(), documentSnapshot.get("hora").toString(),documentSnapshot.get("dia").toString(),documentSnapshot.get("mes").toString(),documentSnapshot.get("anno").toString());
                    cont++;
                }
                CustomListComentario adapter = new CustomListComentario(ComentariosActivity.this, comentarios);

                if(adapter!= null)
                    listComentarios.setAdapter(adapter);
            }
        });
    }

    //---------------------- Obtener Fecha ---------------------------
    private String getHour(){
        Calendar c = Calendar.getInstance();
        String Hora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        return Hora;
    }
    private String getMinute(){
        Calendar c = Calendar.getInstance();
        String Minuto = Integer.toString(c.get(Calendar.MINUTE));
        return Minuto;
    }
    private String getDay(){
        Calendar c = Calendar.getInstance();
        String Dia = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        return Dia;
    }
    private String getMonth(){
        Calendar c = Calendar.getInstance();
        String Mes = Integer.toString(c.get(Calendar.MONTH)+1);
        return Mes;
    }
    private String getYear(){
        Calendar c = Calendar.getInstance();
        String Año = Integer.toString(c.get(Calendar.YEAR));
        return Año;
    }
    private String getSecond(){
        Calendar c = Calendar.getInstance();
        String Segundo = Integer.toString(c.get(Calendar.SECOND));
        return Segundo;
    }
}
