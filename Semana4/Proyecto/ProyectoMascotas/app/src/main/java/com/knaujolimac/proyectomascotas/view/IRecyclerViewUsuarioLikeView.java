package com.knaujolimac.proyectomascotas.view;

import com.knaujolimac.proyectomascotas.adapter.UsuarioLikeAdaptador;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;

import java.util.ArrayList;

/**
 * Created by Camilo Chaparro on 3/11/2016.
 */

public interface IRecyclerViewUsuarioLikeView {

    /**
     * Método encargado de generar el LinearLayout de Manera Vertical
     */
    public void generarLinearLayoutVertical();

    /**
     * Método encargado de crear el adaptador
     * @param listaMedia
     * @return
     */
    public UsuarioLikeAdaptador crearAdaptador(ArrayList<MascotaPerfil> listaMedia);

    /**
     * Método encargado de inicializar el adaptador
     * @param usuarioLikeAdaptador
     */
    public void inicializarAdaptadorRV(UsuarioLikeAdaptador usuarioLikeAdaptador);
}
