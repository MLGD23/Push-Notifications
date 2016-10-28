package com.knaujolimac.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;


import com.knaujolimac.proyectomascotas.adapter.MascotaFavoritoAdaptador;
import com.knaujolimac.proyectomascotas.pojo.Mascota;
import com.knaujolimac.proyectomascotas.presentador.IRecylerViewMascotaFavoritaPresenter;
import com.knaujolimac.proyectomascotas.presentador.RecylerViewMascotaFavoritaPresenter;
import com.knaujolimac.proyectomascotas.view.IRecyclerViewMascotaFavoritaView;

import java.util.ArrayList;

public class MascotaFavoritoActivity extends AppCompatActivity implements IRecyclerViewMascotaFavoritaView {

    private RecyclerView rvMascotasFav;
    private IRecylerViewMascotaFavoritaPresenter iRecylerViewMascotaFavoritaPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_favorito);

        rvMascotasFav = (RecyclerView)findViewById(R.id.rvMascotasFav);

        Toolbar miActionBar = (Toolbar)findViewById(R.id.miActionBarB);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        iRecylerViewMascotaFavoritaPresenter = new RecylerViewMascotaFavoritaPresenter(this,this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(MascotaFavoritoActivity.this,MainActivity.class);
            startActivity(intent);
            //se finaliza el activity para optimizar la memoria
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void generarLinearLayoutVertical() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMascotasFav.setLayoutManager(llm);
    }

    @Override
    public MascotaFavoritoAdaptador crearAdaptador(ArrayList<Mascota> listMascotasFavoritas) {
        MascotaFavoritoAdaptador mascotaAdaptador = new MascotaFavoritoAdaptador(listMascotasFavoritas, this);
        return mascotaAdaptador;
    }

    @Override
    public void inicializarAdaptadorRV(MascotaFavoritoAdaptador mascotaAdaptador) {
        rvMascotasFav.setAdapter(mascotaAdaptador);
    }
}
