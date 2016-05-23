package com.recreu.recreu;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.views.CrearUsuario;
import com.recreu.recreu.views.Explorar;
import com.recreu.recreu.views.IniciarSesion;
import com.recreu.recreu.views.NuevaActividad;
import cl.recreu.recreu.taller_android_bd.R;


public class MainActivity extends AppCompatActivity {

    private TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        tv1 = (TextView) findViewById(R.id.tv1);
    }

    public void crearUsuario(View vista){
        tv1.setText("hola clickeé crear Usuario");
        Intent i = new Intent (this, CrearUsuario.class);
        startActivity(i);

    }
    public void iniciarSesion(View vista){
        tv1.setText("Iniciando sesión");
        Intent i = new Intent(this,IniciarSesion.class);
        startActivity(i);
    }

}
/*
// lugar para practicar -> esto se debe ejecutar después de seleccionar EXPLORAR en navBAr
transaccion = getFragmentManager().beginTransaction();
        transaccion.replace(R.id.fragment_container, new NuevaActividad());
        new MainActivity();
        transaccion.commit();*/