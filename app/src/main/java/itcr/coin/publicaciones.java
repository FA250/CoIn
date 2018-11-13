package itcr.coin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class publicaciones extends AppCompatActivity implements AvisosFragment.OnFragmentInteractionListener, ConfiguracionesFragment.OnFragmentInteractionListener,
        RecomendacionesFragment.OnFragmentInteractionListener, ReportesFragment.OnFragmentInteractionListener {

    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private  AvisosFragment avisosFragment;
    private ConfiguracionesFragment configuracionesFragment;
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

        avisosFragment = new AvisosFragment();
        configuracionesFragment = new ConfiguracionesFragment();
        recomendacionesFragment = new RecomendacionesFragment();
        reportesFragment = new ReportesFragment();

        setFragment(avisosFragment);

        //Colores en int
        final int colorRecomendaciones = Color.parseColor("#00bbff");
        final int colorReportes = Color.parseColor("#24d121");



        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_avisos:
                        mainNav.setItemBackgroundResource(R.color.colorAvisos);
                        setFragment(avisosFragment);
                        fabAgregar.setVisibility(View.INVISIBLE);
                        return true;

                    case R.id.nav_configuraciones:
                        mainNav.setItemBackgroundResource(R.color.colorConfiguraciones);
                        setFragment(configuracionesFragment);
                        fabAgregar.setVisibility(View.INVISIBLE);
                        return true;

                    case R.id.nav_recomendaciones:
                        mainNav.setItemBackgroundResource(R.color.colorRecomendaciones);
                        setFragment(recomendacionesFragment);
                        fabAgregar.setVisibility(View.VISIBLE);
                        fabAgregar.setBackgroundColor(colorRecomendaciones);
                        fabAgregar.setRippleColor(colorRecomendaciones);
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
                        fabAgregar.setBackgroundColor(colorReportes);
                        fabAgregar.setRippleColor(colorReportes);
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

        android.support.v4.app.FragmentTransaction fragmentTran = getSupportFragmentManager().beginTransaction();
        fragmentTran.replace(R.id.main_frame, fragment);
        fragmentTran.commitNow();



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
