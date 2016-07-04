package com.recreu.recreu.views;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.FragmentTransaction;
import android.widget.Button;

import com.recreu.recreu.Modelos.Usuario;
import cl.recreu.recreu.taller_android_bd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class notificacion extends Fragment implements View.OnClickListener {
    private View notificacion;
    private Usuario usuario;
    private Button boton;

    public notificacion(Usuario usuario) {
       this.usuario=usuario;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificacion= inflater.inflate(R.layout.notificacion, container, false);
        boton=(Button)notificacion.findViewById(R.id.botoooon2);
        boton.setOnClickListener(this);
        return notificacion;
    }

    @Override
    public void onClick(View v) {
        System.out.println("ESTOY EN SALIR");
        Intent i = new Intent(getActivity(), Principal.class);
        i.putExtra("usuario", usuario);

        startActivity(i);
        getActivity().finish();
    }
}
