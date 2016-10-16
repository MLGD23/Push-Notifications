package com.knaujolimac.proyectomascotas.presentador;

import android.content.Context;

import com.knaujolimac.proyectomascotas.view.IRecyclerViewMascotaFavoritaView;
import com.knaujolimac.proyectomascotas.pojo.ConstructorMascota;
import com.knaujolimac.proyectomascotas.pojo.Mascota;

import java.util.ArrayList;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public class RecylerViewMascotaFavoritaPresenter implements IRecylerViewMascotaFavoritaPresenter{

    private ArrayList<Mascota> listMascotasFavoritas;
    private IRecyclerViewMascotaFavoritaView iRecyclerViewMascotaFavoritaView;
    private Context context;

    public RecylerViewMascotaFavoritaPresenter(IRecyclerViewMascotaFavoritaView iRecyclerViewMascotaFavoritaView,Context context) {
        this.iRecyclerViewMascotaFavoritaView = iRecyclerViewMascotaFavoritaView;
        this.context = context;

        //Se obtiene el listado de mascotas favoritas
        this.obtenerMascotasFavoritas();

        //Se muestra el Recycler view
        this.mostrarRVMascotasFavoritas();
    }

    @Override
    public void obtenerMascotasFavoritas() {
        ConstructorMascota constructorMascota = new ConstructorMascota(context);
        listMascotasFavoritas = constructorMascota.obtenerMascotasLikes();
    }

    @Override
    public void mostrarRVMascotasFavoritas() {
        iRecyclerViewMascotaFavoritaView.generarLinearLayoutVertical();
        iRecyclerViewMascotaFavoritaView.inicializarAdaptadorRV(iRecyclerViewMascotaFavoritaView.crearAdaptador(listMascotasFavoritas));
    }
}
