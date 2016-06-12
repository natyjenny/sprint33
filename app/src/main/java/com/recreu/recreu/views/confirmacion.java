package com.recreu.recreu.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpDelete;
import com.recreu.recreu.controllers.HttpGet;
import com.recreu.recreu.controllers.HttpPost;
import com.recreu.recreu.utilities.AccesoDirecto;
import com.recreu.recreu.utilities.JsonHandler;
import com.recreu.recreu.utilities.SystemUtilities;

import cl.recreu.recreu.taller_android_bd.R;
// TODO: agregar mensaje de confirmación despues de participar en acti, crear acti
/**
 * Created by ginebra on 09-06-16.
 */
public class confirmacion  extends Fragment implements View.OnClickListener {
    private Button botonOk;
    private Usuario usuario;

    public confirmacion( Usuario usu) {
        this.usuario=usu;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.confirmacion, container, false);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        botonOk = ((Button) getView().findViewById(R.id.botonOk));
        botonOk.setOnClickListener(this);
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
       // transaction.replace(R.id.fragment_container, new Inicio(usuario), "principal");
      //  transaction.addToBackStack(null);

       // transaction.commit();
    }
}
