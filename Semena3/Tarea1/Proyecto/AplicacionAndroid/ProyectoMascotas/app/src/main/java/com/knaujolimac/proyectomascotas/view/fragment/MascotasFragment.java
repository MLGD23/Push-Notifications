package com.knaujolimac.proyectomascotas.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knaujolimac.proyectomascotas.R;
import com.knaujolimac.proyectomascotas.pojo.Mascota;
import com.knaujolimac.proyectomascotas.adapter.MascotaAdaptador;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.knaujolimac.proyectomascotas.presentador.IRecylerViewMascotaFragmentPresenter;
import com.knaujolimac.proyectomascotas.presentador.RecylerViewMascotaFragmentPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MascotasFragment extends Fragment implements IRecyclerViewMascotaFragmentView {

    private IRecylerViewMascotaFragmentPresenter iRecylerViewMascotaFragmentPresenter;
    private RecyclerView rvMascotas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mascotas,container,false);

        rvMascotas = (RecyclerView) view.findViewById(R.id.rvMascotas);
        iRecylerViewMascotaFragmentPresenter = new RecylerViewMascotaFragmentPresenter(this,this.getContext());
        return view;

    }

    @Override
    public void generarLinearLayoutVertical() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMascotas.setLayoutManager(llm);
    }

    @Override
    public MascotaAdaptador crearAdaptador(ArrayList<MascotaPerfil> listMascotas) {
        MascotaAdaptador mascotaAdaptador = new MascotaAdaptador(listMascotas, getActivity(),this);
        return mascotaAdaptador;
    }

    @Override
    public void inicializarAdaptadorRV(MascotaAdaptador mascotaAdaptador) {
        rvMascotas.setAdapter(mascotaAdaptador);
    }
}
