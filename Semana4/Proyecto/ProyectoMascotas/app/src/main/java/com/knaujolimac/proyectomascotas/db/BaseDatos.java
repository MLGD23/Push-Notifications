package com.knaujolimac.proyectomascotas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.knaujolimac.proyectomascotas.pojo.Mascota;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.knaujolimac.proyectomascotas.pojo.UsuarioPerfil;

import java.util.ArrayList;

/**
 * Created by juan.chaparro on 22/07/2016.
 */
public class BaseDatos extends SQLiteOpenHelper {

    private Context context;

    public BaseDatos(Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaContacto = "CREATE TABLE " + ConstantesBaseDatos.TABLE_MASCOTA + "(" +
                ConstantesBaseDatos.TABLE_MASCOTA_ID + " INTEGER, " +
                ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE_MASCOTA + " TEXT, " +
                ConstantesBaseDatos.TABLE_MASCOTA_ID_FOTO + " INTEGER" +
                ")";

        db.execSQL(queryCrearTablaContacto);

        queryCrearTablaContacto = "CREATE TABLE " + ConstantesBaseDatos.TABLE_USUARIO_PERFIL + "(" +
                ConstantesBaseDatos.TABLE_USUARIO_PERFIL_ID + " TEXT, " +
                ConstantesBaseDatos.TABLE_USUARIO_PERFIL_NOMBRE + " TEXT " +
                ")";

        db.execSQL(queryCrearTablaContacto);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + ConstantesBaseDatos.TABLE_MASCOTA);
        onCreate(db);
    }

    /**
     * Método encargado de insertar los likes de una mascota
     * @param contentValues
     */
    public void insertarLikesMascota(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_MASCOTA,null, contentValues);
        db.close();
    }

    /**
     * Método encargado de obtener las cinco mascotas con más likes
     * @param cantidadMascotas
     * @param filtrar
     * @return
     */
    public ArrayList<Mascota> obtenerMascotasRating(int cantidadMascotas, boolean filtrar){

        ArrayList<Mascota> mascotasLike =  new ArrayList<Mascota>();

        String subQuery = " SELECT " + ConstantesBaseDatos.TABLE_MASCOTA_ID + ","
                    + "  COUNT("+ConstantesBaseDatos.TABLE_MASCOTA_ID+") AS CANT_LIKES "
                    + "  FROM "+ConstantesBaseDatos.TABLE_MASCOTA
                    + " GROUP BY " + ConstantesBaseDatos.TABLE_MASCOTA_ID;

        String query = " SELECT DISTINCT A.*, B.CANT_LIKES " +
                " FROM " + ConstantesBaseDatos.TABLE_MASCOTA +" A " +
                " INNER JOIN ("+subQuery+") B ON A."+ConstantesBaseDatos.TABLE_MASCOTA_ID + "= B." + ConstantesBaseDatos.TABLE_MASCOTA_ID +
                " ORDER BY B.CANT_LIKES DESC ";

                if(filtrar){
                    query +=" LIMIT 0,"+cantidadMascotas;
                }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()){
            Mascota data = new Mascota();

            int index = 0;
            data.setIdMascota(registros.getInt(index++));
            data.setNombreMascota(registros.getString(index++));
            data.setFoto(registros.getInt(index++));
            data.setCantidadLikes(registros.getInt(index++));

            mascotasLike.add(data);
        }

        db.close();

        return mascotasLike;
    }

    /**
     * Método encargado de obtener la información del perfil de la mascota
     * @return
     */
    public UsuarioPerfil obtenerUsuarioPerfil(){
        String query = " SELECT * FROM " + ConstantesBaseDatos.TABLE_USUARIO_PERFIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        UsuarioPerfil usuarioPerfil = null;
        while (registros.moveToNext()){
            usuarioPerfil = new UsuarioPerfil();

            int index = 0;
            usuarioPerfil.setId(registros.getString(index++));
            usuarioPerfil.setNombreUsuario(registros.getString(index++));
            break;
        }
        db.close();

        return usuarioPerfil;
    }

    /**
     * Método encargado de actualizar la información del perfil
     * @param nuevoPerfil
     */
    public boolean modificarUsuarioPerfil(UsuarioPerfil nuevoPerfil){
        boolean resultado = false;
        UsuarioPerfil usuarioPerfil = this.obtenerUsuarioPerfil();

        if(usuarioPerfil==null){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_PERFIL_ID, nuevoPerfil.getId());
            contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_PERFIL_NOMBRE, nuevoPerfil.getNombreUsuario());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(ConstantesBaseDatos.TABLE_USUARIO_PERFIL,null, contentValues);
            db.close();
            resultado = true;
        }
        else{
            SQLiteDatabase db = this.getWritableDatabase();
            try{
                String updateStr = " UPDATE " + ConstantesBaseDatos.TABLE_USUARIO_PERFIL
                        + " SET " + ConstantesBaseDatos.TABLE_USUARIO_PERFIL_ID + " = '" + nuevoPerfil.getId() + "'" +
                        ", " + ConstantesBaseDatos.TABLE_USUARIO_PERFIL_NOMBRE  + " = '" + nuevoPerfil.getNombreUsuario() + "'" +
                        " WHERE " + ConstantesBaseDatos.TABLE_USUARIO_PERFIL_ID + " = '" + usuarioPerfil.getId() +"'" ;

                db = this.getWritableDatabase();
                db.execSQL(updateStr);
                resultado = true;
            }
            catch (Exception e){
                Log.e(BaseDatos.class.getName(),e.toString());
                e.printStackTrace();
            }
            finally {
                db.close();
            }
        }

        return resultado;


    }

}
