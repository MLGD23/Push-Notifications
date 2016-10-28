package com.knaujolimac.proyectomascotas.view.fragment;

import com.knaujolimac.proyectomascotas.adapter.MascotaAdaptador;
import com.knaujolimac.proyectomascotas.pojo.Mascota;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;

import java.util.ArrayList;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public interface IRecyclerViewMascotaFragmentView {

    /**
     * Método encargado de generar el LinearLayout de Manera Vertical
     */
    public void generarLinearLayoutVertical();

    /**
     * Método encargado de crear el adaptador
     * @param mascotas
     * @return
     */
    public MascotaAdaptador crearAdaptador(ArrayList<MascotaPerfil> mascotas);

    /**
     * Método encargado de inicializar el adaptador
     * @param mascotaAdaptador
     */
    public void inicializarAdaptadorRV(MascotaAdaptador mascotaAdaptador);

}
