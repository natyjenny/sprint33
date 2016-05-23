package com.recreu.recreu.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cl.recreu.recreu.taller_android_bd.R;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpGet;
import com.recreu.recreu.utilities.JsonHandler;
import com.recreu.recreu.utilities.SystemUtilities;

import java.util.ArrayList;
import java.util.List;



public class Explorar extends ListFragment {

    private BroadcastReceiver br = null;
    private final String URL_GET = " http://10.0.2.2:8080/javaee/actividades";
    private Actividad[] actividadesLista;
    private Actividad actividad;
    private Usuario usuario;

    public Explorar() {
    }

    public Explorar(Usuario usu) {                    //CONSTRUCTOR CON MODELO USUARIO
        this.usuario=usu;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("httpData");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                actividadesLista = jh.getActividades(intent.getStringExtra("data"));
                String[] titulosString = new String[actividadesLista.length];
                String[] fechasString = new String[actividadesLista.length];


                for (int i=0;i<actividadesLista.length;i++){
                    titulosString[i]=" "+actividadesLista[i].getTitulo()+" ";
                  //  fechasString[i]=" "+actividadesLista[i].getFechaInicio()+" "+ (actividadesLista[i].getCategoria().getNombreCategoria()).toUpperCase();   // AQUI PONER CATEGORIA :C
                    fechasString[i]=" "+actividadesLista[i].getFechaInicio()+" ";
                }



                MyAdapter myAdapter = new MyAdapter(getActivity(), titulosString, fechasString);
                setListAdapter(myAdapter);

                // se adapta el string al campo del fragment al estilo lista  simple /                     SE VA A BORRARRR
           //     ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1 ,actividadesString);
             //   setListAdapter(adapter);
            }
        };

        getActivity().registerReceiver(br, intentFilter);
        SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
        if (su.isNetworkAvailable()) {
            try {
                new HttpGet(getActivity().getApplicationContext()).execute(URL_GET);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) { // ACA VEO LO QUE SETEO A DETALLEACTIVIDAD

        actividad=actividadesLista[position];
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new detalleActividad(actividad,usuario),"detalleActi");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPause() {
        if (br != null) {
            getActivity().unregisterReceiver(br);
        }
        super.onPause();
    }




}

