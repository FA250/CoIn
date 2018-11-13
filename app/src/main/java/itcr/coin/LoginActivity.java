package itcr.coin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Abre la ventana para el registro del usuario
        Button btnRegistro= findViewById(R.id.btnCrearCuenta);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Registro.class));
            }
        });

        //Funcionlidad para ingresar y abrir la ventana principal con las publicaciones
        Button btnLogin= findViewById(R.id.btnIngresar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificarUsuario(FirebaseFirestore.getInstance().collection("Usuario"));
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

    //Boton ingresar
    protected void VerificarUsuario(CollectionReference DB)  {
        EditText ETCorreo = (EditText)findViewById(R.id.txtCorreo);
        EditText ETContrasenna = (EditText)findViewById(R.id.txtContrasenna);

        if( ETCorreo.getText().toString().trim().length() == 0 ) {
            ETCorreo.setError("Ingrese el correo");
        }
        else  if(!isEmailValid(ETCorreo.getText().toString().trim())) {
            ETCorreo.setError("Ingrese un correo válido");
        }
        else if(ETContrasenna.getText().toString().trim().length() == 0) {
            ETContrasenna.setError("Ingrese la contraseña");
        }
        else {

            String correo = ETCorreo.getText().toString().trim();
            String contrasenna = ((EditText) findViewById(R.id.txtContrasenna)).getText().toString().trim();

            if(UserExist(DB,correo,contrasenna)){
                Usuario usuario=new Usuario(snapshot.get("Nombre").toString(),snapshot.get("Correo").toString(),(Integer) snapshot.get("Tipo"));

                if(usuario.Tipo!=1)
                    Toast.makeText(this,"Los administradores o proveedores solo pueden ingresar desde la aplicación web",Toast.LENGTH_SHORT).show();
                else //Abrir ventana principal
                    startActivity(new Intent(LoginActivity.this,publicaciones.class));

            }else{
                Toast.makeText(this,"Correo o contraseña incorrectos",Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        }
    }

    DocumentSnapshot snapshot;
    private boolean UserExist(CollectionReference DB, String correo, final String contrasenna){

        String idCorreo=correo.split("@")[0]+correo.split("@")[1];
        DB.document(idCorreo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult() != null && task.getResult().get("password").toString().equals(contrasenna))
                    snapshot=task.getResult();
                else
                    snapshot=null;
            }
        });
        if(snapshot != null && snapshot.exists())
            return true;
        else
            return false;
    }


}
