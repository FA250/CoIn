package itcr.coin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final CollectionReference Ref= FirebaseFirestore.getInstance().collection("Usuario");

        Button btnRegistrarUsuario=findViewById(R.id.btnCrearCuenta);

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacionCrearCuenta(Ref);
            }
        });
    }

    //Validar formato de correo
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void validacionCrearCuenta(CollectionReference DB) {
        EditText ETCorreo = findViewById(R.id.txtCorreo);
        EditText ETNombre = findViewById(R.id.txtNombre);
        EditText ETContrasenna = findViewById(R.id.txtContrasenna);
        EditText ETConfirmarContrasenna = findViewById(R.id.txtConfirmarContrasenna);

        String result="";
        //


        if( ETCorreo.getText().toString().length() == 0 ) {
            ETCorreo.setError("Ingrese el correo");
        }
        else  if(!isEmailValid(ETCorreo.getText().toString())) {
            ETCorreo.setError("Ingrese un correo válido");
        }
        else if(ETNombre.getText().toString().length() == 0) {
            ETNombre.setError("Ingrese un nick");
        }
        else if(ETContrasenna.getText().toString().length() == 0) {
            ETContrasenna.setError("Ingrese la contraseña");
        }
        else if(!ETContrasenna.getText().toString().equals(ETConfirmarContrasenna.getText().toString())) {
            ETConfirmarContrasenna.setError("Las contraseñas deben coincidir");
        }
        else {
            ProgressBar progressBar=new ProgressBar(this);

            progressBar.setVisibility(View.VISIBLE);
            Usuario nuevoUsuario=new Usuario(ETNombre.getText().toString(),ETCorreo.getText().toString(),ETContrasenna.getText().toString(),1);

            if(!UserExist(DB,nuevoUsuario.Correo)){
                //Crear cuenta
                if(nuevoUsuario.RegistrarUsuario(DB)){
                    Toast.makeText(this,"Se ha registrado la cuenta",Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                else{
                    Toast.makeText(this, "Ocurrio un error al registrar la cuenta", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this,"Error: Ya existe un correo igual registrado",Toast.LENGTH_LONG).show();
            }
            progressBar.setVisibility(View.GONE);
            //Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        }
    }

    DocumentSnapshot snapshot;
    private boolean UserExist(CollectionReference DB,String correo){

        DB.document(correo.split("@")[0]+correo.split("@")[1]).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                snapshot=task.getResult();
            }
        });
        if(snapshot != null && snapshot.exists())
            return true;
        else
            return false;
    }


}
