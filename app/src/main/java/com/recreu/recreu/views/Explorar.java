package com.recreu.recreu.views;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import cl.recreu.recreu.taller_android_bd.R;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Tipo;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpGet;
import com.recreu.recreu.utilities.AccesoDirecto;
import com.recreu.recreu.utilities.JsonHandler;
import com.recreu.recreu.utilities.SystemUtilities;

import java.util.ArrayList;
import java.util.Iterator;


public class Explorar extends ListFragment {

    private BroadcastReceiver br = null;
    private String URL_GET;
    private ArrayList <Actividad> actividadesLista = new ArrayList<Actividad>();
    private ArrayList <String> listaFiltro=null;
    private Actividad actividad;
    private Usuario usuario;
    boolean tipodeFiltro;
    boolean ConOSinFiltro=false;

    public Explorar() {
        this.URL_GET=(new AccesoDirecto()).getURL()+"actividades/?var=1&nofinalizadas&dato=2";
    }

    public Explorar(Usuario usu) {
        this.usuario=usu;
        this.URL_GET=(new AccesoDirecto()).getURL()+"actividades?var=1&nofinalizadas&dato=2";
    }

    public Explorar(Usuario usu, int idUsuario, boolean organizador) {
        this.usuario=usu;
        if (organizador)this.URL_GET=(new AccesoDirecto()).getURL()+"usuarios/"+idUsuario+"/actividades/?organizador";
        else this.URL_GET=(new AccesoDirecto()).getURL()+"usuarios/"+idUsuario+"/actividades";
    }

    public Explorar(Usuario usu, ArrayList<String> listaFiltro, boolean filtro) { // filtro por categorias es verdadero
        this.usuario=usu;
        this.listaFiltro=listaFiltro;
        this.tipodeFiltro=filtro;
        this.ConOSinFiltro=true;
        this.URL_GET=(new AccesoDirecto()).getURL()+"actividades/?var=1&nofinalizadas&dato=2";
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
                boolean pasaONoFiltro;

             if(ConOSinFiltro) {        // caso en que si hay filtros asociados
                 ArrayList<Actividad> auxi = new ArrayList<Actividad>();
                 if(tipodeFiltro) { // caso en que se filtra por categoría
                     Iterator<Actividad> iterator = actividadesLista.iterator();

                     while (iterator.hasNext()) {
                         Actividad acti = iterator.next();
                         Iterator<String> iterator2 = listaFiltro.iterator();
                         pasaONoFiltro = false;

                         while (iterator2.hasNext()) {
                             String nombreCategoria = iterator2.next();
                             if ((nombreCategoria.toString()).equals(acti.getTipo().getCategoria().getNombreCategoria())) {
                                 pasaONoFiltro = true;
                                 }
                             }
                         if(pasaONoFiltro)
                             auxi.add(acti);
                     }
                       }else { // caso en que se filtra por tipo
                     Iterator<Actividad> iterator = actividadesLista.iterator();
                     while (iterator.hasNext()) {
                         Actividad acti = iterator.next();
                         Iterator<String> iterator2 = listaFiltro.iterator();
                         pasaONoFiltro = false;

                         while (iterator2.hasNext()) {
                             String nombreTipo = iterator2.next();
                             if ((nombreTipo.toString()).equals(acti.getTipo().getNombreTipo())) {
                                 pasaONoFiltro = true;
                                }
                             }
                         if(pasaONoFiltro)
                             auxi.add(acti);
                         }
                     }
                   actividadesLista=auxi;
             }
                 String[] titulosString = new String[actividadesLista.size()];
                 String[] fechasString = new String[actividadesLista.size()];
                 Tipo[] tiposArray = new Tipo[actividadesLista.size()];

                 for (int i = 0; i < actividadesLista.size(); i++) {
                     titulosString[i] = " " + actividadesLista.get(i).getTitulo() + " ";
                     String fecha, hora, resto = " " + actividadesLista.get(i).getFechaInicio() + " ";
                     fecha = resto.substring(0, 11);
                     String anio, mes, dia, resto2 = fecha;
                     anio = resto2.substring(1, 5);
                     mes = resto2.substring(6, 8);
                     dia = resto2.substring(9, 11);
                     hora = resto.substring(12, 17);
                     fechasString[i] = " Fecha: " + dia + ":" + mes + ":" + anio + " Hora: " + hora;
                     tiposArray[i] = actividadesLista.get(i).getTipo();
                 }
                ExplorarAdaptador explorarAdaptador = new ExplorarAdaptador(getActivity(), titulosString, fechasString, tiposArray);
                setListAdapter(explorarAdaptador);
            }
        };

        getActivity().registerReceiver(br, intentFilter);
        SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
        if (su.isNetworkAvailable()) {
            try {
                new HttpGet(getActivity().getApplicationContext()).execute(URL_GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, View v, int posicion, long id) {
        actividad=actividadesLista.get(posicion);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new detalleActividad(actividad,usuario),"detalleActi");
        new Principal();
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

