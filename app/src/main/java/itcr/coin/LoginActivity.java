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
    protected void VerificarUsuario(CollectionReference DB) {
        EditText ETCorreo = (EditText) findViewById(R.id.txtCorreo);
        EditText ETContrasenna = (EditText) findViewById(R.id.txtContrasenna);

        if (ETCorreo.getText().toString().trim().length() == 0) {
            ETCorreo.setError("Ingrese el correo");
        } else if (!isEmailValid(ETCorreo.getText().toString().trim())) {
            ETCorreo.setError("Ingrese un correo válido");
        } else if (ETContrasenna.getText().toString().trim().length() == 0) {
            ETContrasenna.setError("Ingrese la contraseña");
        } else {

            String correo = ETCorreo.getText().toString().trim();
            String contrasenna = ((EditText) findViewById(R.id.txtContrasenna)).getText().toString().trim();

            UserExist(DB, correo, contrasenna);

            //Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        }
    }

    private void UserExist(CollectionReference DB, String correo, final String contrasenna){
        DB.document(correo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot=task.getResult();
                if(snapshot != null && snapshot.exists() && snapshot.get("Password").toString().equals(contrasenna))
                    Ingresar(task.getResult());
                else
                    Toast.makeText(LoginActivity.this,"Correo o contraseña incorrectos",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Ingresar(DocumentSnapshot snapshot){
        ClaseUsuario usuario=new ClaseUsuario(snapshot.get("Nombre").toString(),snapshot.get("Correo").toString(),Integer.parseInt(snapshot.get("Tipo").toString()));

        if(usuario.Tipo!=1)
            Toast.makeText(this,"Los administradores o proveedores solo pueden ingresar desde la aplicación web",Toast.LENGTH_SHORT).show();
        else { //Abrir ventana principal
            Intent i=new Intent(LoginActivity.this, publicaciones.class);
            i.putExtra("correo",usuario.Correo);
            i.putExtra("nombre",usuario.Nombre);
            startActivity(i);
        }
    }



}
