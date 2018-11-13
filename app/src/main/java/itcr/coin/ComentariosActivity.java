package itcr.coin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;


public class ComentariosActivity extends AppCompatActivity {

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
