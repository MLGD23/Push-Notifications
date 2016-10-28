package com.knaujolimac.proyectomascotas.restApi;

import com.knaujolimac.proyectomascotas.restApi.model.MascotaLikeInstagramResponse;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaLikeResponse;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioDeviceResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioInstaResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Camilo Chaparro on 15/10/2016.
 */
public interface EndpointsApi {

    @GET(ConstantesRestApi.URL_GET_RECENT_MEDIA_USER)
    Call<MascotaResponse> getRecentMedia();

    @GET(ConstantesRestApi.URL_GET_USERS_SEARCH)
    Call<MascotaResponse> getUsersSearch(@QueryMap Map<String, String> map);

    @GET(ConstantesRestApi.URL_GET_RECENT_MEDIA_ID_USER)
    Call<MascotaResponse> getRecentMediaIdUsuario(@Path("idUsuario") String idUsuario);

    @GET(ConstantesRestApi.URL_GET_FOLLOWS)
    Call<UsuarioInstaResponse> getUsuariosFollows();

    @FormUrlEncoded
    @POST(ConstantesRestApi.URL_POST_MEDIA_LIKES)
    Call<MascotaLikeInstagramResponse> procesarLikeInstagram(@Field("access_token") String accessToken, @Path("idFoto") String idFoto);

    /**
     * EndPoint encargado de registrar el id del device de un cliente
     * @param idDispositivo
     * @param idUsuarioInstagram
     * @return
     */
    @FormUrlEncoded
    @POST(ConstantesRestApi.URL_POST_REGISTRAR_USUARIO)
    Call<UsuarioDeviceResponse> registrarUsuarioDispositivo(@Field("idDispositivo") String idDispositivo,
                                                        @Field("idUsuarioInstagram") String idUsuarioInstagram);

    /**
     * EndPoint encargado de procesar el like de un usuario sobre una mascota
     * en el servidor de mascotas
     * @param idUsuarioEmisor
     * @param idUsuarioReceptor
     * @param idFotoInstagram
     * @return
     */
    @FormUrlEncoded
    @POST(ConstantesRestApi.URL_POST_PROCESAR_LIKE_USUARIO)
    Call<MascotaLikeResponse> procesarLikeUsuario(@Field("idUsuarioEmisor") String idUsuarioEmisor,
                                                             @Field("idUsuarioReceptor") String idUsuarioReceptor,
                                                             @Field("idFotoInstagram") String idFotoInstagram );
}
