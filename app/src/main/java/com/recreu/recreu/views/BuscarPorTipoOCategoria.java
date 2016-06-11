package com.recreu.recreu.views;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.recreu.recreu.Modelos.Categoria;
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
    private Button botonOK;

    public BuscarPorTipoOCategoria(Usuario usuSesion, boolean TipoOCategoria) {  // true Categoria
        this.usuario=usuSesion;
        this.tipoBusqueda=TipoOCategoria;
        if (TipoOCategoria)this.URL_GET=(new AccesoDirecto()).getURL()+"categorias";
        else this.URL_GET=(new AccesoDirecto()).getURL()+"ver como cresta obtener todos los tipos";
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

   // @Override
  //  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //   super.onCreate(savedInstanceState);
        //   setContentView(R.layout.buscarporcategoria);
   //     return inflater.inflate(R.layout.buscarporcategoria, container, false);
  //  }


 /*   @Override
    public void onViewStateRestored(Bundle savedInstanceState) {

        botonOK = (Button)getView().findViewById(R.id.botonOk);
        ObjetoCheckbox = (ListView) getView().findViewById(R.id.listaBox);
      //  packageManager = getActivity().getPackageManager();
     //   final List <PackageInfo> packageList = packageManager
     //           .getInstalledPackages(PackageManager.GET_META_DATA); // all apps in the phone
     //   final List <PackageInfo> packageList1 = packageManager
     //           .getInstalledPackages(0);



        adaptadorCheckbox Adapter = new adaptadorCheckbox(listaDatosCheckbox);
        ObjetoCheckbox.setAdapter(Adapter);
        ObjetoCheckbox.setOnItemClickListener(this);
        botonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"" + listaStringChecked,Toast.LENGTH_LONG).show();
            }
        });



    super.onViewStateRestored(savedInstanceState);
}*/




    @Override
    public void onListItemClick(ListView arg0, View v, int position, long arg3) {
        CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox1);    // estan en xml buscarporcategoria
        TextView nombreCategoria = (TextView) v.findViewById(R.id.textView1);
        cb.performClick();
        if (cb.isChecked()) {

            listaSeleccionados.add(nombreCategoria.getText().toString());
        } else if (!cb.isChecked()) {
            listaSeleccionados.remove(nombreCategoria.getText().toString());
        }

      //  FragmentTransaction transaction = getFragmentManager().beginTransaction();
      //  transaction.replace(R.id.fragment_container, new detalleActividad(actividad,usuario),"detalleActi");
        //  new Principal();
     //   transaction.addToBackStack(null);
      //  transaction.commit();

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

    }else {


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
        if(tipoBusqueda)
        transaction.replace(R.id.fragment_container, new Explorar(usuario,listaSeleccionados,true),"detalleActi");
        new Principal();
        transaction.addToBackStack(null);
        transaction.commit();
    }
}