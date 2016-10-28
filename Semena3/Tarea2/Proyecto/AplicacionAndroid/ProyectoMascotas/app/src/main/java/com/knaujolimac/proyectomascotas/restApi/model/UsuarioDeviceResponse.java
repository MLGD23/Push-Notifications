package com.knaujolimac.proyectomascotas.restApi.model;

/**
 * Created by Camilo Chaparro on 23/10/2016.
 */

public class UsuarioDeviceResponse {

    private String id;
    private String idDispositivo;
    private String idUsuarioInstagram;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getIdUsuarioInstagram() {
        return idUsuarioInstagram;
    }

    public void setIdUsuarioInstagram(String idUsuarioInstagram) {
        this.idUsuarioInstagram = idUsuarioInstagram;
    }
}
