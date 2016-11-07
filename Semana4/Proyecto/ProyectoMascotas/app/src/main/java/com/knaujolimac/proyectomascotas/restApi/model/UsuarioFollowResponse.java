package com.knaujolimac.proyectomascotas.restApi.model;

/**
 * Created by Camilo Chaparro on 3/11/2016.
 */

public class UsuarioFollowResponse {

    private String outGoingStatus;
    private boolean userPrivate;
    private String incomingStatus;

    public String getOutGoingStatus() {
        return outGoingStatus;
    }

    public void setOutGoingStatus(String outGoingStatus) {
        this.outGoingStatus = outGoingStatus;
    }

    public boolean isUserPrivate() {
        return userPrivate;
    }

    public void setUserPrivate(boolean userPrivate) {
        this.userPrivate = userPrivate;
    }

    public String getIncomingStatus() {
        return incomingStatus;
    }

    public void setIncomingStatus(String incomingStatus) {
        this.incomingStatus = incomingStatus;
    }
}
