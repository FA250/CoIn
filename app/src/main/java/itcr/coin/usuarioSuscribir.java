package itcr.coin;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

public class usuarioSuscribir {

    public String correo;
    public String nombre;

    public usuarioSuscribir(String correo, String nombre) {
        this.correo=correo;
        this.nombre=nombre;
    }

    public void suscribirse(final CollectionReference DB, final String idUsuario, final Activity context){
        DB.document(idUsuario).collection("Suscrito").whereEqualTo("correo",this.correo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0)
                    dessuscribir(DB,idUsuario,context);
                else
                    suscribir(DB,idUsuario,context);
            }
        });
    }

    private void suscribir(CollectionReference DB,String idUsuario, Activity context){
        DB.document(idUsuario).collection("Suscrito").document(this.correo).set(this);
        Toast.makeText(context,"Se ha suscrito al usuario "+this.nombre,Toast.LENGTH_SHORT).show();
    }
    private void dessuscribir(CollectionReference DB,String idUsuario, Activity context){
        DB.document(idUsuario).collection("Suscrito").document(this.correo).delete();
        Toast.makeText(context,"Se ha eliminado la suscripción del usuario "+this.nombre,Toast.LENGTH_SHORT).show();
    }
}
