package com.recreu.recreu.views;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.utilities.AccesoDirecto;



import cl.recreu.recreu.taller_android_bd.R;


public class PerfilUsuario extends Fragment {
    private final String URL_POST = (new AccesoDirecto()).getURL() + "actividades";
    private Context c;
    private Usuario usuario;
    private View vistaPerfil;
    private TextView nombrePerfil;
    private TextView correoPerfil;
    private TextView carreraPerfil;
    private TextView cumpleanosPerfil;
    private TextView interesesPerfil;
    private TextView telefonoPerfil;



        public PerfilUsuario(Usuario usu) {
            this.usuario = usu;
        }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
        savedInstanceState){

            vistaPerfil = inflater.inflate(R.layout.perfil_usuario, container, false);
            nombrePerfil=(TextView)vistaPerfil.findViewById(R.id.nombrePerfil);
            correoPerfil=(TextView)vistaPerfil.findViewById(R.id.correoPerfil);
            carreraPerfil=(TextView)vistaPerfil.findViewById(R.id.carreraPerfil);
            cumpleanosPerfil=(TextView)vistaPerfil.findViewById(R.id.cumpleanosPerfil);
            interesesPerfil=(TextView)vistaPerfil.findViewById(R.id.interesesPerfil);
            telefonoPerfil=(TextView)vistaPerfil.findViewById(R.id.telefonoPerfil);

           //Set
            nombrePerfil.setText(usuario.getPrimerNombre()+" "+usuario.getApellidoPaterno()+ "\n"+ usuario.getApellidoMaterno());
            correoPerfil.setText(usuario.getCorreo());
            //carreraPerfil.setText(usuario.getCarrera().getNombreCarrera());
            cumpleanosPerfil.setText(usuario.getFechaNacimiento());
            //interesesPerfil.setText(usuario.getIntereses());
            //telefonoPerfil.setText(usuario.getNumeroTelefono());
            return vistaPerfil;
        }


        @Override
        public void onViewStateRestored (Bundle savedInstanceState){
            super.onViewStateRestored(savedInstanceState);
        }
}