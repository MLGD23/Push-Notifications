package com.knaujolimac.proyectomascotas.presentador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.knaujolimac.proyectomascotas.pojo.Mascota;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.knaujolimac.proyectomascotas.pojo.UsuarioPerfil;
import com.knaujolimac.proyectomascotas.restApi.ConstantesRestApi;
import com.knaujolimac.proyectomascotas.restApi.EndpointsApi;
import com.knaujolimac.proyectomascotas.restApi.adapter.RestApiAdapter;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;
import com.knaujolimac.proyectomascotas.view.IRecyclerViewUsuarioLikeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Camilo Chaparro on 3/11/2016.
 */

public class RecylerViewUsuarioLikePresenter implements IRecylerViewUsuarioLikePresenter {

    private ArrayList<MascotaPerfil> listMediaMascotas;
    private IRecyclerViewUsuarioLikeView iRecyclerViewUsuarioLikeView;
    private Context context;
    private String nombreUsuario;

    public RecylerViewUsuarioLikePresenter(IRecyclerViewUsuarioLikeView iRecyclerViewUsuarioLikeView, Context context,String nombreUsuario) {
        this.iRecyclerViewUsuarioLikeView = iRecyclerViewUsuarioLikeView;
        this.context = context;
        this.nombreUsuario = nombreUsuario;
        //Se obtiene la media del usuario que dio like a una de nuestras fotos
        this.obtenerMediaUsuarioLike();

    }

    @Override
    public void obtenerMediaUsuarioLike() {
        listMediaMascotas = new ArrayList<MascotaPerfil>();
        obtenerMediaUsuarioByNomUsu(nombreUsuario);
    }

    /**
     * Método encargado de obtener la média del usuario mediante el nombre de usuario
     * @param nombreUsuario
     */
    private void obtenerMediaUsuarioByNomUsu(final String nombreUsuario){

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorSearchUser();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);


        Map<String, String> map = new HashMap<>();
        map.put(ConstantesRestApi.KEY_GET_USERS_SEARCH_FILTER, nombreUsuario);
        map.put(ConstantesRestApi.KEY_ACCESS_TOKEN, ConstantesRestApi.ACCESS_TOKEN);
        Call<MascotaResponse> mascotaResponseCall = endpointsApi.getUsersSearch(map);

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                ArrayList<MascotaPerfil> listaUsuarios = mascotaResponse.getMascotas();

                if(listaUsuarios==null||listaUsuarios.isEmpty()){
                    Log.e("BUSCAR_USUARIO","No se encontraron datos para el usuario "+nombreUsuario);
                }
                else{
                    UsuarioPerfil usuarioPerfil = listaUsuarios.get(0).getUsuarioPerfil();

                    if(nombreUsuario.equalsIgnoreCase(usuarioPerfil.getNombreUsuario())){

                        //Modificar la relacion de los usuarios
                        servicioObtenerMediaUsuarioLkie(usuarioPerfil.getId());
                    }
                    else{
                        Log.e("BUSCAR_USUARIO","No se encontraron datos para el usuario "+nombreUsuario);
                    }
                }
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Ocurrió un error al obtener las fotos del perfil del usuario", Toast.LENGTH_LONG).show();
                Log.e("Error en la conexión", t.toString());
            }
        });
    }

    /**
     * Método encargado de obtener la media del usuario que dió like a una de nuestras
     * fotod
     * @param idUsuario
     */
    private void servicioObtenerMediaUsuarioLkie(String idUsuario) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorMediaRecent();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);
        Call<MascotaResponse> mascotaResponseCall = endpointsApi.getRecentMediaIdUsuario(idUsuario);

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                listMediaMascotas =mascotaResponse.getMascotas();
                mostrarRVMedisUsuarioLike();
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Ocurrió un error al obtener las fotos del perfil del usuario que dio like", Toast.LENGTH_LONG).show();
                Log.e("Error en la conexión", t.toString());
            }
        });
    }

    @Override
    public void mostrarRVMedisUsuarioLike() {
        iRecyclerViewUsuarioLikeView.generarLinearLayoutVertical();
        iRecyclerViewUsuarioLikeView.inicializarAdaptadorRV(iRecyclerViewUsuarioLikeView.crearAdaptador(listMediaMascotas));

    }
}
