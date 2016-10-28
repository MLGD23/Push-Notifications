package com.knaujolimac.proyectomascotas.presentador;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public interface IRecylerViewPerfilFragmentPresenter {

    /**
     * Método encargado de obtener las fotos del perifl mascota
     */
    public void obtenerFotosPerfilMascota();

    /**
     * Método encargado de mostar las fotos del perfil en el RecycleView
     */
    public void mostrarPerfilRVMascotas();
}
