package com.recreu.recreu;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.views.Explorar;
import com.recreu.recreu.views.PerfilUsuario;
import com.recreu.recreu.views.Principal;
import com.recreu.recreu.views.detalleActividad;
import com.recreu.recreu.views.notificacion;

import cl.recreu.recreu.taller_android_bd.R;


public class NotificationView extends AppCompatActivity {
    private Usuario usuario;
    private Actividad actividad;
    private Button botonVerDetalle;
    private String actividadID;
    private FragmentTransaction transaccion;
    private View vista;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification_view);
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        transaccion = getFragmentManager().beginTransaction();

        usuario = (Usuario) getIntent().getExtras().getSerializable("usuarioN");
        System.out.println("usuario: " + usuario.getPrimerNombre());
        transaccion.replace(R.id.fragment_container2, new notificacion(usuario));
        transaccion.addToBackStack(null);
        transaccion.commit();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        nm.cancel(getIntent().getExtras().getInt("notificationID"));

        System.out.println("ESTOY EN LISTA");
        transaccion = getFragmentManager().beginTransaction();
        String latitud = "7";
        String longitud = "550";
        transaccion.replace(R.id.fragment_container3, new Explorar(usuario, true, latitud, longitud), "explorar");
        transaccion.addToBackStack(null);
        transaccion.commit();

    }



}

//geo fix -70,68908214569092 -33,45029578663348



