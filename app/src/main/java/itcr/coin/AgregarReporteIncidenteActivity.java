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

public class AgregarReporteIncidenteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reporte_incidente);
        final CollectionReference Ref= FirebaseFirestore.getInstance().collection("Incidente");

        Intent i = getIntent();
        final String nombreUsuario=i.getStringExtra("nombre");
        final String correoUsuario=i.getStringExtra("correo");

        Button btnAgregarIncidente= findViewById(R.id.btnAgregarI);

        btnAgregarIncidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacionCrearIncidente(Ref,nombreUsuario,correoUsuario);
            }
        });
    }

    public void validacionCrearIncidente(CollectionReference DB, String nombreUsuario, String idUsuario) {
        EditText ETTitulo = findViewById(R.id.txtTitulo);
        EditText ETTelefono = findViewById(R.id.txtTelefono);
        EditText ETUbicacion = findViewById(R.id.txtUbicacion);
        EditText ETDescripcion = findViewById(R.id.txtDescripcion);
        EditText ETHorario = findViewById(R.id.txtHorario);
        EditText ETFecha = findViewById(R.id.txtFecha);


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
            ETHorario.setError("Ingrese la hora");
        }
        else if(ETFecha.getText().toString().length() == 0) {
            ETHorario.setError("Ingrese la fecha");
        }
        else {
            ProgressBar progressBar=new ProgressBar(this);

            progressBar.setVisibility(View.VISIBLE);
            ClasePublicacion nuevoIncidente=new ClasePublicacion(idUsuario,nombreUsuario,ETTitulo.getText().toString(),ETTelefono.getText().toString(),ETUbicacion.getText().toString(),ETDescripcion.getText().toString(),ETFecha.getText().toString()+" - "+ETHorario.getText().toString(),getSecond(),getMinute(),getHour(),getDay(),getMonth(),getYear());

            if(nuevoIncidente.CrearPublicacion(DB)) {
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
