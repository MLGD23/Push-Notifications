package com.knaujolimac.proyectomascotas.pojo;

/**
 * Created by Camilo Chaparro on 15/10/2016.
 */
public class MascotaPerfil {

    private UsuarioPerfil usuarioPerfil;
    private int cantidadLikes;
    private String urlFoto;

    public UsuarioPerfil getUsuarioPerfil() {
        return usuarioPerfil;
    }

    public void setUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
        this.usuarioPerfil = usuarioPerfil;
    }

    public int getCantidadLikes() {
        return cantidadLikes;
    }

    public void setCantidadLikes(int cantidadLikes) {
        this.cantidadLikes = cantidadLikes;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
