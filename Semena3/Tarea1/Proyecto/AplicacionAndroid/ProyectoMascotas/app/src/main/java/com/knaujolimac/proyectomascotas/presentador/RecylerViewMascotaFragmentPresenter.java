package com.knaujolimac.proyectomascotas.presentador;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.knaujolimac.proyectomascotas.pojo.ConstructorPerfilMascota;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.knaujolimac.proyectomascotas.pojo.UsuarioPerfil;
import com.knaujolimac.proyectomascotas.restApi.EndpointsApi;
import com.knaujolimac.proyectomascotas.restApi.adapter.RestApiAdapter;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioInstaResponse;
import com.knaujolimac.proyectomascotas.view.fragment.IRecyclerViewMascotaFragmentView;
import com.knaujolimac.proyectomascotas.pojo.ConstructorMascota;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public class RecylerViewMascotaFragmentPresenter implements IRecylerViewMascotaFragmentPresenter {


    private IRecyclerViewMascotaFragmentView iRecyclerViewMascotaFragmentView;
    private Context context;
    private ArrayList<MascotaPerfil> listMascotas;
    private ConstructorMascota constructorMascota;
    private List<UsuarioPerfil> usuariosPerfil;
    private int consecUsuario;
    private ProgressDialog ringProgressDialog;


    public RecylerViewMascotaFragmentPresenter(IRecyclerViewMascotaFragmentView iRecyclerViewMascotaFragmentView, Context context) {
        this.iRecyclerViewMascotaFragmentView = iRecyclerViewMascotaFragmentView;
        this.context = context;
        //Se obtienen las mascotas
        this.obtenerMascotas();

    }

    @Override
    public void obtenerMascotas() {
        /**constructorMascota = new ConstructorMascota(this.context);
        constructorMascota.obtenerUsuariosMediaReciente();
        listMascotas = constructorMascota.getListMascotas();*/
        /**
         * Se obtiene la lista de usuarios seguidos en instagram
         */
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorUsuariosFollows();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);
        Call<UsuarioInstaResponse> usuarioInstaResponseCall = endpointsApi.getUsuariosFollows();

        usuarioInstaResponseCall.enqueue(new Callback<UsuarioInstaResponse>() {
            @Override
            public void onResponse(Call<UsuarioInstaResponse> call, Response<UsuarioInstaResponse> response) {
                UsuarioInstaResponse usuarioInstaResponse = response.body();
                usuariosPerfil = usuarioInstaResponse.getUsuarios();
                if(usuariosPerfil!=null&&usuariosPerfil.size()>0){
                    ringProgressDialog = ProgressDialog.show(context, "Por favor espere!",  "Cargando información...", false);
                    ringProgressDialog.setCancelable(false);

                }
                obtenerMediaUsuarios();
            }

            @Override
            public void onFailure(Call<UsuarioInstaResponse> call, Throwable t) {
                Toast.makeText(context, "Ocurrió un error al obtener los usuarios seguidos", Toast.LENGTH_LONG).show();
                Log.e("Error en la conexión", t.toString());

            }
        });
    }

    /**
     * Método encargado de obtener la media reciente de los usuarios seguidos
     */
    public void obtenerMediaUsuarios() {

        if(usuariosPerfil!=null&&!usuariosPerfil.isEmpty()){
            RestApiAdapter restApiAdapter = new RestApiAdapter();
            Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorMediaRecent();
            EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);

            Call<MascotaResponse> mascotaResponseCall = null;
            //Se iteran los usuarios para obtener las fotos del perfil
            consecUsuario = 1;
            for(final UsuarioPerfil usuario :  usuariosPerfil){

                listMascotas = new ArrayList<MascotaPerfil>();
                mascotaResponseCall = endpointsApi.getRecentMediaIdUsuario(usuario.getId());

                mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
                    @Override
                    public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                        MascotaResponse mascotaResponse = response.body();
                        listMascotas.addAll(mascotaResponse.getMascotas());

                        //Se visualizan las mascotas
                        if(consecUsuario==usuariosPerfil.size()){
                            obtenerMediaUsuarioPrincipal();
                        }
                        aumentarContador();

                    }

                    @Override
                    public void onFailure(Call<MascotaResponse> call, Throwable t) {
                        Toast.makeText(context, "Ocurrió un error al obtener las fotos del perfil del usuario", Toast.LENGTH_LONG).show();
                        Log.e("Error en la conexión", t.toString());
                    }
                });


            }
        }
    }

    /**
     * Método encargado de obtener la media reciente del usuario principal
     */
    private void obtenerMediaUsuarioPrincipal() {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorMediaRecent();
        ;
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);
        Call<MascotaResponse> mascotaResponseCall = endpointsApi.getRecentMedia();

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                listMascotas.addAll(mascotaResponse.getMascotas());
                ringProgressDialog.dismiss();
                mostrarRVMascotas();
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Ocurrió un error al obtener las fotos del perfil del usuario", Toast.LENGTH_LONG).show();
                Log.e("Error en la conexión", t.toString());
            }
        });
    }

    public void aumentarContador(){
        consecUsuario++;
    }

    @Override
    public void mostrarRVMascotas() {
        iRecyclerViewMascotaFragmentView.inicializarAdaptadorRV(iRecyclerViewMascotaFragmentView.crearAdaptador(listMascotas));
        iRecyclerViewMascotaFragmentView.generarLinearLayoutVertical();
    }


}
