package com.knaujolimac.proyectomascotas.pojo;

import android.content.Context;


import com.knaujolimac.proyectomascotas.R;
import com.knaujolimac.proyectomascotas.db.BaseDatos;

import java.util.ArrayList;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public class ConstructorPerfilMascota {

    private Context context;

    public ConstructorPerfilMascota(Context context) {
        this.context = context;
    }

    /**
     * Método encargado de obtener las fotos de perfil
     * @return
     */
    public ArrayList<Mascota> obtenerFotosPerfilMascota(){
        ArrayList<Mascota> listFotosPerfil = new ArrayList<Mascota>();
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 15));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 20));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 30));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 7));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 4));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 3));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 2));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 7));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 6));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 15));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 20));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 30));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 15));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 20));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 30));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 7));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 4));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 3));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 2));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 7));

        return listFotosPerfil;
    }


    /**
     * Método encargado de obtener las mascotas con mas likes
     * @return
     */
    public UsuarioPerfil obtenerUsuarioPerfil(){
        UsuarioPerfil usuarioPerfil = null;
        BaseDatos db = new BaseDatos(context);

        usuarioPerfil = db.obtenerUsuarioPerfil();

        return usuarioPerfil;
    }

    /**
     * Método encargado de actualizar la información del perfil
     * @param nuevoPerfil
     * @return
     */
    public boolean modificarUsuarioPerfil(UsuarioPerfil nuevoPerfil){
        BaseDatos db = new BaseDatos(context);
        return db.modificarUsuarioPerfil(nuevoPerfil);
    }

}
