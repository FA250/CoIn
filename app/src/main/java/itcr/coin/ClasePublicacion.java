package itcr.coin;

import com.google.firebase.firestore.CollectionReference;

public class ClasePublicacion {
    public String Titulo;
    public int Telefono;
    public  String Ubicacion;
    public String Descripcion;
    public String Horario;
    public int segundos;
    public int minuto;
    public int hora;
    public int dia;
    public int mes;
    public int anno;

    public ClasePublicacion() {
        super();
    }

    public ClasePublicacion(String Titulo, int Telefono, String Ubicacion, String Decripcion, String Horario, int segundos, int minuto, int hora, int dia, int mes, int anno) {

        this.Titulo = Titulo;
        this.Telefono = Telefono;
        this.Ubicacion = Ubicacion;
        this.Descripcion = Decripcion;
        this.Horario = Horario;
        this.segundos = segundos;
        this.minuto = minuto;
        this.hora = hora;
        this.dia = dia;
        this.mes = mes;
        this.anno = anno;
    }

    public boolean CrearPublicacion(CollectionReference DB){
        try {
            String id = Integer.toString(anno)+Integer.toString(mes)+Integer.toString(dia)+Integer.toString(hora)+Integer.toString(minuto)+Integer.toString(segundos);
            DB.document(id).set(this);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
