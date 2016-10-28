package com.knaujolimac.proyectomascotas.restApi.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.knaujolimac.proyectomascotas.pojo.UsuarioPerfil;
import com.knaujolimac.proyectomascotas.restApi.JsonKeys;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioInstaResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Camilo Chaparro on 24/10/2016.
 */

public class UsuarioInstagramDeserializador implements JsonDeserializer<UsuarioInstaResponse> {

    @Override
    public UsuarioInstaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        UsuarioInstaResponse usuarioInstaResponse = gson.fromJson(json, UsuarioInstaResponse.class);
        JsonArray usuarioInstaResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        ArrayList<UsuarioPerfil> usuarios = null;

        usuarios = this.deserealizarUsuariosFollows(usuarioInstaResponseData);

        usuarioInstaResponse.setUsuarios(usuarios);

        return usuarioInstaResponse;

    }

    /**
     * Método encargado de deserealizar el json de los usuarios instagramas seguidos
     * @param usuarioResponseData
     * @return
     */
    public ArrayList<UsuarioPerfil>  deserealizarUsuariosFollows(JsonArray usuarioResponseData ){
        ArrayList<UsuarioPerfil> usuarioPerfiles = new ArrayList<>();

        for (int i = 0; i < usuarioResponseData.size() ; i++) {

            JsonObject usuarioResponseDataObject = usuarioResponseData.get(i).getAsJsonObject();

            String id               = usuarioResponseDataObject.get(JsonKeys.USER_ID).getAsString();
            String nombreUsuario   = usuarioResponseDataObject.get(JsonKeys.USER_NAME).getAsString();
            String fullName   = usuarioResponseDataObject.get(JsonKeys.USER_FULLNAME).getAsString();
            String urlFotoProfile   = usuarioResponseDataObject.get(JsonKeys.PROFILE_PICTURE).getAsString();

            UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
            usuarioPerfil.setId(id);
            usuarioPerfil.setNombreUsuario(nombreUsuario);
            usuarioPerfil.setFullName(fullName);
            usuarioPerfil.setUrlFotoProfile(urlFotoProfile);

            usuarioPerfiles.add(usuarioPerfil);
        }

        return usuarioPerfiles;
    }

}
