package com.knaujolimac.proyectomascotas.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.knaujolimac.proyectomascotas.R;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Juan Camilo Chaparro on 17/07/2016.
 */
public class PerfilMascotaAdaptador extends RecyclerView.Adapter<PerfilMascotaAdaptador.FotoPerfilViewHolder> {

    private ArrayList<MascotaPerfil> fotosPerfil;
    private Activity activity;

    public PerfilMascotaAdaptador(ArrayList<MascotaPerfil> fotosPerfil,Activity activity){
        this.fotosPerfil = fotosPerfil;
        this.activity = activity;
    }


    @Override
    public FotoPerfilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Le genera vida al Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_foto_perfil,parent,false);
        return new FotoPerfilViewHolder(v);
    }

    /**
     * Cada vez quese recorre la lista de mascotas
     * @param fotoPerfilViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(FotoPerfilViewHolder fotoPerfilViewHolder, int position) {
        final MascotaPerfil mascota = fotosPerfil.get(position);

        //Se setean los valores en el holder
       // fotoPerfilViewHolder.imgFotoPerfil.setImageResource(mascota.getUrlFoto());
        Picasso.with(activity)
                .load(mascota.getUrlFoto())
                .placeholder(R.drawable.mascotaperfilb)
                .into(fotoPerfilViewHolder.imgFotoPerfil);
        fotoPerfilViewHolder.tvCantFotoPer.setText(String.valueOf(mascota.getCantidadLikes()));

    }

    @Override
    public int getItemCount() {
        return fotosPerfil.size();
    }

    public static class FotoPerfilViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFotoPerfil;
        private TextView tvCantFotoPer;

        public FotoPerfilViewHolder (View itemView) {
            super(itemView);
            imgFotoPerfil = (ImageView)itemView.findViewById(R.id.imgFotoPerfil);
            tvCantFotoPer = (TextView)itemView.findViewById(R.id.tvCantFotoPer);
        }
    }

}
