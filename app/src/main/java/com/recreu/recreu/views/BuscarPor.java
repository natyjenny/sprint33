package com.recreu.recreu.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.utilities.AccesoDirecto;

import cl.recreu.recreu.taller_android_bd.R;

/**
 * Created by ginebra on 10-06-16.
 */
public class BuscarPor extends Fragment implements View.OnClickListener {
    private View vistaBuscarpor;
    private Usuario usuario;
    private Button Portipo;
    private Button PorCategoria;

    private BroadcastReceiver br = null;


    public BuscarPor(Usuario usu) {
        this.usuario = usu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vistaBuscarpor= inflater.inflate(R.layout.buscarpor, container, false);
        Portipo = ((Button)vistaBuscarpor.findViewById(R.id.botonTipo));
        Portipo.setOnClickListener(this);
        PorCategoria=(Button)((Button)vistaBuscarpor.findViewById(R.id.botonCategoria));
        PorCategoria.setOnClickListener(this);
        vistaBuscarpor.findViewById(R.id.botonFecha).setOnClickListener(this);
        return vistaBuscarpor;

    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onPause() {
        if (br != null) {
            getActivity().unregisterReceiver(br);
        }
        super.onPause();
    }


    @Override
    public void onClick(View view) {
        FragmentTransaction transaccion;
        switch (view.getId()) {
            case R.id.botonTipo:
                    System.out.println("Aprete boton tipo");
                transaccion = getFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragment_container, new BuscarPorTipoOCategoria(usuario,false), "BuscarPorTipo");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();
                break;

            case R.id.botonCategoria:
                System.out.println("Aprete boton Categoria");
                transaccion = getFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragment_container, new BuscarPorTipoOCategoria(usuario,true), "BuscarCategoria");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();

                break;
            case R.id.botonFecha:
                System.out.println("Aprete boton fecha");
                transaccion = getFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragment_container, new PorFecha(usuario), "BuscarFecha");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();
                break;

        }
    }

}
