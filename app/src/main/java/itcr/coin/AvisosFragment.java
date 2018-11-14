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


public class AvisosFragment extends Fragment {
    View rootView;
    ClaseAviso[] publicaciones;

    TextView NoPublicaciones;
    ListView ListaPublicaciones;

    static String Usuario,IdUsuario;

    public static AvisosFragment newInstance(String usuario,String idUsuario) {
        AvisosFragment fragment = new AvisosFragment();
        Usuario=usuario;
        IdUsuario=idUsuario;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_avisos, container, false);

        CollectionReference DB=FirebaseFirestore.getInstance().collection("Anuncio");

        NoPublicaciones=rootView.findViewById(R.id.labelNoPublicaciones);
        ListaPublicaciones=rootView.findViewById(R.id.listAvisos);

        ActualizarPublicaciones(DB);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActualizarPublicaciones(FirebaseFirestore.getInstance().collection("Anuncio"));
    }

    private void ActualizarPublicaciones(CollectionReference DB) {
        DB/*.orderBy("id",Query.Direction.DESCENDING)*/.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                publicaciones=new ClaseAviso[queryDocumentSnapshots.size()];
                if(queryDocumentSnapshots.size()>0)
                    NoPublicaciones.setVisibility(View.INVISIBLE);
                else
                    NoPublicaciones.setVisibility(View.VISIBLE);

                int cont=0;
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    publicaciones[cont]=new ClaseAviso(documentSnapshot.getId(),documentSnapshot.get("Nombre").toString(),documentSnapshot.get("Servicio").toString(),documentSnapshot.get("Ubicacion").toString(),
                            documentSnapshot.get("Descripcion").toString(),documentSnapshot.get("FechaInicio").toString(),documentSnapshot.get("FechaFinal").toString());
                    cont++;
                }
                CustomListAviso adapter = new CustomListAviso(getActivity(), publicaciones, Usuario,IdUsuario);

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
