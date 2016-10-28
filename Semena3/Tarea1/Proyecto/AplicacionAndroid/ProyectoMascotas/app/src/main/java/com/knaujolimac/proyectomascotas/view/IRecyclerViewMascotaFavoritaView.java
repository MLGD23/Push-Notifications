package com.knaujolimac.proyectomascotas.view;

import com.knaujolimac.proyectomascotas.adapter.MascotaFavoritoAdaptador;
import com.knaujolimac.proyectomascotas.pojo.Mascota;

import java.util.ArrayList;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public interface IRecyclerViewMascotaFavoritaView {

    /**
     * Método encargado de generar el LinearLayout de Manera Vertical
     */
    public void generarLinearLayoutVertical();

    /**
     * Método encargado de crear el adaptador
     * @param mascotas
     * @return
     */
    public MascotaFavoritoAdaptador crearAdaptador(ArrayList<Mascota> mascotas);

    /**
     * Método encargado de inicializar el adaptador
     * @param mascotaAdaptador
     */
    public void inicializarAdaptadorRV(MascotaFavoritoAdaptador mascotaAdaptador);
}
