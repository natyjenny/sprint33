package com.recreu.recreu.views;

import android.os.Bundle;
import android.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Usuario;

import cl.recreu.recreu.taller_android_bd.R;


public class Inicio extends Fragment {
    private Usuario usuario;
    private TextView tv;
    private TextView bienv;
    private Button eliminar;
    private Button buscar;

    public Inicio(Usuario usu) {
        this.usuario = usu;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.principal, container, false);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {

        tv = (TextView) getActivity().findViewById(R.id.nombreUsuario);
        tv.setText(usuario.getPrimerNombre());
        bienv = (TextView) getActivity().findViewById(R.id.bienvenida);
        if (!usuario.isSexo()) bienv.setText("Bienvenida");
        eliminar = (Button) getActivity().findViewById(R.id.botonEliminarUsuario);


        if (!usuario.isEsAdministrador()) {
            // compartir espacio entre los dos botones
            eliminar.setVisibility(View.INVISIBLE);
        }else{
            // dejar solo boton buscar

        }

        super.onViewStateRestored(savedInstanceState);
    }

}


