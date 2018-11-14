package itcr.coin;

import com.google.firebase.firestore.CollectionReference;

public class ClaseAviso {
    public String id;
    public String Titulo;
    public String Servicio;
    public String Ubicacion;
    public String Descripcion;
    public String FechaInicio;
    public String FechaFin;

    public ClaseAviso(String id,String Titulo, String Servicio, String Ubicacion, String Descripcion, String FechaInicio, String FechaFin){
        this.id = id;
        this.Titulo = Titulo;
        this.Servicio = Servicio;
        this.Ubicacion = Ubicacion;
        this.Descripcion = Descripcion;
        this.FechaInicio = FechaInicio;
        this.FechaFin = FechaFin;
    }

    public void ActivarReporte(CollectionReference DB, String ID){
        DB.document(ID).update("reportada",true);
    }
}
