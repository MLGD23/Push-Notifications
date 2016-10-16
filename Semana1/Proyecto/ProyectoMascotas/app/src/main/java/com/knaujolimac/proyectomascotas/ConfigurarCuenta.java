package com.knaujolimac.proyectomascotas;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.knaujolimac.proyectomascotas.pojo.ConstructorPerfilMascota;
import com.knaujolimac.proyectomascotas.pojo.MascotaPerfil;
import com.knaujolimac.proyectomascotas.pojo.UsuarioPerfil;
import com.knaujolimac.proyectomascotas.restApi.ConstantesRestApi;
import com.knaujolimac.proyectomascotas.restApi.EndpointsApi;
import com.knaujolimac.proyectomascotas.restApi.adapter.RestApiAdapter;
import com.knaujolimac.proyectomascotas.restApi.model.MascotaResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurarCuenta extends AppCompatActivity {

    private Button btnGuardarCuenta;
    private TextInputEditText tieUsuarioInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);

        Toolbar miActionBar = (Toolbar)findViewById(R.id.miActionBarB);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnGuardarCuenta = (Button)findViewById(R.id.btnGuardarCuenta);
        tieUsuarioInstagram= (TextInputEditText) findViewById(R.id.tieUsuarioInstagram);

        btnGuardarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUsuario = tieUsuarioInstagram.getText().toString();

                if(nombreUsuario.isEmpty()){
                    Toast.makeText(ConfigurarCuenta.this,"No ha proporcionado el nombre del usuario",Toast.LENGTH_LONG).show();
                }
                else{
                    obtenerInformacionUsuario(nombreUsuario);
                    tieUsuarioInstagram.setText("");
                }
            }
        });
    }

    /**
     * Método encargado de obtener la información del usuario ingresado
     * @param nombreUsuario
     */
    private void obtenerInformacionUsuario(final String nombreUsuario) {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorSearchUser();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);


        Map<String, String> map = new HashMap<>();
        map.put(ConstantesRestApi.KEY_GET_USERS_SEARCH_FILTER, nombreUsuario);
        map.put(ConstantesRestApi.KEY_ACCESS_TOKEN, ConstantesRestApi.ACCESS_TOKEN);
        Call<MascotaResponse> mascotaResponseCall = endpointsApi.getUsersSearch(map);

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                ArrayList<MascotaPerfil> listaUsuarios = mascotaResponse.getMascotas();

                if(listaUsuarios==null||listaUsuarios.isEmpty()){
                    Toast.makeText(ConfigurarCuenta.this, "No se encontraron datos para el usuario ingresado", Toast.LENGTH_LONG).show();
                }
                else{
                    UsuarioPerfil usuarioPerfil = listaUsuarios.get(0).getUsuarioPerfil();

                    if(nombreUsuario.equalsIgnoreCase(usuarioPerfil.getNombreUsuario())){
                        if(actualizarPerfilMascota(usuarioPerfil)){
                            String mensje = "Se guardo la cuenta del usuario "+usuarioPerfil.getNombreUsuario()+" correctamente.";
                            Toast.makeText(ConfigurarCuenta.this, mensje, Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ConfigurarCuenta.this, "Ocurrió un error al actualizar la cuenta " + nombreUsuario, Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(ConfigurarCuenta.this, "No se encontro el usuario " + nombreUsuario, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(ConfigurarCuenta.this, "Ocurrió un error al obtener las fotos del perfil del usuario", Toast.LENGTH_LONG).show();
                Log.e("Error en la conexión", t.toString());
            }
        });

    }

    /**
     * Método encargado de actualizar la cuenta
     * @param usuarioPerfil
     * @return
     */
    private boolean actualizarPerfilMascota(UsuarioPerfil usuarioPerfil) {
        ConstructorPerfilMascota constructorPerfilMascota = new ConstructorPerfilMascota(this);
        return constructorPerfilMascota.modificarUsuarioPerfil(usuarioPerfil);
    }
}
