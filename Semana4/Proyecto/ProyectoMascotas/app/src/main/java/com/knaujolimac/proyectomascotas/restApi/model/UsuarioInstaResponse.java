package com.knaujolimac.proyectomascotas.restApi.model;

import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.knaujolimac.proyectomascotas.pojo.UsuarioPerfil;

import java.util.ArrayList;

/**
 * Created by Camilo Chaparro on 24/10/2016.
 */

public class UsuarioInstaResponse {

    private ArrayList<UsuarioPerfil> usuarios;

    public ArrayList<UsuarioPerfil> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<UsuarioPerfil> usuarios) {
        this.usuarios = usuarios;
    }
}
