package com.knaujolimac.mismapas;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String indicadorMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        indicadorMapa = intent.getStringExtra(Constantes.INDICADOR_MAPA);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        switch (indicadorMapa) {
            case Constantes.INDICADOR_MAPA_PARQUE:
                this.establecerMapa();
                break;
            case Constantes.INDICADOR_MAPA_IGLESIA:
                this.establecerMapa();
                break;
            case Constantes.INDICADOR_MAPA_ZONA_COMIDAS:
                this.establecerMapa();
                break;
            case Constantes.INDICADOR_MAPA_POLIDEPORTIVO:
                this.establecerMapa();
                break;
        }
    }





    public void establecerMapa(){

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(4.578830534417852, -74.07731344398893);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,20));

        Toast.makeText(this,"Crear Mapa", Toast.LENGTH_SHORT);

        /*LatLng mexico = new LatLng(19.440311826530742, -99.15481941250005);
        mMap.addMarker(new MarkerOptions().position(mexico).title("Marker in Mexico"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mexico));*/
    }
}
