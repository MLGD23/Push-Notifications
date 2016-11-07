package com.knaujolimac.proyectomascotas.restApi.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.knaujolimac.proyectomascotas.pojo.UsuarioPerfil;
import com.knaujolimac.proyectomascotas.restApi.ConstantesRestApi;
import com.knaujolimac.proyectomascotas.restApi.JsonKeys;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioFollowResponse;
import com.knaujolimac.proyectomascotas.restApi.model.UsuarioInstaResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Camilo Chaparro on 24/10/2016.
 */

public class UsuarioInstagramDeserializador implements JsonDeserializer<Object> {

    private int opcionDeserealiar;

    public UsuarioInstagramDeserializador(int opcionDeserealiar) {
        this.opcionDeserealiar = opcionDeserealiar;
    }

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        Object retorno  = null;
        switch (opcionDeserealiar){
            case ConstantesRestApi.DESEREALIZAR_USUARIOS_FOLLOW:
                ArrayList<UsuarioPerfil> usuarios;
                UsuarioInstaResponse usuarioInstaResponse = gson.fromJson(json, UsuarioInstaResponse.class);
                usuarios = this.deserealizarUsuariosFollows(json);
                usuarioInstaResponse.setUsuarios(usuarios);
                retorno = usuarioInstaResponse;
                break;
            case ConstantesRestApi.DESEREALIZAR_RELATIONSHIP:
                UsuarioFollowResponse usuarioFollowResponse = gson.fromJson(json, UsuarioFollowResponse.class);
                usuarioFollowResponse = deserealizarRelationShip(usuarioFollowResponse,json);
                retorno = usuarioFollowResponse;
                break;
        }

        return retorno;

    }

    /**
     * Método encargado de deserealizar el json de los usuarios instagramas seguidos
     * @param json
     * @return
     */
    public ArrayList<UsuarioPerfil>  deserealizarUsuariosFollows(JsonElement json){

        JsonArray usuarioInstaResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        ArrayList<UsuarioPerfil> usuarioPerfiles = new ArrayList<>();

        for (int i = 0; i < usuarioInstaResponseData.size() ; i++) {

            JsonObject usuarioResponseDataObject = usuarioInstaResponseData.get(i).getAsJsonObject();

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

    /**
     * Método encargado de procesar la respuesta del EndPoint relationship
     * @param usuarioFollowResponse
     * @param json
     * @return
     */
    public UsuarioFollowResponse deserealizarRelationShip(UsuarioFollowResponse usuarioFollowResponse,JsonElement json){

        JsonObject dataJsonObject = json.getAsJsonObject();
        JsonObject valoresRespuestaDataObject = dataJsonObject.getAsJsonObject(JsonKeys.MEDIA_RESPONSE_ARRAY);

        String outGoingStatus = valoresRespuestaDataObject.get(JsonKeys.OUTGOING_STATUS).getAsString();
        boolean userPrivate = valoresRespuestaDataObject.get(JsonKeys.USER_IS_PRIVATE).getAsBoolean();
        String incomingStatus = "";
        if(valoresRespuestaDataObject.get(JsonKeys.INCOMING_STATUS)!=null){
            incomingStatus = valoresRespuestaDataObject.get(JsonKeys.INCOMING_STATUS).getAsString();
        }

        usuarioFollowResponse.setOutGoingStatus(outGoingStatus);
        usuarioFollowResponse.setIncomingStatus(incomingStatus);
        usuarioFollowResponse.setUserPrivate(userPrivate);

        return usuarioFollowResponse;
    }

}
