package com.recreu.recreu.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;

import android.view.LayoutInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Usuario;

import org.w3c.dom.Text;

import cl.recreu.recreu.taller_android_bd.R;


public class Inicio extends Fragment {
    private Usuario usuario;
    private TextView tv;
    private TextView bienv;
    private Button eliminar;

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
        return inflater.inflate(R.layout.inicio, container, false);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {

        tv = (TextView) getActivity().findViewById(R.id.nombreUsuario);
        tv.setText(usuario.getPrimerNombre());
        bienv = (TextView) getActivity().findViewById(R.id.bienvenida);
        if (!usuario.isSexo()) bienv.setText("Bienvenida");
        eliminar = (Button) getActivity().findViewById(R.id.botonEliminarUsuario);
        if (!usuario.isEsAdministrador()) eliminar.setVisibility(View.INVISIBLE);

        super.onViewStateRestored(savedInstanceState);
    }

}


