package itcr.coin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class publicaciones extends AppCompatActivity {

    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private  AvisosFragment avisosFragment;
    private ConfiguracionesFragment configuracionesFragment;
    private RecomendacionesFragment recomendacionesFragment;
    private  ReportesFragment reportesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        mainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        mainFrame = (FrameLayout) findViewById(R.id.main_frame);

        avisosFragment = new AvisosFragment();
        configuracionesFragment = new ConfiguracionesFragment();
        recomendacionesFragment = new RecomendacionesFragment();
        reportesFragment = new ReportesFragment();




        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_avisos:
                        mainNav.setItemBackgroundResource(R.color.colorAvisos);
                        //setFragment(avisosFragment);
                        return true;

                    case R.id.nav_configuraciones:
                        mainNav.setItemBackgroundResource(R.color.colorConfiguraciones);
                        //setFragment(configuracionesFragment);
                        return true;

                    case R.id.nav_recomendaciones:
                        mainNav.setItemBackgroundResource(R.color.colorRecomendaciones);
                        //setFragment(recomendacionesFragment);
                        return true;

                    case R.id.nav_reportes:
                        mainNav.setItemBackgroundResource(R.color.colorReportes);
                        //setFragment(reportesFragment);
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
        fragmentTran.commit();



    }
}
