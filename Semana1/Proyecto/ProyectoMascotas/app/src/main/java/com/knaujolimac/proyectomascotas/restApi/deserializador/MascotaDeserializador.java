package com.knaujolimac.proyectomascotas.restApi.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.knaujolimac.proyectomascotas.pojo.UsuarioPerfil;
import com.knaujolimac.proyectomascotas.restApi.ConstantesRestApi;
import com.knaujolimac.proyectomascotas.restApi.JsonKeys;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Camilo Chaparro on 15/10/2016.
 */
public class MascotaDeserializador implements JsonDeserializer<MascotaResponse> {

    private int opcionDeserealiar;

    public MascotaDeserializador(int opcionDeserealiar) {
        this.opcionDeserealiar = opcionDeserealiar;
    }

    @Override
    public MascotaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        MascotaResponse mascotaResponse = gson.fromJson(json, MascotaResponse.class);
        JsonArray mascotaResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        ArrayList<MascotaPerfil> mascotas  = null;
        if(opcionDeserealiar == ConstantesRestApi.DESEREALIZAR_USER_SEARCH){
            mascotas = this.deserealizarSearchUser(mascotaResponseData);
        }
        else {
            mascotas = this.deserealizarMediaRecent(mascotaResponseData);
        }

        mascotaResponse.setMascotas(mascotas);
        return mascotaResponse;
    }

    /**
     * Método encargado de deserealizar la información de la media reciente de un perfil
     * @param mascotaResponseData
     * @return
     */
    public ArrayList<MascotaPerfil>  deserealizarMediaRecent(JsonArray mascotaResponseData ){
        ArrayList<MascotaPerfil> mascotas = new ArrayList<>();

        for (int i = 0; i < mascotaResponseData.size() ; i++) {

            JsonObject contactoResponseDataObject = mascotaResponseData.get(i).getAsJsonObject();

            JsonObject userJson     = contactoResponseDataObject.getAsJsonObject(JsonKeys.USER);
            String id               = userJson.get(JsonKeys.USER_ID).getAsString();
            String nombreUsuario   = userJson.get(JsonKeys.USER_NAME).getAsString();
            String nombreCompleto   = userJson.get(JsonKeys.USER_FULLNAME).getAsString();
            String urlFotoPerfil =  userJson.get(JsonKeys.PROFILE_PICTURE).getAsString();

            JsonObject imageJson            = contactoResponseDataObject.getAsJsonObject(JsonKeys.MEDIA_IMAGES);
            JsonObject stdResolutionJson    = imageJson.getAsJsonObject(JsonKeys.MEDIA_STANDARD_RESOLUTION);
            String urlFoto                  = stdResolutionJson.get(JsonKeys.MEDIA_URL).getAsString();

            JsonObject likesJson = contactoResponseDataObject.getAsJsonObject(JsonKeys.MEDIA_LIKES);
            int likes = likesJson.get(JsonKeys.MEDIA_LIKES_COUNT).getAsInt();

            MascotaPerfil mascotaData= new MascotaPerfil();
            UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
            usuarioPerfil.setId(id);
            usuarioPerfil.setNombreUsuario(nombreUsuario);
            usuarioPerfil.setFullName(nombreCompleto);
            usuarioPerfil.setUrlFotoProfile(urlFotoPerfil);

            mascotaData.setUsuarioPerfil(usuarioPerfil);
            mascotaData.setCantidadLikes(likes);
            mascotaData.setUrlFoto(urlFoto);

            mascotas.add(mascotaData);
        }

        return mascotas;
    }

    /**
     * Método encargado de deserealizar los datos en la busqueda de un usuario
     * @param mascotaResponseData
     * @return
     */
    public ArrayList<MascotaPerfil>  deserealizarSearchUser(JsonArray mascotaResponseData ){
        ArrayList<MascotaPerfil> mascotas = new ArrayList<>();

        for (int i = 0; i < mascotaResponseData.size() ; i++) {

            JsonObject contactoResponseDataObject = mascotaResponseData.get(i).getAsJsonObject();

            String id               = contactoResponseDataObject.get(JsonKeys.USER_ID).getAsString();
            String nombreUsuario   = contactoResponseDataObject.get(JsonKeys.USER_NAME).getAsString();


            MascotaPerfil mascotaData= new MascotaPerfil();
            UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
            usuarioPerfil.setId(id);
            usuarioPerfil.setNombreUsuario(nombreUsuario);
            mascotaData.setUsuarioPerfil(usuarioPerfil);

            mascotas.add(mascotaData);
        }

        return mascotas;
    }


}
