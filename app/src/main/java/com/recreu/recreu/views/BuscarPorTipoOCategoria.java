package com.recreu.recreu.views;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.recreu.recreu.Modelos.Categoria;
import com.recreu.recreu.Modelos.Tipo;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpGet;
import com.recreu.recreu.utilities.AccesoDirecto;
import com.recreu.recreu.utilities.JsonHandler;
import com.recreu.recreu.utilities.SystemUtilities;

import cl.recreu.recreu.taller_android_bd.R;

/**
 * Created by ginebra on 10-06-16.
 */
public class BuscarPorTipoOCategoria extends ListFragment implements View.OnClickListener {


    private ListView ObjetoCheckbox;
    private BroadcastReceiver br = null;
    private ArrayList <String> listaSeleccionados;
    private Usuario usuario;
    private List<String> listaDatosCheckbox;
    private String URL_GET;
    private boolean tipoBusqueda;
    private Categoria[] CategoriasLista;
    private Tipo[] TiposLista;
    private Button botonOK;

    public BuscarPorTipoOCategoria(Usuario usuSesion, boolean TipoOCategoria) {  // true Categoria
        this.usuario=usuSesion;
        this.tipoBusqueda=TipoOCategoria;
        if (TipoOCategoria)this.URL_GET=(new AccesoDirecto()).getURL()+"categorias";
        else this.URL_GET=(new AccesoDirecto()).getURL()+"tipos";
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

  //  @Override
 //   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
  //  super.onCreate(savedInstanceState);
  //     return inflater.inflate(R.layout.buscarportipoocategoria, container, false);
  //  }


    @Override
    public void onListItemClick(ListView arg0, View v, int position, long arg3) {
        CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox1);
        TextView nombreCategoria = (TextView) v.findViewById(R.id.textView1);
        cb.performClick();
        if (cb.isChecked()) {
            listaSeleccionados.add(nombreCategoria.getText().toString());
        } else if (!cb.isChecked()) {
            listaSeleccionados.remove(nombreCategoria.getText().toString());
        }
    }


    @Override
    public void onResume() {
       if (tipoBusqueda) {       // si es tipo categoria
        IntentFilter intentFilter = new IntentFilter("httpData");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                CategoriasLista = jh.getCategorias(intent.getStringExtra("data"));
                String[] stringCategorias = new String[CategoriasLista.length];

                for (int i = 0; i < CategoriasLista.length; i++) {
                    stringCategorias[i] = ""+CategoriasLista[i].getNombreCategoria()+"";
                }
                System.out.println(stringCategorias);
                adaptadorCheckbox checkBoxAdaptador = new adaptadorCheckbox(getActivity(), stringCategorias);
                setListAdapter(checkBoxAdaptador);

               // TODO: insertar boton independiente ( flotante - abajo) de ListFragment ( ya que sino se repite )
             //   RelativeLayout espacioBoton = (RelativeLayout) getView().findViewById(R.id.buttonContainer);
           //     Button boton=new Button(getContext());
           //     espacioBoton.addView(boton);


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

    }else { // BusquedaporTipo
           IntentFilter intentFilter = new IntentFilter("httpData");
           br = new BroadcastReceiver() {
               @Override
               public void onReceive(Context context, Intent intent) {
                   JsonHandler jh = new JsonHandler();
                  TiposLista = jh.getTipos(intent.getStringExtra("data"));
                   String[] stringTipos = new String[TiposLista.length];

                   for (int i = 0; i < TiposLista.length; i++) {
                       stringTipos[i] = ""+TiposLista[i].getNombreTipo()+"";
                   }
                   System.out.println(stringTipos);
                   adaptadorCheckbox checkBoxAdaptador = new adaptadorCheckbox(getActivity(), stringTipos);
                   setListAdapter(checkBoxAdaptador);

                   // creo boton luego de crear la list de checkbox :CC
                   //   RelativeLayout espacioBoton = (RelativeLayout) getView().findViewById(R.id.buttonContainer);
                   //     Button boton=new Button(getContext());
                   //     espacioBoton.addView(boton);


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

       }
        super.onResume();
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
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(tipoBusqueda) transaction.replace(R.id.fragment_container, new Explorar(usuario,listaSeleccionados,true),"verPorCategoria");
        if(!tipoBusqueda) transaction.replace(R.id.fragment_container, new Explorar(usuario,listaSeleccionados,true),"verPorTipo");

        new Principal();
        transaction.addToBackStack(null);
        transaction.commit();
    }
}