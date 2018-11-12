package itcr.coin;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

public class Usuario {
    public String Nombre;
    public String Correo;
    public String Password;
    public int Tipo;

    public Usuario(){

    }

    public Usuario(String Nombre, String Correo, String Password, int Tipo){
        this.Nombre=Nombre;
        this.Correo=Correo;
        this.Password=Password;
        this.Tipo=Tipo;
    }

    public Usuario(String Nombre, String Correo, int Tipo){
        this.Nombre=Nombre;
        this.Correo=Correo;
        this.Tipo=Tipo;
    }


    public boolean RegistrarUsuario(CollectionReference DB){
        try {
            DB.document(this.Correo.split("@")[0]).set(this);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
