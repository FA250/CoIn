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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class RecomendacionesFragment extends Fragment {

    View rootView;
    ClasePublicacion[] publicaciones;

    TextView NoPublicaciones;
    ListView ListaPublicaciones;

    static String Usuario,IdUsuario;

    public static RecomendacionesFragment newInstance(String usuario,String idUsuario) {
        RecomendacionesFragment fragment = new RecomendacionesFragment();
        Usuario=usuario;
        IdUsuario=idUsuario;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_recomendaciones, container, false);

        CollectionReference DB=FirebaseFirestore.getInstance().collection("Recomendacion");

        NoPublicaciones=(TextView) rootView.findViewById(R.id.labelNoPublicaciones);
        ListaPublicaciones=(ListView)rootView.findViewById(R.id.listRecomendaciones);

        ActualizarPublicaciones(DB);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActualizarPublicaciones(FirebaseFirestore.getInstance().collection("Recomendacion"));
    }

    private void ActualizarPublicaciones(CollectionReference DB) {
        DB.orderBy("id",Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                publicaciones=new ClasePublicacion[queryDocumentSnapshots.size()];
                if(queryDocumentSnapshots.size()>0)
                    NoPublicaciones.setVisibility(View.INVISIBLE);
                else
                    NoPublicaciones.setVisibility(View.VISIBLE);

                int cont=0;
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    publicaciones[cont]=new ClasePublicacion(documentSnapshot.get("idUsuario").toString(),documentSnapshot.get("nombreUsuario").toString(),documentSnapshot.get("Titulo").toString(),documentSnapshot.get("Telefono").toString(),
                                                                documentSnapshot.get("Ubicacion").toString(),documentSnapshot.get("Descripcion").toString(),documentSnapshot.get("Horario").toString(),documentSnapshot.get("segundos").toString(),documentSnapshot.get("minuto").toString(),
                                                                documentSnapshot.get("hora").toString(),documentSnapshot.get("dia").toString(),documentSnapshot.get("mes").toString(),documentSnapshot.get("anno").toString());
                    cont++;
                }
                CustomList adapter = new CustomList(getActivity(), publicaciones, "Recomendacion",Usuario,IdUsuario);

                if(adapter!= null)
                    ListaPublicaciones.setAdapter(adapter);
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
