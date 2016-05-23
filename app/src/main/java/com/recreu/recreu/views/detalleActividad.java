package com.recreu.recreu.views;

import android.app.Fragment;
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
import com.recreu.recreu.utilities.JsonHandler;
import com.recreu.recreu.utilities.SystemUtilities;

import cl.recreu.recreu.taller_android_bd.R;

public class detalleActividad extends Fragment implements View.OnClickListener {
    private TextView cajaTitutlo;
    private TextView cajaCuerpo;
    private TextView cajaRequisitos;
    private Actividad actividad;
    private boolean participando;
    private int resultadoConsulta;
    private BroadcastReceiver br = null;
    private Button botonPC;
    private Usuario usuario;
    private final String URL_PUT_ACTIVIDAD = "http://10.0.2.2:8080/javaee/";
    private Usuario[] listaUsuarios;

        public detalleActividad(Actividad act, Usuario usu) {
        this.actividad=act;
        this.usuario=usu;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detalle_actividad, container, false);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {

        cajaTitutlo = ((TextView) getView().findViewById(R.id.TextViewTitulo)); //  -> estoy seteando
        cajaTitutlo.setText(actividad.getTitulo());
        cajaCuerpo = ((TextView) getView().findViewById(R.id.TextViewCuerpo)); //  -> estoy seteando
        cajaCuerpo.setText(actividad.getCuerpo());
        cajaRequisitos = ((TextView) getView().findViewById(R.id.TextViewRequisitos)); //  -> estoy seteando
        cajaRequisitos.setText(actividad.getRequerimientos());
        botonPC = ((Button) getView().findViewById(R.id.botonPC));
        botonPC.setOnClickListener(this);

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        String URL_USUARIOS_EN_ACTIVIDAD=URL_PUT_ACTIVIDAD+"actividades/"+actividad.getActividadId()+"/usuarios";
        IntentFilter intentFilter2 = new IntentFilter("httpData");

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                listaUsuarios = jh.getIdesUsuariosEnAct(intent.getStringExtra("data"));
                if (listaUsuarios != null){
                    participando = false;
                    for (int i = 0; i < listaUsuarios.length; i++) {
                        if (listaUsuarios[i].getUsuarioId() == usuario.getUsuarioId())
                            participando = true;
                    }
                    if (participando == true) {
                        botonPC.setText("CANCELAR PARTICIPACIÓN");                      // HU: CANCELAR PARTICIPACION
                    } else {

                        resultadoConsulta = listaUsuarios.length;
                        if (actividad.getMaximoPersonas() > resultadoConsulta) {         //  HU : PARTICIPAR DE ACTIVIDAD
                            botonPC.setText("PARTICIPAR");
                        } else {
                            botonPC.setText("SIN CUPOS");
                            botonPC.setClickable(false);
                        }
                    }
                }
            }
        };
        getActivity().registerReceiver(br, intentFilter2);
        SystemUtilities su2 = new SystemUtilities(getActivity().getApplicationContext());
        if (su2.isNetworkAvailable()) {
            try {
                new HttpGet(getActivity().getApplicationContext()).execute(URL_USUARIOS_EN_ACTIVIDAD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }           super.onResume();
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

        //    int ideUsuario = Usuario.getId();
        String URL_PARTICIPACION = URL_PUT_ACTIVIDAD+"usuarios/"+usuario.getUsuarioId()+"/actividades/"+actividad.getActividadId()+"";

        // DELETE: borro fila usuario y actividad ( SI ES ORGANIZADOR HAY QUE "NOTIFICAR AL RESTO" y pedirle a otro que sea organizador
        if ( participando == true){
            try {
                SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
                if (su.isNetworkAvailable()) {
                    try {
                        AsyncTask resp = new HttpDelete(getActivity().getApplicationContext()).execute(URL_PARTICIPACION);
                        Toast.makeText(getActivity(), " Cancelando participacion  ...   ", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
            }


            // POST : AGREGAR FILA A USUARIO_ACTIVIDAD
        }else{
            String nuevaParticipacion = "{\"usuarioId\":\""+usuario.getUsuarioId()+
                    ",\"actividadId\":"+actividad.getActividadId()+"}";
            try {
                SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
                if (su.isNetworkAvailable()) {
                    try {
                        AsyncTask resp = new HttpPost(getActivity().getApplicationContext()).execute(nuevaParticipacion,URL_PARTICIPACION);
                        Toast.makeText(getActivity(), " Confirmando Participacióm ...   ", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
            }

        }
    }
}