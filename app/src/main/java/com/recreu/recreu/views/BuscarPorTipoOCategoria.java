package com.recreu.recreu.views;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.SparseBooleanArray;
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
public class BuscarPorTipoOCategoria extends Fragment implements View.OnClickListener {


    private ListView ObjetoCheckbox;
    private BroadcastReceiver br4 = null;
    private ArrayList <String> listaSeleccionados = new ArrayList<String>();
    private String[] datosLista;
    private Usuario usuario;
    private List<String> listaDatosCheckbox;
    private String URL_GET;
    private boolean tipoBusqueda;
    private Categoria[] CategoriasLista;
    private Tipo[] TiposLista;
    private Button botonSiguiente;
    private ListView listaBox;

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

    @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      return inflater.inflate(R.layout.probando, container, false);
    }


    public void onListItemClick2(ListView arg0, View v, int position, long arg3) {

        CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox1);
        TextView nombreCategoria = (TextView) v.findViewById(R.id.textView1);
        System.out.println("APRETEE :"+nombreCategoria.getText().toString());
        cb.performClick();
        if (cb.isChecked()) {
            listaSeleccionados.add(nombreCategoria.getText().toString());
        } else if (!cb.isChecked()) {
            listaSeleccionados.remove(nombreCategoria.getText().toString());
        }
    }


    @Override
    public void onResume() {
        listaBox = (ListView)getView().findViewById(R.id.listita);
        botonSiguiente=(Button)getView().findViewById(R.id.botonSiguiente);
        botonSiguiente.setOnClickListener(this);

       if (tipoBusqueda) {       // si es tipo categoria
        IntentFilter intentFilter = new IntentFilter("httpData");
        br4 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                CategoriasLista = jh.getCategorias(intent.getStringExtra("data"));
                String[] stringCategorias = new String[CategoriasLista.length];

                for (int i = 0; i < CategoriasLista.length; i++) {
                    stringCategorias[i] = ""+CategoriasLista[i].getNombreCategoria()+"";
                }
             //   System.out.println(stringCategorias);
                datosLista=stringCategorias;

                adaptadorCheckbox checkBoxAdaptador = new adaptadorCheckbox(getActivity(), stringCategorias);
                listaBox.setAdapter(checkBoxAdaptador);

            }
        };
        getActivity().registerReceiver(br4, intentFilter);
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
           br4 = new BroadcastReceiver() {
               @Override
               public void onReceive(Context context, Intent intent) {
                   JsonHandler jh = new JsonHandler();
                  TiposLista = jh.getTipos(intent.getStringExtra("data"));
                   String[] stringTipos = new String[TiposLista.length];
                   for (int i = 0; i < TiposLista.length; i++) {
                       stringTipos[i] = ""+TiposLista[i].getNombreTipo()+"";
                   }
                  // System.out.println(stringTipos);
                   datosLista=stringTipos;

                   adaptadorCheckbox checkBoxAdaptador = new adaptadorCheckbox(getActivity(), stringTipos);
                   listaBox.setAdapter(checkBoxAdaptador);

               }
           };
           getActivity().registerReceiver(br4, intentFilter);
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
        if (br4 != null) {
            getActivity().unregisterReceiver(br4);
        }
        super.onPause();
    }


    @Override
    public void onClick(View view) {

        //System.out.println(" entre al listener de SIGUIENTE");

        for (int i = 0; i < listaBox.getAdapter().getCount(); i++) {
            if (listaBox.getAdapter().getItem(i) !=null) {
               //System.out.println(" DATOSLISTA en i: "+datosLista[i]);
                String truet ="true";
                if(truet.equals(listaBox.getAdapter().getItem(i).toString())) {
                //    System.out.println(" Si true == "+listaBox.getAdapter().getItem(i).toString());

                    if (listaSeleccionados != null)
                        listaSeleccionados.add("" + datosLista[i] + "");
                }
            }
        }
        System.out.println(" que llevo seleccionado:"+listaSeleccionados);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(tipoBusqueda) transaction.replace(R.id.fragment_container, new Explorar(usuario,listaSeleccionados,true),"verPorCategoria");
        if(!tipoBusqueda) transaction.replace(R.id.fragment_container, new Explorar(usuario,listaSeleccionados,false),"verPorTipo");

        new Principal();
        transaction.addToBackStack(null);
        transaction.commit();
    }
}