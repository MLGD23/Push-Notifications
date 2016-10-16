package com.knaujolimac.proyectomascotas.presentador;

import android.content.Context;

import com.knaujolimac.proyectomascotas.view.fragment.IRecyclerViewMascotaFragmentView;
import com.knaujolimac.proyectomascotas.pojo.ConstructorMascota;
import com.knaujolimac.proyectomascotas.pojo.Mascota;

import java.util.ArrayList;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public class RecylerViewMascotaFragmentPresenter implements IRecylerViewMascotaFragmentPresenter {


    private IRecyclerViewMascotaFragmentView iRecyclerViewMascotaFragmentView;
    private Context context;
    private ArrayList<Mascota> listMascotas;
    private ConstructorMascota constructorMascota;


    public RecylerViewMascotaFragmentPresenter(IRecyclerViewMascotaFragmentView iRecyclerViewMascotaFragmentView, Context context) {
        this.iRecyclerViewMascotaFragmentView = iRecyclerViewMascotaFragmentView;
        this.context = context;
        //Se obtienen las mascotas
        this.obtenerMascotas();
        //Se visualizan las mascotas
        this.mostrarRVMascotas();
    }

    @Override
    public void obtenerMascotas() {
        constructorMascota = new ConstructorMascota(this.context);
        listMascotas = constructorMascota.obtenerMascotas();
    }

    @Override
    public void mostrarRVMascotas() {
        iRecyclerViewMascotaFragmentView.inicializarAdaptadorRV(iRecyclerViewMascotaFragmentView.crearAdaptador(listMascotas));
        iRecyclerViewMascotaFragmentView.generarLinearLayoutVertical();
    }
}
