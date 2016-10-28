package com.knaujolimac.proyectomascotas.pojo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;
import com.knaujolimac.proyectomascotas.R;
import com.knaujolimac.proyectomascotas.db.BaseDatos;
import com.knaujolimac.proyectomascotas.restApi.EndpointsApi;
import com.knaujolimac.proyectomascotas.restApi.adapter.RestApiAdapter;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioDeviceResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public class ConstructorPerfilMascota {

    private Context context;
    private boolean resultadoOperacion;

    public ConstructorPerfilMascota(Context context) {
        this.context = context;
    }

    /**
     * Método encargado de obtener las fotos de perfil
     * @return
     */
    public ArrayList<Mascota> obtenerFotosPerfilMascota(){
        ArrayList<Mascota> listFotosPerfil = new ArrayList<Mascota>();
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 15));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 20));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 30));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 7));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 4));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 3));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 2));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 7));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 6));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 15));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 20));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 30));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 15));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 20));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 30));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 7));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 4));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 3));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 2));
        listFotosPerfil.add(new Mascota(R.drawable.mascotaperfilb, 7));

        return listFotosPerfil;
    }


    /**
     * Método encargado de obtener las mascotas con mas likes
     * @return
     */
    public UsuarioPerfil obtenerUsuarioPerfil(){
        UsuarioPerfil usuarioPerfil = null;
        BaseDatos db = new BaseDatos(context);

        usuarioPerfil = db.obtenerUsuarioPerfil();

        return usuarioPerfil;
    }

    /**
     * Método encargado de actualizar la información del perfil
     * @param nuevoPerfil
     * @return
     */
    public boolean modificarUsuarioPerfil(UsuarioPerfil nuevoPerfil){
        BaseDatos db = new BaseDatos(context);
        return db.modificarUsuarioPerfil(nuevoPerfil);
    }


    /**
     * Método encargado de registrar el device en el servidor
     */
    public void registrarDispositivoServidor() {

        //Se obtiene la información del usuario de instagram configurado
        UsuarioPerfil usuarioPerfil= obtenerUsuarioPerfil();


        //Se obtiene el token del dispositivo
        String token = FirebaseInstanceId.getInstance().getToken();


        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndpointsApi endponits = restApiAdapter.establecerConexionServidorMascotas();
        Call<UsuarioDeviceResponse> usuarioDeviceResponseCall = endponits.registrarUsuarioDispositivo(token,usuarioPerfil.getNombreUsuario());

        usuarioDeviceResponseCall.enqueue(new Callback<UsuarioDeviceResponse>() {
            @Override
            public void onResponse(Call<UsuarioDeviceResponse> call, Response<UsuarioDeviceResponse> response) {
                UsuarioDeviceResponse usuarioDeviceResponse = response.body();
                Log.d("ID_FIREBASE", usuarioDeviceResponse.getId());
                Log.d("IDDISPOSITIVO_FIREBASE", usuarioDeviceResponse.getIdDispositivo());
                Log.d("IDUSU_INSTA_FIREBASE", usuarioDeviceResponse.getIdUsuarioInstagram());
                Toast.makeText(context,"Se registro el dispositivo correctamente.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UsuarioDeviceResponse> call, Throwable t) {
                Toast.makeText(context,"Ocurrió un error en la conexión.",Toast.LENGTH_SHORT).show();
                Log.e("REGISTRAR_USUARIO", "Ocurrio un error al registrar el usuario. " + t.toString());
            }
        });
    }

}
