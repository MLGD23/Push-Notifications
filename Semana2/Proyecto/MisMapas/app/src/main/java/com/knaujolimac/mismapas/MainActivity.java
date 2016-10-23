package com.knaujolimac.mismapas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * Método encargado de visualizar el mapa
     * @param tipoMapa
     */
    public void visualizarMapa(String tipoMapa){
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra(Constantes.INDICADOR_MAPA,tipoMapa);
        startActivity(intent);
    }

    /**
     * Método encargado de controlar las acciones de los botones
     * @param view
     */
    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.btnDeporte){
            visualizarMapa(Constantes.INDICADOR_MAPA_POLIDEPORTIVO);
        }
        else if(view.getId()==R.id.btnIglesia){
            visualizarMapa(Constantes.INDICADOR_MAPA_IGLESIA);
        }
        else if(view.getId()==R.id.btnZonaComidas){
            visualizarMapa(Constantes.INDICADOR_MAPA_ZONA_COMIDAS);
        }
        else if(view.getId()==R.id.btnParque){
            visualizarMapa(Constantes.INDICADOR_MAPA_PARQUE);
        }

    }
}
