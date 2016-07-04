package com.recreu.recreu.views;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.utilities.AccesoDirecto;



import cl.recreu.recreu.taller_android_bd.R;
// TODO: Arreglar diseño de perfil de usuario

public class PerfilUsuario extends Fragment implements View.OnClickListener {
    private final String URL_POST = (new AccesoDirecto()).getURL() + "actividades";
    private Context c;
    private Usuario usuarioPerfil, usuarioSesion;
    private View vistaPerfil;
    private TextView nombrePerfil;
    private TextView correoPerfil;
    private TextView carreraPerfil;
    private TextView cumpleanosPerfil;
    private TextView interesesPerfil;
    private TextView telefonoPerfil;
    private Button activOrganizador, activParticipante, eliminarDeActividad,botonReporte,botonCalificacion,botonConfirmadas;
    private Actividad actividad;
    private boolean organizaOno=false;


    public PerfilUsuario(Usuario usuSesion, Usuario usuPerfil) {
        this.usuarioSesion=usuSesion;
        this.usuarioPerfil = usuPerfil;
    }

    public PerfilUsuario(Usuario usuSesion, Usuario usuPerfil, boolean organizaOno, Actividad actividad) {
        this.usuarioSesion=usuSesion;
        this.usuarioPerfil = usuPerfil;
        this.actividad = actividad;
        this.organizaOno=organizaOno;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        vistaPerfil = inflater.inflate(R.layout.perfil_usuario, container, false);
        nombrePerfil = (TextView) vistaPerfil.findViewById(R.id.nombrePerfil);
        correoPerfil = (TextView) vistaPerfil.findViewById(R.id.correoPerfil);
        carreraPerfil = (TextView) vistaPerfil.findViewById(R.id.carreraPerfil);
        cumpleanosPerfil = (TextView) vistaPerfil.findViewById(R.id.cumpleanosPerfil);
        interesesPerfil = (TextView) vistaPerfil.findViewById(R.id.interesesPerfil);
        telefonoPerfil = (TextView) vistaPerfil.findViewById(R.id.telefonoPerfil);
        activParticipante=(Button)vistaPerfil.findViewById(R.id.actividadesParticipante);
        activOrganizador=(Button)vistaPerfil.findViewById(R.id.actividadesOrganizador);
       // System.out.println(" ID Usuario Sesion : "+usuarioSesion.getId() +" Id usuario perfil "+usuarioPerfil.getId());
        botonReporte=(Button)vistaPerfil.findViewById(R.id.btnreporte);
        botonCalificacion=(Button)vistaPerfil.findViewById(R.id.btncalificacion);
        botonConfirmadas=(Button)vistaPerfil.findViewById(R.id.actividadesConfirmadas);


        // filtro botones -> PRIVACIDAD - observo perfil ajeno
        if (usuarioSesion.getId() != usuarioPerfil.getId()) {
            activOrganizador.setVisibility(View.GONE);
            activParticipante.setVisibility(View.GONE);
            botonConfirmadas.setVisibility(View.GONE);
            botonReporte.setOnClickListener(this);
            botonCalificacion.setOnClickListener(this);
        }

        // filtro botones -> PRIVACIDAD - observo mi propio perfil
        if (usuarioSesion.getId() == usuarioPerfil.getId()) {
            activOrganizador.setOnClickListener(this);
            botonReporte.setVisibility(View.GONE);
            botonConfirmadas.setOnClickListener(this);
            activParticipante.setOnClickListener(this);
            botonCalificacion.setVisibility(View.GONE);
        }


        // creación boton eliminar usuario de actividad
        eliminarDeActividad=(Button)vistaPerfil.findViewById(R.id.eliminarDeActividad);

        //Set
        nombrePerfil.setText(usuarioPerfil.getPrimerNombre() + " " + usuarioPerfil.getApellidoPaterno() + "\n" + usuarioPerfil.getApellidoMaterno());
        correoPerfil.setText(usuarioPerfil.getCorreo() + "@usach.cl");

        try{carreraPerfil.setText(usuarioPerfil.getCarrera().getNombreCarrera());} catch(Exception e){carreraPerfil.setText("no especifica");}
        cumpleanosPerfil.setText(usuarioPerfil.getFechaNacimiento());
        interesesPerfil.setText(usuarioPerfil.getIntereses());
        telefonoPerfil.setText(usuarioPerfil.getNumeroTelefono());

        if (organizaOno) {
            System.out.println("Llegue como organzador al perfil");
            eliminarDeActividad.setVisibility(View.VISIBLE);
            eliminarDeActividad.setOnClickListener(this);
        }
        return vistaPerfil;
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction transaccion;
        switch (v.getId()) {
            case R.id.actividadesOrganizador:
                transaccion = getFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragment_container, new Explorar(usuarioSesion, usuarioPerfil.getId(), true), "explorar");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();
                break;

            case R.id.actividadesParticipante:
                transaccion = getFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragment_container, new Explorar(usuarioSesion, usuarioPerfil.getId(), false), "heParticipado");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();
                break;

            case R.id.actividadesConfirmadas:
                transaccion = getFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragment_container, new Explorar(usuarioSesion,1), "heCOnfirmado");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();
                break;

            case R.id.eliminarDeActividad:
                transaccion = getFragmentManager().beginTransaction();
                String mensaje=" USUARIO ELIMINADO CON EXITO";
                transaccion.replace(R.id.fragment_container, new confirmacion(usuarioSesion,mensaje), "mensaje");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();
                break;
            case R.id.btncalificacion:
                transaccion = getFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragment_container, new Calificacion(usuarioSesion,usuarioPerfil,actividad), "calificando");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();
                break;

            case R.id.btnreporte:
                transaccion = getFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragment_container, new Reporte(usuarioSesion,usuarioPerfil,actividad), "reporteando");
                new Principal();
                transaccion.addToBackStack(null);
                transaccion.commit();
                break;

        }

    }
}