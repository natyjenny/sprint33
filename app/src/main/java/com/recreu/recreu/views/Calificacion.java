package com.recreu.recreu.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpDelete;
import com.recreu.recreu.utilities.AccesoDirecto;
import com.recreu.recreu.utilities.SystemUtilities;

import cl.recreu.recreu.taller_android_bd.R;

/**
 * Created by ginebra on 03-07-16.
 */
public class Calificacion extends Fragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    private Usuario UsuarioSesion, UsuarioPerfil;
    private String URL_PUT,calificacionFinal;
    private Actividad actCalificacion;
    private Button btncalificacion;
    private RatingBar estrellas;

    public Calificacion(Usuario UsuarioSesion, Usuario UsuarioPerfil, Actividad actCalificacion) {
        this.URL_PUT = (new AccesoDirecto()).getURL();
        this.actCalificacion = actCalificacion;
        this.UsuarioPerfil = UsuarioPerfil;
        this.UsuarioSesion = UsuarioSesion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calificacion, container, false);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {

        btncalificacion = (Button)getActivity().findViewById(R.id.enviandocali);
        btncalificacion.setOnClickListener(this);
        estrellas = (RatingBar)getActivity().findViewById(R.id.estrellas);
        estrellas.setOnRatingBarChangeListener(this);

        super.onViewStateRestored(savedInstanceState);

    }






        @Override
    public void onClick(View view) {

        // AQUI VA URL QUE FALTA ... meter calificación al link
         String URL_CALIFICACION = URL_PUT+"usuario/"+UsuarioPerfil.getUsuarioId()+"/usuario/"+UsuarioSesion.getUsuarioId()+"/actividades/"+actCalificacion.getActividadId()+"";


        try {
            SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
            if (su.isNetworkAvailable()) {
                try {
                 //   AsyncTask resp = new HttpPut(getActivity().getApplicationContext()).execute(URL_CALIFICACION);


                    FragmentTransaction transaccion = getFragmentManager().beginTransaction();
                    String mensaje="CALIFICACIÓN REALIZADA CON EXITO";
                    transaccion.replace(R.id.fragment_container, new confirmacion(UsuarioSesion,mensaje), "confirmar");
                    new Principal();
                    transaccion.addToBackStack(null);
                    transaccion.commit();



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
        }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        calificacionFinal= ""+estrellas.getRating()+"";
    }
}








