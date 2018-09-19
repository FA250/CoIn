package itcr.coin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    //Variables para autenticación con Firebse
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListenenr;

    //Tag para los logs
    private static final String TAG = "CoIn code log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Verificar que exista el usuario para llamar a MainActivity
        mAuth = FirebaseAuth.getInstance();
        mAuthListenenr = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null)
                    startActivity(new Intent(LoginActivity.this, publicaciones.class));
            }
        };

        //TODO crear el boton y el metodo para logearse desde la interfaz grafica y llamar a LogearUsuario(<correo>,<contraseña>);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListenenr);
    }

    //Busca que el usuario exista en Firebase
    private void LogearUsuario(String correo, String contrasenna)
    {
        mAuth.createUserWithEmailAndPassword(correo, contrasenna)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Datos incorrectos",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
