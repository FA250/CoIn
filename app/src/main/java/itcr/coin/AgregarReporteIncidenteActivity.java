package itcr.coin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AgregarReporteIncidenteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reporte_incidente);

        final CollectionReference Ref= FirebaseFirestore.getInstance().collection("Recomendacion");

        Button btnAgregarRecomendacion= findViewById(R.id.btnAgregarR);

        btnAgregarRecomendacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacionCrearRecomendacion(Ref);
            }
        });
    }

    public void validacionCrearRecomendacion(CollectionReference DB) {
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
            ClasePublicacion nuevaRecomendacion=new ClasePublicacion(ETTitulo.getText().toString(),Integer.parseInt(ETTelefono.getText().toString()),ETUbicacion.getText().toString(),ETDescripcion.getText().toString(),ETHorario.getText().toString(),getSecond(),getMinute(),getHour(),getDay(),getMonth(),getYear());

            nuevaRecomendacion.CrearPublicacion(DB);

            progressBar.setVisibility(View.GONE);
            //Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        }
    }



    //---------------------- Obtener Fecha ---------------------------
    private int getHour(){
        Calendar c = Calendar.getInstance();
        int Hora = c.get(Calendar.HOUR_OF_DAY);
        return Hora;
    }
    private int getMinute(){
        Calendar c = Calendar.getInstance();
        int Minuto = c.get(Calendar.MINUTE);
        return Minuto;
    }
    private int getDay(){
        Calendar c = Calendar.getInstance();
        int Dia = c.get(Calendar.DAY_OF_MONTH);
        return Dia;
    }
    private int getMonth(){
        Calendar c = Calendar.getInstance();
        int Mes = c.get(Calendar.MONTH)+1;
        return Mes;
    }
    private int getYear(){
        Calendar c = Calendar.getInstance();
        int Año = c.get(Calendar.YEAR);
        return Año;
    }
    private int getSecond(){
        Calendar c = Calendar.getInstance();
        int Segundo = c.get(Calendar.SECOND);
        return Segundo;
    }
}
