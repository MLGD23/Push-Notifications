package com.knaujolimac.proyectomascotas.restApi.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.knaujolimac.proyectomascotas.restApi.JsonKeys;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaLikeInstagramResponse;

import java.lang.reflect.Type;

/**
 * Created by Camilo Chaparro on 26/10/2016.
 */

public class MascotaLikeDeserializador implements JsonDeserializer<MascotaLikeInstagramResponse> {

    @Override
    public MascotaLikeInstagramResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        MascotaLikeInstagramResponse mascotaLikeInstagramResponse = gson.fromJson(json, MascotaLikeInstagramResponse.class);

        JsonObject likeRepuestaObject = json.getAsJsonObject().getAsJsonObject(JsonKeys.LIKE_META);
        String codeRepuesta = likeRepuestaObject.get(JsonKeys.LIKE_CODE).getAsString();

        mascotaLikeInstagramResponse.setCodigoRespuesta(codeRepuesta);

        return mascotaLikeInstagramResponse;

    }
}
