package com.recreu.recreu.views;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.recreu.recreu.MainActivity;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpDelete;
import com.recreu.recreu.utilities.SystemUtilities;

import cl.recreu.recreu.taller_android_bd.R;

public class Principal extends AppCompatActivity {
    FragmentTransaction transaccion;
    private Usuario usuario;
    private TextView nombre;
    private Button boton1;

    private EditText usuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        transaccion = getFragmentManager().beginTransaction();

        transaccion.replace(R.id.fragment_container, new Inicio(usuario), "inicio");
        new Principal();
        transaccion.addToBackStack(null);
        transaccion.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.navbar, menu);

        return true;
    }

    // boton atras : termina este Fragment y obtiene el anterior de la pila
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.inicio:

                if ( getFragmentManager().findFragmentByTag("inicio") == null) {
                    transaccion = getFragmentManager().beginTransaction();

                    transaccion.replace(R.id.fragment_container, new Inicio(usuario), "inicio");
                    new Principal();
                    transaccion.addToBackStack(null);
                    transaccion.commit();

                }
                break;

            case R.id.anadirActividad:

                if ( getFragmentManager().findFragmentByTag("nuevaActividad") == null) {
                    transaccion = getFragmentManager().beginTransaction();

                    transaccion.replace(R.id.fragment_container, new NuevaActividad(usuario), "nuevaActividad");
                    new Principal();
                    transaccion.addToBackStack(null);
                    transaccion.commit();

                }
                break;

            case R.id.explorar:
                if (getFragmentManager().findFragmentByTag("explorar") == null) {
                    transaccion = getFragmentManager().beginTransaction();

                    transaccion.replace(R.id.fragment_container, new Explorar(), "explorar");
                    new Principal();
                    transaccion.addToBackStack(null);
                    transaccion.commit();

                }
                break;


            case R.id.cerrarSesion:
                finish();
                usuario = null;
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);


            case R.id.exit:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void explorar(View view) {

        if (getFragmentManager().findFragmentByTag("explorar") == null) {
            transaccion = getFragmentManager().beginTransaction();

            transaccion.replace(R.id.fragment_container, new Explorar(usuario), "explorar");
            new Principal();
            transaccion.addToBackStack(null);
            transaccion.commit();

        }

    }
    public void irAEliminar(View view){
        if (getFragmentManager().findFragmentByTag("eliminar") == null) {
            transaccion = getFragmentManager().beginTransaction();

            transaccion.replace(R.id.fragment_container, new ListaEliminar(), "eliminar");
            new Principal();
            transaccion.addToBackStack(null);
            transaccion.commit();

        }

    }

    public void crearActividad(View view){
        if (getFragmentManager().findFragmentByTag("nuevaActividad") == null) {
            transaccion = getFragmentManager().beginTransaction();

            transaccion.replace(R.id.fragment_container, new NuevaActividad(usuario), "nuevaActividad");
            new Principal();
            transaccion.addToBackStack(null);
            transaccion.commit();

        }

    }

}



