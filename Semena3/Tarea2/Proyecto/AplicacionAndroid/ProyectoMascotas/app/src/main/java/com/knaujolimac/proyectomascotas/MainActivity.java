package com.knaujolimac.proyectomascotas;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.knaujolimac.proyectomascotas.adapter.PageAdapter;
import com.knaujolimac.proyectomascotas.pojo.ConstructorPerfilMascota;
import com.knaujolimac.proyectomascotas.pojo.UsuarioPerfil;
import com.knaujolimac.proyectomascotas.view.fragment.MascotasFragment;
import com.knaujolimac.proyectomascotas.view.fragment.PerfilFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar miActionBar;
    private ViewPager idViewPager;
    private TabLayout idTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miActionBar = (Toolbar) findViewById(R.id.miActionBar);
        idViewPager = (ViewPager) findViewById(R.id.idViewPager);
        idTabLayout = (TabLayout) findViewById(R.id.idTabLayout);

        this.setUpViewPager();

        if(idTabLayout !=null){
            setSupportActionBar(miActionBar);
        }

        ImageButton btnFavorito = (ImageButton) findViewById(R.id.btnFavorito);

        btnFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MascotaFavoritoActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_opciones,menu);
        miActionBar.inflateMenu(R.menu.menu_opciones);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.mContacto:
                intent = new Intent(this,DatosContactoActivity.class);
                startActivity(intent);
                break;
            case R.id.mAcerca:
                intent = new Intent(this,AcercaDeActivity.class);
                startActivity(intent);
                break;
            case R.id.mConfigurarCta:
                intent = new Intent(this,ConfigurarCuenta.class);
                startActivity(intent);
                break;
            default:
                //Registrar el dispositivo
                this.registrarDispositivo();

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método encargado de registrar el dispositivo en el servidor
     */
    private void registrarDispositivo() {
        ConstructorPerfilMascota constructorPerfilMascota = new ConstructorPerfilMascota(this);
        constructorPerfilMascota.registrarDispositivoServidor();
    }


    private ArrayList<Fragment> prepararFragments(){
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(new MascotasFragment());
        fragments.add(new PerfilFragment());

        return fragments;
    }

    private void setUpViewPager(){
        idViewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),this.prepararFragments()));
        idTabLayout.setupWithViewPager(idViewPager);
        idTabLayout.getTabAt(0).setIcon(R.drawable.ic_action_name);
        idTabLayout.getTabAt(1).setIcon(R.drawable.ic_perfil);
    }




}