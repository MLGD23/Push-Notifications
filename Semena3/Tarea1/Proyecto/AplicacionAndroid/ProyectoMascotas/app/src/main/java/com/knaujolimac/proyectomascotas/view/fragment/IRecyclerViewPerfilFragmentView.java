package com.knaujolimac.proyectomascotas.view.fragment;

import com.knaujolimac.proyectomascotas.adapter.PerfilMascotaAdaptador;
import com.knaujolimac.proyectomascotas.pojo.Mascota;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;

import java.util.ArrayList;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public interface IRecyclerViewPerfilFragmentView {


    /**
     * Método encargado de generar el GridLayout
     */
    public void generarGridLayout();

    /**
     * Método encargado de crear el adaptador
     * @param listFotosPerfil
     * @return
     */
    public PerfilMascotaAdaptador crearAdaptador(ArrayList<MascotaPerfil> listFotosPerfil);

    /**
     * Método encargado de inicializar el adaptador
     * @param perfilAdaptador
     */
    public void inicializarAdaptadorRV(PerfilMascotaAdaptador perfilAdaptador);

    public void actualizarDatosPerfil(MascotaPerfil mascotaPerfil);
}
