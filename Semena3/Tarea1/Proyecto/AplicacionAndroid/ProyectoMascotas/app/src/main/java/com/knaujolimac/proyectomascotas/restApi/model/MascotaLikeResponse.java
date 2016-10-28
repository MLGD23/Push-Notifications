package com.knaujolimac.proyectomascotas.restApi.model;

/**
 * Created by Camilo Chaparro on 26/10/2016.
 */

public class MascotaLikeResponse {

    private String idUsuarioNotificado;
    private boolean notificacionProcesada;

    public String getIdUsuarioNotificado() {
        return idUsuarioNotificado;
    }

    public void setIdUsuarioNotificado(String idUsuarioNotificado) {
        this.idUsuarioNotificado = idUsuarioNotificado;
    }

    public boolean isNotificacionProcesada() {
        return notificacionProcesada;
    }

    public void setNotificacionProcesada(boolean notificacionProcesada) {
        this.notificacionProcesada = notificacionProcesada;
    }
}
