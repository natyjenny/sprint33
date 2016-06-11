package com.recreu.recreu.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.recreu.recreu.Modelos.Usuario;

import cl.recreu.recreu.taller_android_bd.R;

/**
 * Created by ginebra on 10-06-16.
 */
public class BusquedaPorTipo extends Fragment implements View.OnClickListener {

        private View vistaBuscarporTipo;
        private BroadcastReceiver br = null;
        private Usuario usuario;

        public BusquedaPorTipo(Usuario usu) {
            this.usuario = usu;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            vistaBuscarporTipo= inflater.inflate(R.layout.buscaportipo, container, false);

            return vistaBuscarporTipo;

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

        }

}
