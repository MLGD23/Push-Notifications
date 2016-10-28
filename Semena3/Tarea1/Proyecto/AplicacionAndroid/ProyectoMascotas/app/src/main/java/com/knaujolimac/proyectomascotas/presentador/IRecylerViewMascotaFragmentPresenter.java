package com.knaujolimac.proyectomascotas.presentador;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public interface IRecylerViewMascotaFragmentPresenter {

    /**
     * Método encargado de obtener las mascotas
     */
    public void obtenerMascotas();

    /**
     * Método encargado de mostrar las mascotas en el Recycler View
     */
    public void mostrarRVMascotas();
}
