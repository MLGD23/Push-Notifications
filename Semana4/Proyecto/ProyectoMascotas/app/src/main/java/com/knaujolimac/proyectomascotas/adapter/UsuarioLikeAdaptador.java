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
 * Created by Camilo Chaparro on 3/11/2016.
 */

public class UsuarioLikeAdaptador  extends RecyclerView.Adapter<UsuarioLikeAdaptador.UsuarioLikeViewHolder>{

    private ArrayList<MascotaPerfil> listaMedia;
    private Activity activity;

    public UsuarioLikeAdaptador(ArrayList<MascotaPerfil> listaMedia, Activity activity) {
        this.listaMedia = listaMedia;
        this.activity = activity;
    }

    @Override
    public UsuarioLikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Le genera vida al Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_usuario_like,parent,false);
        return new UsuarioLikeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UsuarioLikeViewHolder holder, int position) {

        final MascotaPerfil mascota = listaMedia.get(position);

        //Se setean los valores en el holder
        Picasso.with(activity)
                .load(mascota.getUrlFoto())
                .placeholder(R.drawable.mascotaperfilb)
                .into(holder.imgFotoUsuarioLike);
        holder.tvNombreUsuarioLikeCv.setText(mascota.getUsuarioPerfil().getNombreUsuario());
        holder.tvCantLUsuarioLikeCv.setText(String.valueOf(mascota.getCantidadLikes()));

    }

    @Override
    public int getItemCount() {
        return listaMedia.size();
    }


    public static class UsuarioLikeViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFotoUsuarioLike;
        private TextView tvNombreUsuarioLikeCv;
        private TextView tvCantLUsuarioLikeCv;

        public UsuarioLikeViewHolder (View itemView) {
            super(itemView);
            imgFotoUsuarioLike = (ImageView)itemView.findViewById(R.id.imgFotoUsuarioLike);
            tvNombreUsuarioLikeCv = (TextView)itemView.findViewById(R.id.tvNombreUsuarioLikeCv);
            tvCantLUsuarioLikeCv = (TextView)itemView.findViewById(R.id.tvCantLUsuarioLikeCv);
        }
    }
}
