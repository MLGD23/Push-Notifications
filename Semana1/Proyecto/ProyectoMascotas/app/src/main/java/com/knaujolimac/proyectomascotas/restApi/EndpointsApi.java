package com.knaujolimac.proyectomascotas.restApi;

import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
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
}
