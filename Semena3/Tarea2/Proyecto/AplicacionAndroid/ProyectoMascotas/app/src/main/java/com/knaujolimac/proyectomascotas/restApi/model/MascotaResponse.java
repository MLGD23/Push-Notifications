package com.knaujolimac.proyectomascotas.restApi.model;

import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;

import java.util.ArrayList;

/**
 * Created by Camilo Chaparro on 15/10/2016.
 */
public class MascotaResponse {

    private ArrayList<MascotaPerfil> mascotas;

    public ArrayList<MascotaPerfil> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<MascotaPerfil> mascotas) {
        this.mascotas = mascotas;
    }
}
