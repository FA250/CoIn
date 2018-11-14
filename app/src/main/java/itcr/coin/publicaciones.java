package itcr.coin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class publicaciones extends AppCompatActivity implements AvisosFragment.OnFragmentInteractionListener, SuscripcionesFragment.OnFragmentInteractionListener,
        RecomendacionesFragment.OnFragmentInteractionListener, ReportesFragment.OnFragmentInteractionListener {

    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private  AvisosFragment avisosFragment;
    private SuscripcionesFragment configuracionesFragment;
    private RecomendacionesFragment recomendacionesFragment;
    private  ReportesFragment reportesFragment;
    private FloatingActionButton fabAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        Intent i = getIntent();

        final String nombre=i.getStringExtra("nombre");
        final String correo=i.getStringExtra("correo");

        mainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        mainFrame = (FrameLayout) findViewById(R.id.main_frame);
        fabAgregar = (FloatingActionButton) findViewById(R.id.fab);
        fabAgregar.setVisibility(View.INVISIBLE);

        avisosFragment = new AvisosFragment().newInstance(nombre,correo);
        configuracionesFragment = new SuscripcionesFragment().newInstance(nombre,correo);
        recomendacionesFragment = new RecomendacionesFragment().newInstance(nombre,correo);
        reportesFragment = new ReportesFragment().newInstance(nombre,correo);

        setFragment(avisosFragment);

        //Colores en int


        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colorsRecomendaciones = new int[] {
                Color.parseColor("#00bbff"),
                Color.parseColor("#00bbff"),
                Color.parseColor("#00bbff"),
                Color.parseColor("#0298ce")
        };

        int[] colorsReportes= new int[] {
                Color.parseColor("#24d121"),
                Color.parseColor("#24d121"),
                Color.parseColor("#24d121"),
                Color.parseColor("#199117")
        };

        final ColorStateList coloresRec = new ColorStateList(states, colorsRecomendaciones);
        final ColorStateList coloresRep = new ColorStateList(states, colorsReportes);

        getSupportActionBar().setTitle(
                Html.fromHtml("<font color='" + getResources().getColor(R.color.colorAvisos) + "'>"
                        + "Avisos</font>"));



        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_avisos:
                        mainNav.setItemBackgroundResource(R.color.colorAvisos);
                        setFragment(avisosFragment);
                        fabAgregar.setVisibility(View.INVISIBLE);
                        getSupportActionBar().setTitle(
                                Html.fromHtml("<font color='" + getResources().getColor(R.color.colorAvisos) + "'>"
                                        + "Avisos</font>"));
                        return true;

                    case R.id.nav_configuraciones:
                        mainNav.setItemBackgroundResource(R.color.colorConfiguraciones);
                        setFragment(configuracionesFragment);
                        fabAgregar.setVisibility(View.INVISIBLE);
                        getSupportActionBar().setTitle(
                                Html.fromHtml("<font color='" + getResources().getColor(R.color.colorConfiguraciones) + "'>"
                                        + "Subscripciones</font>"));
                        return true;

                    case R.id.nav_recomendaciones:
                        mainNav.setItemBackgroundResource(R.color.colorRecomendaciones);
                        setFragment(recomendacionesFragment);
                        fabAgregar.setVisibility(View.VISIBLE);
                        fabAgregar.setBackgroundTintList(coloresRec);
                        getSupportActionBar().setTitle(
                                Html.fromHtml("<font color='" + getResources().getColor(R.color.colorRecomendaciones) + "'>"
                                        + "Recomendaciones</font>"));
                        fabAgregar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i=new Intent(publicaciones.this,AgregarRecomendacionActivity.class);
                                i.putExtra("correo",correo);
                                i.putExtra("nombre",nombre);
                                startActivity(i);
                            }
                        });
                        return true;

                    case R.id.nav_reportes:
                        mainNav.setItemBackgroundResource(R.color.colorReportes);
                        setFragment(reportesFragment);
                        fabAgregar.setVisibility(View.VISIBLE);
                        fabAgregar.setBackgroundTintList(coloresRep);
                        getSupportActionBar().setTitle(
                                Html.fromHtml("<font color='" + getResources().getColor(R.color.colorReportes) + "'>"
                                        + "Reportes</font>"));
                        fabAgregar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i=new Intent(publicaciones.this,AgregarReporteIncidenteActivity.class);
                                i.putExtra("correo",correo);
                                i.putExtra("nombre",nombre);
                                startActivity(i);
                            }
                        });
                        return true;

                    default:
                        return false;
                }
            }
        });

    };

    private void setFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTran = getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment);
        fragmentTran.commitNow();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
