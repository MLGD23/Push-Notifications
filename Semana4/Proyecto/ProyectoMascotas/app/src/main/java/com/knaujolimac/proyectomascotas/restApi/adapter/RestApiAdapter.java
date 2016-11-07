package com.knaujolimac.proyectomascotas.restApi.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knaujolimac.proyectomascotas.restApi.ConstantesRestApi;
import com.knaujolimac.proyectomascotas.restApi.EndpointsApi;
import com.knaujolimac.proyectomascotas.restApi.deserializador.MascotaDeserializador;
import com.knaujolimac.proyectomascotas.restApi.deserializador.MascotaLikeDeserializador;
import com.knaujolimac.proyectomascotas.restApi.deserializador.UsuarioInstagramDeserializador;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaLikeInstagramResponse;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioFollowResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioInstaResponse;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Camilo Chaparro on 15/10/2016.
 */
public class RestApiAdapter {

    /**
     * Método encargado de establecer conexión con los servicios expuestos
     * en el servidor Instagram
     * @param gson
     * @return
     */
    public EndpointsApi establecerConexionRestApiInstagram(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(EndpointsApi.class);
    }

    /**
     * Método encargado de obtener conexión con los servicios
     * expuestos en el servidor de mascotas
     * @return
     */
    public EndpointsApi establecerConexionServidorMascotas(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL_SERVICIOR_MASCOTAS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                ;
        return retrofit.create(EndpointsApi.class);
    }

    public Gson construyeGsonDeserializadorMediaRecent(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MascotaResponse.class, new MascotaDeserializador(ConstantesRestApi.DESEREALIZAR_MEDIA_RECENT));
        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorSearchUser(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MascotaResponse.class, new MascotaDeserializador(ConstantesRestApi.DESEREALIZAR_USER_SEARCH));
        return gsonBuilder.create();
    }


    public Gson construyeGsonDeserializadorUsuariosFollows(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(UsuarioInstaResponse.class, new UsuarioInstagramDeserializador(ConstantesRestApi.DESEREALIZAR_USUARIOS_FOLLOW));
        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorDarLike(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MascotaLikeInstagramResponse.class, new MascotaLikeDeserializador());
        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorUsuariosRelationShip(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(UsuarioFollowResponse.class, new UsuarioInstagramDeserializador(ConstantesRestApi.DESEREALIZAR_RELATIONSHIP));
        return gsonBuilder.create();
    }



}
