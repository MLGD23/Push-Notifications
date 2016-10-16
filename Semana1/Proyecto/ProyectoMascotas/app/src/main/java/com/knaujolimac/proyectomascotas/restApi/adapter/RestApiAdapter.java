package com.knaujolimac.proyectomascotas.restApi.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knaujolimac.proyectomascotas.restApi.ConstantesRestApi;
import com.knaujolimac.proyectomascotas.restApi.EndpointsApi;
import com.knaujolimac.proyectomascotas.restApi.deserializador.MascotaDeserializador;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Camilo Chaparro on 15/10/2016.
 */
public class RestApiAdapter {

    public EndpointsApi establecerConexionRestApiInstagram(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

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


}
