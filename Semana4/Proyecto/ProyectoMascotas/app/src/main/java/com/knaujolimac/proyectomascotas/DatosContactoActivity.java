package com.knaujolimac.proyectomascotas;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class DatosContactoActivity extends AppCompatActivity {

    private TextInputEditText tieNombre;
    private TextInputEditText tieCorreo;
    private TextInputEditText tieMensaje;
    private Button btnEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_contacto);

        Toolbar miActionBar = (Toolbar)findViewById(R.id.miActionBarB);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tieNombre= (TextInputEditText) findViewById(R.id.tieNombre);
        tieCorreo= (TextInputEditText) findViewById(R.id.tieCorreo);
        tieMensaje= (TextInputEditText) findViewById(R.id.tieMensaje);
        btnEnviar = (Button)findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DatosContactoActivity.this,"Mensaje enviado",Toast.LENGTH_LONG).show();
            }
        });
    }
}

