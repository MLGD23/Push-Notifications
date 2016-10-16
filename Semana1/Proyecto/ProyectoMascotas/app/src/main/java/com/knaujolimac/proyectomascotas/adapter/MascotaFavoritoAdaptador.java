package com.knaujolimac.proyectomascotas.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.knaujolimac.proyectomascotas.R;
import com.knaujolimac.proyectomascotas.pojo.Mascota;

import java.util.ArrayList;

/**
 * Created by Juan Camilo Chaparro on 10/07/2016.
 */
public class MascotaFavoritoAdaptador extends RecyclerView.Adapter<MascotaFavoritoAdaptador.MascotaFavoritoViewHolder>{

    private ArrayList<Mascota> mascotasFavoritas;
    private Activity activity;

    public MascotaFavoritoAdaptador(ArrayList<Mascota> mascotasFavoritas,Activity activity){
        this.mascotasFavoritas = mascotasFavoritas;
        this.activity = activity;
    }


    @Override
    public MascotaFavoritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Le genera vida al Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota_favorita,parent,false);
        return new MascotaFavoritoViewHolder(v);
    }

    /**
     * Cada vez quese recorre la lista de mascotas
     * @param mascotaFavoritoViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MascotaFavoritoViewHolder mascotaFavoritoViewHolder, int position) {
        final Mascota mascota = mascotasFavoritas.get(position);

        //Se setean los valores en el holder
        mascotaFavoritoViewHolder.imgFotoFavorito.setImageResource(mascota.getFoto());
        mascotaFavoritoViewHolder.tvNombreMFavCv.setText(mascota.getNombreMascota());
        mascotaFavoritoViewHolder.tvCantLMFavCv.setText(String.valueOf(mascota.getCantidadLikes()));
    }

    @Override
    public int getItemCount() {
        return mascotasFavoritas.size();
    }

    public static class MascotaFavoritoViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFotoFavorito;
        private TextView tvNombreMFavCv;
        private TextView tvCantLMFavCv;

        public MascotaFavoritoViewHolder (View itemView) {
            super(itemView);
            imgFotoFavorito = (ImageView)itemView.findViewById(R.id.imgFotoFavorito);
            tvNombreMFavCv = (TextView)itemView.findViewById(R.id.tvNombreMFavCv);
            tvCantLMFavCv = (TextView)itemView.findViewById(R.id.tvCantLMFavCv);
        }
    }

}
