package com.knaujolimac.proyectomascotas.presentador;

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
import com.knaujolimac.proyectomascotas.view.fragment.IRecyclerViewPerfilFragmentView;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public class RecylerViewPerfilFragmentPresenter implements IRecylerViewPerfilFragmentPresenter {

    private IRecyclerViewPerfilFragmentView iRecyclerViewPerfilFragmentView;
    private Context context;
    private ArrayList<MascotaPerfil> listFotosPerfil;
    private boolean primeraVez;

    public RecylerViewPerfilFragmentPresenter(IRecyclerViewPerfilFragmentView iRecyclerViewPerfilFragmentView, Context context) {
        this.iRecyclerViewPerfilFragmentView = iRecyclerViewPerfilFragmentView;
        this.context = context;

        //Obtiene las fotos del perfil
        this.obtenerFotosPerfilMascota();
        //Muestra las fotos del perfil en el Recycle View
        //this.mostrarPerfilRVMascotas();
    }


    @Override
    public void obtenerFotosPerfilMascota() {

        ConstructorPerfilMascota constructorPerfilMascota = new ConstructorPerfilMascota(this.context);
        UsuarioPerfil usuarioPerfil= constructorPerfilMascota.obtenerUsuarioPerfil();

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorMediaRecent();;
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);
        Call<MascotaResponse> mascotaResponseCall = null;

        //Si no se ha registrado un perfil, se obtiene la media del usuario principal

        if(usuarioPerfil==null){
            mascotaResponseCall = endpointsApi.getRecentMedia();
            primeraVez = true;
        }
        else{ //se obtiene la información del perfil registrado mediante el ID
            mascotaResponseCall = endpointsApi.getRecentMediaIdUsuario(usuarioPerfil.getId());
            primeraVez = false;
        }

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                listFotosPerfil = mascotaResponse.getMascotas();

                if(primeraVez){
                    //Agrega los datos del usuario en la base de datos
                    ConstructorPerfilMascota constructorPerfilMascota = new ConstructorPerfilMascota(context);
                    UsuarioPerfil nuevoPerfil = listFotosPerfil.get(0).getUsuarioPerfil();
                    constructorPerfilMascota.modificarUsuarioPerfil(nuevoPerfil);
                }
                mostrarPerfilRVMascotas();
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Ocurrió un error al obtener las fotos del perfil del usuario", Toast.LENGTH_LONG).show();
                Log.e("Error en la conexión", t.toString());
            }
        });

        //
        //listFotosPerfil = constructorPerfilMascota.obtenerFotosPerfilMascota();
    }

    @Override
    public void mostrarPerfilRVMascotas() {
        iRecyclerViewPerfilFragmentView.generarGridLayout();
        iRecyclerViewPerfilFragmentView.inicializarAdaptadorRV(iRecyclerViewPerfilFragmentView.crearAdaptador(listFotosPerfil));
        if(listFotosPerfil!=null && listFotosPerfil.size()> 0)
            iRecyclerViewPerfilFragmentView.actualizarDatosPerfil(listFotosPerfil.get(0));
    }
}
