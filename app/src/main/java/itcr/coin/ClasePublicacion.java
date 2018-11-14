package itcr.coin;

import com.google.firebase.firestore.CollectionReference;

public class ClasePublicacion {
    public  String id;
    public String idUsuario;
    public String nombreUsuario;
    public String Titulo;
    public String Telefono;
    public String Ubicacion;
    public String Descripcion;
    public String Horario;
    public String segundos;
    public String minuto;
    public String hora;
    public String dia;
    public String mes;
    public String anno;
    public Boolean reportada;

    private String tipo;
    public ClasePublicacion() {
        super();
    }

    public ClasePublicacion(String idUsuario, String nombreUsuario, String Titulo, String Telefono, String Ubicacion, String Decripcion, String Horario, String segundos, String minuto, String hora, String dia, String mes, String anno) {
        this.id=anno+mes+dia+hora+minuto+segundos;
        this.idUsuario=idUsuario;
        this.nombreUsuario=nombreUsuario;
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
        this.reportada=false;
    }

    public ClasePublicacion(String idUsuario, String nombreUsuario, String Titulo, String Telefono, String Ubicacion, String Decripcion, String Horario, String segundos, String minuto, String hora, String dia, String mes, String anno, String tipo) {
        this.id=anno+mes+dia+hora+minuto+segundos;
        this.idUsuario=idUsuario;
        this.nombreUsuario=nombreUsuario;
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
        this.tipo = tipo;
        this.reportada=false;
    }

    public void ActivarReporte(CollectionReference DB, String ID){
        DB.document(ID).update("reportada",true);
    }



    public boolean CrearPublicacion(CollectionReference DB){
        try {
            DB.document(id).set(this);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public String getTipo() {
        return tipo;
    }
}
