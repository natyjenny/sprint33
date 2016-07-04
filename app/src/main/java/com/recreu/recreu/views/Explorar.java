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

    private BroadcastReceiver br3 = null;
    private String URL_GET;
    private int actividadID;
    private ArrayList <Actividad> actividadesLista = new ArrayList<Actividad>();
    private ArrayList <String> listaFiltro=null;
    private Actividad actividad;
    private int confirmadas;
    private Usuario usuario;
    boolean tipodeFiltro,ConOSinFiltro=false,notificacion = false;



    public Explorar() {
        this.URL_GET=(new AccesoDirecto()).getURL()+"actividades/?var=1&nofinalizadas&dato=2";
    }

    public Explorar(Usuario usu) {
        this.usuario=usu;
        this.URL_GET=(new AccesoDirecto()).getURL()+"actividades?var=1&nofinalizadas&dato=2";
    }
    //Función para explorar desde un rango de fechas.
    public Explorar(Usuario usu, String url){
        this.usuario=usu;
        this.URL_GET=(new AccesoDirecto()).getURL()+url;
    }

    // para ver actividades confirmadas ( que aun no pasan )       // ACA FALTA CONSULTAA
    public Explorar(Usuario usuarioSesion, int confirmadas){
        this.usuario=usuarioSesion;
        this.confirmadas=confirmadas;
        this.URL_GET=(new AccesoDirecto()).getURL()+"usuarios/"+usuario.getId()+"/actividades?nofinalizadas";
    }

    // este constructor viene desde notificacion
    public Explorar(Usuario usuario,boolean notif, String latitud, String longitud){
        this.usuario=usuario;
        this.notificacion=notif;
        this.URL_GET=((new AccesoDirecto()).getURL() + "actividades/?latitud="+latitud+"&longitud="+longitud+"&ladocuadrado=60&minutos=35");
    }

      // actividades en que he participado y que donde soy organizador
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
        br3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                if(intent.getStringExtra("data")!=null){
                actividadesLista = jh.getActividades(intent.getStringExtra("data"));
                boolean pasaONoFiltro;

          /*      // caso en que viene desde , salto directamente a ver detallenotificación
                if(notificacion){
                    actividad=actividadesLista.get(actividadID);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new detalleActividad(actividad,usuario),"detalleActi");
                    new Principal();
                    transaction.addToBackStack(null);
                    transaction.commit();

                }

          */


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
            }}
        };

        getActivity().registerReceiver(br3, intentFilter);
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
        if (notificacion)transaction.replace(R.id.fragment_container3, new detalleActividad(actividad,usuario),"detalleActi");
        else transaction.replace(R.id.fragment_container, new detalleActividad(actividad,usuario),"detalleActi");
        new Principal();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPause() {
        if (br3 != null) {
            getActivity().unregisterReceiver(br3);
        }
        super.onPause();
    }
}

