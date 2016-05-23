package com.recreu.recreu.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpDelete;
import com.recreu.recreu.controllers.HttpGet;
import com.recreu.recreu.utilities.JsonHandler;
import com.recreu.recreu.utilities.SystemUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import cl.recreu.recreu.taller_android_bd.R;

public class ListaEliminar extends ListFragment{

    private String URL_GET="http://10.0.2.2:8080/javaee/usuarios/";



    private BroadcastReceiver br = null;
    private Usuario[] usuariosLista;
    private Usuario usuBorrar;

    public ListaEliminar() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eliminar_usuario, container, false);
    }*/

    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("httpData");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                String datoJson= intent.getStringExtra("data");
                System.out.println("DATO JSON "+datoJson);
                usuariosLista = jh.getUsuarios(datoJson);
                String[] usuariosString = new String[usuariosLista.length]; //  se va a BORRAR
                // List<String> values=new ArrayList<String>();


                for (int i=0;i<usuariosLista.length;i++){
                    usuariosString[i]=" "+usuariosLista[i].getPrimerNombre()+" "+ usuariosLista[i].getApellidoPaterno();
                    //       values.add(actividadesLista[i].getTitulo());

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1 ,usuariosString);
                setListAdapter(adapter);
            }
        };

        getActivity().registerReceiver(br, intentFilter);
        SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
        if (su.isNetworkAvailable()) {
            try {
                new HttpGet(getActivity().getApplicationContext()).execute(URL_GET);
                System.out.println("ya pedí los usuarios");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) { // ACA VEO LO QUE SETEO A DETALLEACTIVIDAD

        usuBorrar = usuariosLista[position];

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new EliminarUsuario( usuBorrar), "detalleBorrar");
        transaction.addToBackStack(null);
        transaction.commit();
        System.out.println("Estoy en onclickk, con usuario id " + usuBorrar.getUsuarioId());
    }




    @Override
    public void onPause() {
        if (br != null) {
            getActivity().unregisterReceiver(br);
        }
        super.onPause();
    }



}








