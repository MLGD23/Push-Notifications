package com.knaujolimac.proyectomascotas.restApi;

/**
 * Created by Camilo Chaparro on 15/10/2016.
 */
public class ConstantesRestApi {
    //https://api.instagram.com/v1/
    public static final String VERSION = "/v1/";
    public static final String ROOT_URL = "https://api.instagram.com" + VERSION;
    public static final String ACCESS_TOKEN = "4036470568.74b78a3.bf555f5c30554b6fb3ec1b71906a43e7";
    public static final String CONECTOR = "?";
    public static final String ASIGNACION= "=";
    public static final String KEY_ACCESS_TOKEN = "access_token";

    //Endpoints
    //este enpoint permite obtener la fotos recientes de mi perfil
    public static final String KEY_GET_RECENT_MEDIA_USER = "users/self/media/recent/";
    public static final String URL_GET_RECENT_MEDIA_USER =
            KEY_GET_RECENT_MEDIA_USER + CONECTOR + KEY_ACCESS_TOKEN + ASIGNACION + ACCESS_TOKEN;
    //https://api.instagram.com/v1/users/self/media/recent/?access_token=ACCESS-TOKEN

    //Este endpoint permite realizar la busqueda de usarios mediante el filtro nombre
    public static final String URL_GET_USERS_SEARCH = "users/search";
    public static final String KEY_GET_USERS_SEARCH_FILTER = "q";
    //https://api.instagram.com/v1/users/search?q=jack&access_token=ACCESS-TOKEN


    //Este endpoint permite obtener la media reciente mediante el id de un usuario
    public static final String KEY_GET_RECENT_MEDIA_ID_USER = "users/{idUsuario}";
    public static final String KEY_GET_RECENT_MEDIA_ID_USER_COMPLEMENTO = "/media/recent/";
    public static final String URL_GET_RECENT_MEDIA_ID_USER =
            KEY_GET_RECENT_MEDIA_ID_USER + KEY_GET_RECENT_MEDIA_ID_USER_COMPLEMENTO + CONECTOR + KEY_ACCESS_TOKEN + ASIGNACION + ACCESS_TOKEN;;
    //https://api.instagram.com/v1/users/{user-id}/media/recent/?access_token=ACCESS-TOKEN


    public static final int DESEREALIZAR_MEDIA_RECENT = 1;
    public static final int DESEREALIZAR_USER_SEARCH = 2;



}
