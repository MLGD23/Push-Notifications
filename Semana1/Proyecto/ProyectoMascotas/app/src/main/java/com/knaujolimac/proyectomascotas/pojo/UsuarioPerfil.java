package com.knaujolimac.proyectomascotas.pojo;

/**
 * Created by Camilo Chaparro on 15/10/2016.
 */
public class UsuarioPerfil {
    private String id;
    private String nombreUsuario;
    private String fullName;
    private String urlFotoProfile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrlFotoProfile() {
        return urlFotoProfile;
    }

    public void setUrlFotoProfile(String urlFotoProfile) {
        this.urlFotoProfile = urlFotoProfile;
    }
}
