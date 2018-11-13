package itcr.coin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AgregarRecomendacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_recomendacion);
        final CollectionReference Ref= FirebaseFirestore.getInstance().collection("Recomendacion");

        Intent i = getIntent();
        final String nombreUsuario=i.getStringExtra("nombre");
        final String correoUsuario=i.getStringExtra("correo");

        Button btnAgregarRecomendacion= findViewById(R.id.btnAgregarR);

        btnAgregarRecomendacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacionCrearRecomendacion(Ref,nombreUsuario,correoUsuario);
            }
        });
    }

    public void validacionCrearRecomendacion(CollectionReference DB, String nombreUsuario, String idUsuario) {
        EditText ETTitulo = findViewById(R.id.txtTitulo);
        EditText ETTelefono = findViewById(R.id.txtTelefono);
        EditText ETUbicacion = findViewById(R.id.txtUbicacion);
        EditText ETDescripcion = findViewById(R.id.txtDescripcion);
        EditText ETHorario = findViewById(R.id.txtHorario);


        if( ETTitulo.getText().toString().length() == 0 ) {
            ETTitulo.setError("Ingrese el titulo");
        }
        else  if(ETTelefono.getText().toString().length() == 0 ) {
            ETTelefono.setError("Ingrese el teléfono");
        }
        else if(ETUbicacion.getText().toString().length() == 0) {
            ETUbicacion.setError("Ingrese la ubicación");
        }
        else if(ETDescripcion.getText().toString().length() == 0) {
            ETDescripcion.setError("Ingrese la descripcion");
        }
        else if(ETHorario.getText().toString().length() == 0) {
            ETHorario.setError("Ingrese el horario");
        }
        else {
            ProgressBar progressBar=new ProgressBar(this);

            progressBar.setVisibility(View.VISIBLE);
            ClasePublicacion nuevaRecomendacion=new ClasePublicacion(idUsuario,nombreUsuario,ETTitulo.getText().toString(),ETTelefono.getText().toString(),ETUbicacion.getText().toString(),ETDescripcion.getText().toString(),ETHorario.getText().toString(),getSecond(),getMinute(),getHour(),getDay(),getMonth(),getYear());

            if(nuevaRecomendacion.CrearPublicacion(DB)) {
                Toast.makeText(this,"Se agrego la publicación",Toast.LENGTH_SHORT).show();
                this.finish();
            }
            else
                Toast.makeText(this,"Ocurrió un error al agregar la publicación",Toast.LENGTH_LONG).show();


            progressBar.setVisibility(View.GONE);
            //Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        }
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
