package com.recreu.recreu.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpPost;
import com.recreu.recreu.utilities.AccesoDirecto;
import com.recreu.recreu.utilities.SystemUtilities;

import cl.recreu.recreu.taller_android_bd.R;

/**
 * Created by ginebra on 03-07-16.
 */
public class Reporte  extends Fragment implements View.OnClickListener{
    private String URL_POST;
    private Button btnreporteOk;
    private Usuario UsuarioSesion, UsuarioPerfil;
    private EditText descripcion;
    private TextView nombreReportado;
    private Actividad actividad;
    private AsyncTask resp;



    public Reporte(Usuario UsuarioSesion, Usuario UsuarioPerfil,Actividad actividad) {
        this.URL_POST = (new AccesoDirecto()).getURL();
        this.UsuarioPerfil = UsuarioPerfil;
        this.UsuarioSesion = UsuarioSesion;
        this.actividad=actividad;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reporte, container, false);
    }


    public void onViewStateRestored(Bundle savedInstanceState) {

        btnreporteOk = (Button)getActivity().findViewById(R.id.botonreporteOk);
        btnreporteOk.setOnClickListener(this);
        descripcion = (EditText)getActivity().findViewById(R.id.descripcion);
        nombreReportado = (TextView)getActivity().findViewById(R.id.nombreReportado);
        nombreReportado.setText(""+UsuarioPerfil.getPrimerNombre().toUpperCase()+" "+UsuarioPerfil.getApellidoPaterno().toUpperCase()+" "+UsuarioPerfil.getApellidoMaterno().toUpperCase());


        super.onViewStateRestored(savedInstanceState);

    }


    @Override
    public void onClick(View view) {
        // AQUI VA URL QUE FALTA ... meter descripcion al link
        String descripcionReporte= descripcion.getText().toString();
        String URL_REPORTE= URL_POST+"reportes";



        String nuevoReporte = "{\"actividad\":{ \"actividadId\":"+actividad.getActividadId()+"}" +
                ",\"motivoReporte\":\""+descripcionReporte+
                "\",\"usuarioReportado\":{ \"usuarioId\":"+UsuarioPerfil.getId()+"}" +
                ",\"usuarioReportador\":{ \"usuarioId\":"+UsuarioSesion.getId()+"}" + "}";


        try {
            SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
            if (su.isNetworkAvailable()) {
                try {
                    resp = new HttpPost(getActivity().getApplicationContext()).execute(nuevoReporte, URL_REPORTE);
                    Toast.makeText(getActivity(), " Agregando Reporte ...   ", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
        }

        FragmentTransaction transaccion = getFragmentManager().beginTransaction();
        String mensaje="REPORTE REALIZADO CON EXITO";
        transaccion.replace(R.id.fragment_container, new confirmacion(UsuarioSesion,mensaje), "confirmar");
        new Principal();
        transaccion.addToBackStack(null);
        transaccion.commit();


    }
}
