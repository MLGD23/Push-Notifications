package com.knaujolimac.proyectomascotas.servicios;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.knaujolimac.proyectomascotas.MainActivity;
import com.knaujolimac.proyectomascotas.MediaUsuarioLike;
import com.knaujolimac.proyectomascotas.pojo.ConstructorMascota;
import com.knaujolimac.proyectomascotas.restApi.ConstantesRestApi;

import static android.app.SearchManager.ACTION_KEY;

/**
 * Created by Camilo Chaparro on 6/11/2016.
 */

public class NotificacionReceiver  extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        //Se obtiene el tipo de accion
        String accion = intent.getAction();
        //Se obtiene el id del usuario que dió like
        String idUsuario = intent.getExtras().getString(ConstantesServicios.NOTIFICACION_USUARIO);

        switch (accion){
            case ConstantesServicios.ACTION_NOT_MI_PERFIL:
                this.verMediaHome();
                break;
            case ConstantesServicios.ACTION_NOT_FOLLOW_UNFOLLOW:
                this.followUnFollowUsuario(idUsuario);
                break;
            case ConstantesServicios.ACTION_NOT_PERFIL_USUARIO:
                this.verPerfilUsuarioLike(idUsuario);
                break;
        }
    }

    /**
     * Método encargado de mostar el home del usuario configurado en la aplicación
     */
    private void verMediaHome(){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    /**
     * Método encargado de mostrar la media del usuario que dió like
     */
    private void verPerfilUsuarioLike(String usuario){
        Intent intent = new Intent(context,MediaUsuarioLike.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(ConstantesRestApi.USUARIO_LIKE,usuario);
        context.startActivity(intent);
    }

    /**
     * Método encargado de seguir o no seguir el usuario de entrada en instagrama
     * @param usuario
     */
    private void followUnFollowUsuario(String usuario){
        ConstructorMascota constructorMascota = new ConstructorMascota(context);
        constructorMascota.modificarRelacionShipByNomUsu(usuario);
    }
}
