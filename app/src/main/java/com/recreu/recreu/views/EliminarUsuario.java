package com.recreu.recreu.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpDelete;
import com.recreu.recreu.utilities.SystemUtilities;

import cl.recreu.recreu.taller_android_bd.R;

public class EliminarUsuario extends Fragment implements View.OnClickListener {
    private TextView tv;
    private Usuario usuario;
    private String URL_GET="http://10.0.2.2:8080/javaee/usuarios/";

    public EliminarUsuario(Usuario usu) {
        // Required empty public constructor
        this.usuario=usu;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eliminar_usuario, container, false);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        tv = (TextView)getActivity().findViewById(R.id.segurisimo);
        tv.setText("¿Está seguro de eliminar a "+usuario.getPrimerNombre()+" ?");
        Button but = (Button)getActivity().findViewById(R.id.OKEY);
        but.setOnClickListener(this);
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
        if (su.isNetworkAvailable()) {
            try {
                new HttpDelete(getActivity().getApplicationContext()).execute(URL_GET + usuario.getUsuarioId());
                FragmentTransaction transaccion = getFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragment_container, new ListaEliminar(), "eliminar");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}