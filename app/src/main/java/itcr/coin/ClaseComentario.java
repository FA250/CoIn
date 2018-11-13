package itcr.coin;

import com.google.firebase.firestore.CollectionReference;

public class ClaseComentario {
    public String id;
    public String NombreUsuario;
    public String Comentario;
    public String idUsuario;
    public String segundos;
    public String minuto;
    public String hora;
    public String dia;
    public String mes;
    public String anno;

    public ClaseComentario(String NombreUsuario,String Comentario,String idUsuario,String segundos, String minuto, String hora, String dia, String mes, String anno) {
        this.id=anno+mes+dia+hora+minuto+segundos;
        this.NombreUsuario=NombreUsuario;
        this.Comentario=Comentario;
        this.idUsuario=idUsuario;
        this.segundos = segundos;
        this.minuto = minuto;
        this.hora = hora;
        this.dia = dia;
        this.mes = mes;
        this.anno = anno;
    }
    public boolean AgregarComentario(CollectionReference DB,String idPublicacion){
        try {
            DB.document(idPublicacion).collection("Comentario").document(id).set(this);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
