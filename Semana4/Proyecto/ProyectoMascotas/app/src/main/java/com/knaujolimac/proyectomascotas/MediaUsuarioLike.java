package com.knaujolimac.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.knaujolimac.proyectomascotas.adapter.UsuarioLikeAdaptador;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.knaujolimac.proyectomascotas.presentador.IRecylerViewUsuarioLikePresenter;
import com.knaujolimac.proyectomascotas.presentador.RecylerViewUsuarioLikePresenter;
import com.knaujolimac.proyectomascotas.restApi.ConstantesRestApi;
import com.knaujolimac.proyectomascotas.view.IRecyclerViewUsuarioLikeView;

import java.util.ArrayList;

public class MediaUsuarioLike extends AppCompatActivity implements IRecyclerViewUsuarioLikeView {

    private RecyclerView rvUsuarioLike;
    private IRecylerViewUsuarioLikePresenter iRecylerViewUsuarioLikePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_usuario_like);

        rvUsuarioLike = (RecyclerView)findViewById(R.id.rvUsuarioLike);

        Toolbar miActionBar = (Toolbar)findViewById(R.id.miActionBarB);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Se obtiene el id del usuario que dió like
        String idUsuarioLike = this.getIntent().getExtras().getString(ConstantesRestApi.USUARIO_LIKE);

        iRecylerViewUsuarioLikePresenter = new RecylerViewUsuarioLikePresenter(this,this,idUsuarioLike);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(MediaUsuarioLike.this,MainActivity.class);
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
        rvUsuarioLike.setLayoutManager(llm);

    }

    @Override
    public UsuarioLikeAdaptador crearAdaptador(ArrayList<MascotaPerfil> listaMedia) {
        UsuarioLikeAdaptador usuarioLikeAdaptador = new UsuarioLikeAdaptador(listaMedia, this);
        return usuarioLikeAdaptador;
    }

    @Override
    public void inicializarAdaptadorRV(UsuarioLikeAdaptador usuarioLikeAdaptador) {
        rvUsuarioLike.setAdapter(usuarioLikeAdaptador);

    }
}
