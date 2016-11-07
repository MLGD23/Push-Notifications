package com.knaujolimac.proyectomascotas.pojo;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.knaujolimac.proyectomascotas.ConfigurarCuenta;
import com.knaujolimac.proyectomascotas.R;
import com.knaujolimac.proyectomascotas.db.BaseDatos;
import com.knaujolimac.proyectomascotas.db.ConstantesBaseDatos;
import com.knaujolimac.proyectomascotas.restApi.ConstantesRestApi;
import com.knaujolimac.proyectomascotas.restApi.EndpointsApi;
import com.knaujolimac.proyectomascotas.restApi.adapter.RestApiAdapter;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaLikeInstagramResponse;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaLikeResponse;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioDeviceResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioFollowResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioInstaResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public class ConstructorMascota {

    private Context context;
    private static final int CANTIDAD_MASCOTAS_LIKES = 5;
    private List<UsuarioPerfil> usuariosPerfil;
    private ArrayList<MascotaPerfil> listMascotas;

    public ConstructorMascota(Context context) {
        this.context = context;
        listMascotas = new ArrayList<MascotaPerfil>();
    }

    /**
     * Método encargado de registrar la mascota cuando recibe un like
     * @param mascota
     */
    public void darLikeMascotaBD(Mascota mascota){
        BaseDatos db = new BaseDatos(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_ID, mascota.getIdMascota());
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE_MASCOTA, mascota.getNombreMascota());
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_ID_FOTO, mascota.getFoto());
        db.insertarLikesMascota(contentValues);
    }


    /**
     * Método encargado de procesar el like efectuado por el usuario
     * mediante los Endpoint del servidor mascotas y el de instagram
     * @param mascota
     */
    public void procesarLikeMascota(final MascotaPerfil mascota){

        //Se obtiene la información del usuario configurado en la app
        ConstructorPerfilMascota constructorPerfilMascota = new ConstructorPerfilMascota(this.context);
        UsuarioPerfil usuarioPerfil= constructorPerfilMascota.obtenerUsuarioPerfil();

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndpointsApi endponits = restApiAdapter.establecerConexionServidorMascotas();
        Call<MascotaLikeResponse> mascotaLikeResponseCall = endponits.procesarLikeUsuario(usuarioPerfil.getNombreUsuario(),
                mascota.getUsuarioPerfil().getNombreUsuario(),mascota.getIdFoto());

        mascotaLikeResponseCall.enqueue(new Callback<MascotaLikeResponse>() {
            @Override
            public void onResponse(Call<MascotaLikeResponse> call, Response<MascotaLikeResponse> response) {
                MascotaLikeResponse mascotaLikeResponse = response.body();

                if(mascotaLikeResponse!=null){
                    Log.d("ID_USU_NOTIFICADO", mascotaLikeResponse.getIdUsuarioNotificado());
                    Log.d("ESTADO_NOTIFICACION", mascotaLikeResponse.isNotificacionProcesada()?"Prcesada exitosamente":"Procesada con error");

                    //Se invoca enpoint encargado de procesar like instagram
                    procesarLikeInstagram(mascota.getIdFoto());
                    //Toast.makeText(context,"Se registro el dispositivo correctamente.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MascotaLikeResponse> call, Throwable t) {
                Toast.makeText(context,"Ocurrió un error en la conexión.",Toast.LENGTH_SHORT).show();
                Log.e("REGISTRAR_USUARIO", "Ocurrio un error al registrar el usuario. " + t.toString());
            }
        });

    }

    /**
     * Método encargado de procesar el like en instagram
     * @param idFotoLike
     */
    private void procesarLikeInstagram(String idFotoLike) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorDarLike();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);
        Call<MascotaLikeInstagramResponse> mascotaLikeCall = endpointsApi.procesarLikeInstagram(ConstantesRestApi.ACCESS_TOKEN,idFotoLike);

        mascotaLikeCall.enqueue(new Callback<MascotaLikeInstagramResponse>() {
            @Override
            public void onResponse(Call<MascotaLikeInstagramResponse> call, Response<MascotaLikeInstagramResponse> response) {
                MascotaLikeInstagramResponse mascotaLikeResponse = response.body();
                Log.d("ID_COD_RESPUESTA", mascotaLikeResponse.getCodigoRespuesta());
            }

            @Override
            public void onFailure(Call<MascotaLikeInstagramResponse> call, Throwable t) {
                Toast.makeText(context, "Ocurrió un error al obtener los usuarios seguidos", Toast.LENGTH_LONG).show();
                Log.e("Error en la conexión", t.toString());

            }
        });

    }

    /**
     * Método encargado de modificar la relación entre un usuario y el usuario principal
     * del token
     * @param idUsuarioLike
     * @param accion
     */
    private void modificarRelationShip(String idUsuarioLike,String accion){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorUsuariosRelationShip();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);

        Call<UsuarioFollowResponse> mascotaLikeCall = endpointsApi.modificarRelacionUsuario(idUsuarioLike,accion);

        mascotaLikeCall.enqueue(new Callback<UsuarioFollowResponse>() {
            @Override
            public void onResponse(Call<UsuarioFollowResponse> call, Response<UsuarioFollowResponse> response) {
                UsuarioFollowResponse usuarioFollowResponse = response.body();
                Log.d("OUTGOIN_STATUS", usuarioFollowResponse.getOutGoingStatus());
                Log.d("TARGET_USER_IS_PRIVATE", usuarioFollowResponse.isUserPrivate()?"true":"false");
                Log.d("INCOMING_STATUS", usuarioFollowResponse.getIncomingStatus());
            }

            @Override
            public void onFailure(Call<UsuarioFollowResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Ocurrió un error al modificar la relacion con el usuario", Toast.LENGTH_LONG).show();
                Log.e("Error en la conexión", t.toString());

            }
        });
    }


    /**
     * Método encargado de modificar la relación entre un usuario y el usuario principal
     * del token
     * @param nombreUsuario
     */
    public void modificarRelacionShipByNomUsu(final String nombreUsuario){

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

                        //Se invoca el método para calcular el estado actual de la relacion
                        //Si se esta siguiendo se deja de seguir, en caso contrario se sigue
                        cacularRelationShipUsuario(usuarioPerfil.getId(),usuarioPerfil.getNombreUsuario());
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
     * Método encargado de validar el estado actual de la relacion con el usuario
     * del token
     * @param idUsuarioLike
     * @param nomUsuario
     */
    private void cacularRelationShipUsuario(final String idUsuarioLike,final String nomUsuario){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorUsuariosRelationShip();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);

        Call<UsuarioFollowResponse> mascotaLikeCall = endpointsApi.obtenerInfoRelacionUsuario(idUsuarioLike);

        mascotaLikeCall.enqueue(new Callback<UsuarioFollowResponse>() {
            @Override
            public void onResponse(Call<UsuarioFollowResponse> call, Response<UsuarioFollowResponse> response) {
                UsuarioFollowResponse usuarioFollowResponse = response.body();
                Log.d("CONSULTA_RELACION","Estado inicial de la relacion entre los usuarios");
                Log.d("OUTGOIN_STATUS", usuarioFollowResponse.getOutGoingStatus());
                Log.d("TARGET_USER_IS_PRIVATE", usuarioFollowResponse.isUserPrivate()?"true":"false");
                Log.d("INCOMING_STATUS", usuarioFollowResponse.getIncomingStatus());

                if(usuarioFollowResponse.getOutGoingStatus().equalsIgnoreCase(ConstantesRestApi.ACTION_FOLLOW_RESPUESTA)){
                    String accion = ConstantesRestApi.ACTION_UNFOLLOW;
                    modificarRelationShip(idUsuarioLike,accion);
                    Toast.makeText(context, "Dejaste de seguir al usuario "+nomUsuario, Toast.LENGTH_LONG).show();
                }
                else{
                    String accion = ConstantesRestApi.ACTION_FOLLOW;
                    modificarRelationShip(idUsuarioLike,accion);
                    Toast.makeText(context, "Empezaste a seguir al usuario "+nomUsuario, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioFollowResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Ocurrió un error al modificar la relacion con el usuario", Toast.LENGTH_LONG).show();
                Log.e("Error en la conexión", t.toString());

            }
        });
    }


    /**
     * Método encargado de obtener las mascotas con mas likes
     * @return
     */
    public ArrayList<Mascota> obtenerMascotasLikes(){
        ArrayList<Mascota> mascotasLikes = null;
        BaseDatos db = new BaseDatos(context);

        mascotasLikes = db.obtenerMascotasRating(this.CANTIDAD_MASCOTAS_LIKES,true);
        return mascotasLikes;
    }

    /**
     * Método encargado de actualizar la cantidad de likes
     * @param mascotas
     * @return
     */
    public ArrayList<Mascota> actualizarLikesMascota(ArrayList<Mascota> mascotas){
        ArrayList<Mascota> mascotasLikes = null;
        BaseDatos db = new BaseDatos(context);

        mascotasLikes = db.obtenerMascotasRating(this.CANTIDAD_MASCOTAS_LIKES,false);

        /*Se actualiza la cantidad de likes*/
        for(Mascota mascota :mascotas){
            for(Mascota mLike :mascotasLikes){
                if(mascota.getIdMascota()==mLike.getIdMascota()){
                    mascota.setCantidadLikes(mLike.getCantidadLikes());
                }
            }
        }

        return mascotas;
    }

    public ArrayList<MascotaPerfil> getListMascotas() {
        return listMascotas;
    }

    public void setListMascotas(ArrayList<MascotaPerfil> listMascotas) {
        this.listMascotas = listMascotas;
    }
}
