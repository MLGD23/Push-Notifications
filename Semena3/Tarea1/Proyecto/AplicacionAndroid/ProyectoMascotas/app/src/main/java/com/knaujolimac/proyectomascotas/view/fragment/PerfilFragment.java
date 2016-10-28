package com.knaujolimac.proyectomascotas.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.knaujolimac.proyectomascotas.R;
import com.knaujolimac.proyectomascotas.adapter.PerfilMascotaAdaptador;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.knaujolimac.proyectomascotas.presentador.IRecylerViewPerfilFragmentPresenter;
import com.knaujolimac.proyectomascotas.presentador.RecylerViewPerfilFragmentPresenter;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PerfilFragment extends Fragment implements IRecyclerViewPerfilFragmentView {


    private RecyclerView rvMiPerfil;
    private CircularImageView cvImagenPerfil;
    private TextView idNombrePefil;
    private IRecylerViewPerfilFragmentPresenter iRecylerViewPerfilFragmentPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil,container,false);
        rvMiPerfil = (RecyclerView) view.findViewById(R.id.rvMiPerfil);
        cvImagenPerfil = (CircularImageView) view.findViewById(R.id.cvImagenPerfil);
        idNombrePefil =(TextView) view.findViewById(R.id.idNombrePefil);
        iRecylerViewPerfilFragmentPresenter = new RecylerViewPerfilFragmentPresenter(this,getContext());

        return view;
    }


    @Override
    public void generarGridLayout() {
        GridLayoutManager glm = new GridLayoutManager(getActivity(),3);
        rvMiPerfil.setLayoutManager(glm);
    }

    @Override
    public PerfilMascotaAdaptador crearAdaptador(ArrayList<MascotaPerfil> listFotos) {
        PerfilMascotaAdaptador perfilMascotaAdaptador = new PerfilMascotaAdaptador(listFotos, getActivity());
        return perfilMascotaAdaptador;
    }

    @Override
    public void inicializarAdaptadorRV(PerfilMascotaAdaptador perfilAdaptador) {
        rvMiPerfil.setAdapter(perfilAdaptador);
    }

    @Override
    public void actualizarDatosPerfil(MascotaPerfil mascotaPerfil) {
        //Se setea la imagen del perfil
        Picasso.with(getActivity())
                .load(mascotaPerfil.getUsuarioPerfil().getUrlFotoProfile())
                .placeholder(R.drawable.mascotaperfilb)
                .into(cvImagenPerfil);

        //se setea el nombre del usurio del perfil
        idNombrePefil.setText(mascotaPerfil.getUsuarioPerfil().getFullName());
    }
}
