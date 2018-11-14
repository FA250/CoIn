package itcr.coin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SuscripcionesFragment extends Fragment {

    View rootView;
    ClasePublicacion[] publicaciones1;
    ClasePublicacion[] publicaciones2;

    TextView NoPublicaciones;
    ListView ListaPublicaciones;

    CollectionReference DB1,DB2;

    static String Usuario,IdUsuario;

    public static SuscripcionesFragment newInstance(String usuario,String idUsuario) {
        SuscripcionesFragment fragment = new SuscripcionesFragment();
        Usuario=usuario;
        IdUsuario=idUsuario;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_recomendaciones, container, false);

        DB1=FirebaseFirestore.getInstance().collection("Recomendacion");
        DB2=FirebaseFirestore.getInstance().collection("Incidente");

        NoPublicaciones=(TextView) rootView.findViewById(R.id.labelNoPublicaciones);
        ListaPublicaciones=(ListView)rootView.findViewById(R.id.listRecomendaciones);

        ActualizarPublicaciones(DB1,DB2);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Suscritos=new ArrayList<String>();
        ActualizarPublicaciones(DB1,DB2);
    }

    ArrayList<String> Suscritos=new ArrayList<String>();
    private void ActualizarPublicaciones(final CollectionReference DB1, final CollectionReference DB2) {
        CollectionReference DBUser = FirebaseFirestore.getInstance().collection("Usuario");
        DBUser.document(IdUsuario).collection("Suscrito").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.size() > 0) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        Suscritos.add(documentSnapshot.getString("correo"));



                    if (Suscritos != null && Suscritos.size() > 0) {
                        NoPublicaciones.setVisibility(View.INVISIBLE);
                        DB1.orderBy("id", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                int cont = 0;
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    if (Suscritos.contains(documentSnapshot.get("idUsuario").toString()))
                                        cont++;
                                }
                                publicaciones1 = new ClasePublicacion[cont];
                                cont=0;
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        if (Suscritos.contains(documentSnapshot.get("idUsuario").toString())) {
                                            publicaciones1[cont] = new ClasePublicacion(documentSnapshot.get("idUsuario").toString(), documentSnapshot.get("nombreUsuario").toString(), documentSnapshot.get("Titulo").toString(), documentSnapshot.get("Telefono").toString(),
                                                documentSnapshot.get("Ubicacion").toString(), documentSnapshot.get("Descripcion").toString(), documentSnapshot.get("Horario").toString(), documentSnapshot.get("segundos").toString(), documentSnapshot.get("minuto").toString(),
                                                documentSnapshot.get("hora").toString(), documentSnapshot.get("dia").toString(), documentSnapshot.get("mes").toString(), documentSnapshot.get("anno").toString(), "Recomendacion");
                                            cont++;
                                    }

                                }

                                DB2.orderBy("id", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                        int cont = 0;
                                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                            if (Suscritos.contains(documentSnapshot.get("idUsuario").toString()))
                                                cont++;
                                        }
                                        publicaciones2 = new ClasePublicacion[cont];
                                        cont=0;
                                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                            if (Suscritos.contains(documentSnapshot.get("idUsuario").toString())) {
                                                publicaciones2[cont] = new ClasePublicacion(documentSnapshot.get("idUsuario").toString(), documentSnapshot.get("nombreUsuario").toString(), documentSnapshot.get("Titulo").toString(), documentSnapshot.get("Telefono").toString(),
                                                        documentSnapshot.get("Ubicacion").toString(), documentSnapshot.get("Descripcion").toString(), documentSnapshot.get("Horario").toString(), documentSnapshot.get("segundos").toString(), documentSnapshot.get("minuto").toString(),
                                                        documentSnapshot.get("hora").toString(), documentSnapshot.get("dia").toString(), documentSnapshot.get("mes").toString(), documentSnapshot.get("anno").toString(), "Incidente");
                                                cont++;
                                            }

                                        }

                                        ClasePublicacion[] publicaciones;
                                        if(publicaciones1!=null || publicaciones2!=null)
                                            NoPublicaciones.setVisibility(View.INVISIBLE);
                                        else
                                            NoPublicaciones.setVisibility(View.VISIBLE);
                                        publicaciones = ArrayUtils.concat(publicaciones1, publicaciones2);
                                        /*else if(publicaciones1)
                                            publicaciones=publicaciones1;
                                        else
                                            publicaciones=publicaciones2;
                                        Toast.makeText(getActivity(),"DDD",Toast.LENGTH_SHORT).show();*/

                                        CustomList adapter = new CustomList(getActivity(), publicaciones, null, Usuario, IdUsuario);

                                        if (adapter != null)
                                            ListaPublicaciones.setAdapter(adapter);
                                    }
                                });

                            }
                        });
                    } else {
                        NoPublicaciones.setVisibility(View.VISIBLE);
                    }
                }
            }

        });

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
