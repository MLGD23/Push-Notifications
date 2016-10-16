package com.knaujolimac.proyectomascotas.pojo;

import android.content.ContentValues;
import android.content.Context;


import com.knaujolimac.proyectomascotas.R;
import com.knaujolimac.proyectomascotas.db.BaseDatos;
import com.knaujolimac.proyectomascotas.db.ConstantesBaseDatos;

import java.util.ArrayList;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public class ConstructorMascota {

    private Context context;
    private static final int CANTIDAD_MASCOTAS_LIKES = 5;

    public ConstructorMascota(Context context) {
        this.context = context;
    }

    public ArrayList<Mascota> obtenerMascotas(){
        /* Se realiza la creacón de mascotas dummy */
        ArrayList<Mascota> listMascotas = new ArrayList<Mascota>();
        int indiceMascota = 1;
        listMascotas.add(new Mascota(indiceMascota++, R.drawable.perroa, "Ander"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.perrob, "Barry"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.perroc, "Bruno"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.perrod, "Chispita"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.perroe, "Chispi"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.perrof, "Dardo"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.perrog, "Deus"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.perroh, "Darwin"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.gatoa, "Diosa"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.gatob, "Dinamita"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.gatoc, "Cash"));
        listMascotas.add(new Mascota(indiceMascota++,R.drawable.gatod, "Cornelius"));

        //Se actualiza la cantidad de likes de las mascotas
        listMascotas = actualizarLikesMascota(listMascotas);


        return listMascotas;
    }

    /**
     * Método encargado de registrar la mascota cuando recibe un like
     * @param mascota
     */
    public void darLikeMascota(Mascota mascota){
        BaseDatos db = new BaseDatos(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_ID, mascota.getIdMascota());
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE_MASCOTA, mascota.getNombreMascota());
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_ID_FOTO, mascota.getFoto());
        db.insertarLikesMascota(contentValues);
    }

    /**
     * Método encargado de obtener las mascotas con mas likes
     * @return
     */
    public ArrayList<Mascota> obtenerMascotasLikes(){
        ArrayList<Mascota> mascotasLikes = null;
        BaseDatos db = new BaseDatos(context);

        mascotasLikes = db.obtenerMascotasRating(this.CANTIDAD_MASCOTAS_LIKES,true);
        return mascotasLikes;
    }

    /**
     * Método encargado de actualizar la cantidad de likes
     * @param mascotas
     * @return
     */
    public ArrayList<Mascota> actualizarLikesMascota(ArrayList<Mascota> mascotas){
        ArrayList<Mascota> mascotasLikes = null;
        BaseDatos db = new BaseDatos(context);

        mascotasLikes = db.obtenerMascotasRating(this.CANTIDAD_MASCOTAS_LIKES,false);

        /*Se actualiza la cantidad de likes*/
        for(Mascota mascota :mascotas){
            for(Mascota mLike :mascotasLikes){
                if(mascota.getIdMascota()==mLike.getIdMascota()){
                    mascota.setCantidadLikes(mLike.getCantidadLikes());
                }
            }
        }

        return mascotas;
    }


}
