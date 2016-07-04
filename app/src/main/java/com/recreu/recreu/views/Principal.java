package com.recreu.recreu.views;

import android.Manifest;
import android.app.FragmentTransaction;
import java.util.UUID;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.recreu.recreu.MainActivity;
import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Tipo;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.NotificationView;
import com.recreu.recreu.controllers.HttpDelete;
import com.recreu.recreu.controllers.HttpGet;
import com.recreu.recreu.controllers.HttpPost;
import com.recreu.recreu.utilities.AccesoDirecto;
import com.recreu.recreu.utilities.JsonHandler;
import com.recreu.recreu.utilities.MyLocationListener;
import com.recreu.recreu.utilities.SystemUtilities;

import android.location.LocationListener;

import org.json.JSONObject;

import java.util.ArrayList;

import cl.recreu.recreu.taller_android_bd.R;

public class Principal extends AppCompatActivity {
    private BroadcastReceiver br = null;
    private FragmentTransaction transaccion;
    private Usuario usuario;
    private Actividad act;
    private TextView nombre;
    private Button boton1;
    private int notificacionID = 1;

    private EditText usuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        transaccion = getFragmentManager().beginTransaction();
        System.out.println("ESTOY EN EL PRINCIPAL");
        transaccion.replace(R.id.fragment_container, new Inicio(usuario), "inicio");
        new Principal();
        transaccion.addToBackStack(null);
        transaccion.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.navbar, menu);

        return true;
    }

    // boton atras : termina este Fragment y obtiene el anterior de la pila
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.inicio:

                if (getFragmentManager().findFragmentByTag("inicio") == null) {
                    transaccion = getFragmentManager().beginTransaction();

                    transaccion.replace(R.id.fragment_container, new Inicio(usuario), "inicio");
                    new Principal();
                    transaccion.addToBackStack(null);
                    transaccion.commit();

                }
                break;

            case R.id.anadirActividad:

                if (getFragmentManager().findFragmentByTag("nuevaActividad") == null) {
                    transaccion = getFragmentManager().beginTransaction();

                    transaccion.replace(R.id.fragment_container, new NuevaActividad(usuario), "nuevaActividad");
                    new Principal();
                    transaccion.addToBackStack(null);
                    transaccion.commit();

                }
                break;

            case R.id.explorar:
                if (getFragmentManager().findFragmentByTag("explorar") == null) {
                    transaccion = getFragmentManager().beginTransaction();

                    transaccion.replace(R.id.fragment_container, new Explorar(usuario), "explorar");
                    new Principal();
                    transaccion.addToBackStack(null);
                    transaccion.commit();

                }
                break;

            case R.id.verPerfil:
                if (getFragmentManager().findFragmentByTag("verPerfil") == null) {
                    transaccion = getFragmentManager().beginTransaction();


                    transaccion.replace(R.id.fragment_container, new PerfilUsuario(usuario, usuario), "verPerfil");
                    new Principal();
                    transaccion.addToBackStack(null);
                    transaccion.commit();

                }
                break;
            case R.id.buscar:
                if (getFragmentManager().findFragmentByTag("buscarActividad") == null) {
                    transaccion = getFragmentManager().beginTransaction();
                    transaccion.replace(R.id.fragment_container, new BuscarPor(usuario), "buscarActividad");
                    new Principal();
                    transaccion.addToBackStack(null);
                    transaccion.commit();

                }
                break;


            case R.id.cerrarSesion:
                finish();
                usuario = null;
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);


            case R.id.exit:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void explorar(View view) {

        if (getFragmentManager().findFragmentByTag("explorar") == null) {
            transaccion = getFragmentManager().beginTransaction();

            transaccion.replace(R.id.fragment_container, new Explorar(usuario), "explorar");
            new Principal();
            transaccion.addToBackStack(null);
            transaccion.commit();

        }

    }

    public void irAEliminar(View view) {
        if (getFragmentManager().findFragmentByTag("eliminar") == null) {
            transaccion = getFragmentManager().beginTransaction();
            transaccion.replace(R.id.fragment_container, new ListaEliminar(), "eliminar");
            new Principal();
            transaccion.addToBackStack(null);
            transaccion.commit();
        }
    }

    public void buscarActividad(View view){
        if (getFragmentManager().findFragmentByTag("buscarActividad") == null) {
            transaccion = getFragmentManager().beginTransaction();

            transaccion.replace(R.id.fragment_container, new BuscarPor(usuario), "buscarActividad");
            new Principal();
            transaccion.addToBackStack(null);
            transaccion.commit();
        }
    }

    public void crearActividad(View view) {
        if (getFragmentManager().findFragmentByTag("nuevaActividad") == null) {
            transaccion = getFragmentManager().beginTransaction();
            transaccion.replace(R.id.fragment_container, new NuevaActividad(usuario), "nuevaActividad");
            new Principal();
            transaccion.addToBackStack(null);
            transaccion.commit();


        }

    }

    protected void mostrarNotificacion(CharSequence tick, Actividad act) {



        Intent i = new Intent(this, NotificationView.class);
        i.putExtra("notificationID", notificacionID);
        i.putExtra("numero",12);
        i.putExtra("actividadN",act.getTitulo());
        i.putExtra("usuarioN",usuario);
                          //    System.out.println("que habia en act.getActividadId() : "+act.getActividadId());

        i.putExtra("actividadID",act.getActividadId().toString());
                          //     System.out.println("que hay en  actividadID : "+act.getActividadId());
       // i.putExtra("actividadCompleta",acti);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(tick)
                .setContentTitle(act.getTitulo())
                .setContentText(act.getCuerpo())
                .setSmallIcon(R.drawable.smile)

                .addAction(R.drawable.smile, tick, pendingIntent)
                .setVibrate(new long[]{100, 250, 100, 500})
                .build();


        noti.flags |= Notification.FLAG_AUTO_CANCEL;


        nm.notify(notificacionID, noti);
        notificacionID=notificacionID+1;
    }

    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("httpNotificacion");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                if (jh.getActividades(intent.getStringExtra("data"))!=null) {
                    ArrayList<Actividad> actividadesLista = jh.getActividades(intent.getStringExtra("data"));
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
                        mostrarNotificacion("Actividad de su interes en Recreu", actividadesLista.get(i));
                    }

                }
            }
        };

        this.registerReceiver(br, intentFilter);
        SystemUtilities su = new SystemUtilities(this.getApplicationContext());
        if (su.isNetworkAvailable()) {
            try {
                new HttpGet(this.getApplicationContext(), true).execute((new AccesoDirecto()).getURL() + "actividades/?latitud=7&longitud=550&ladocuadrado=60&minutos=35");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onResume();
    }



    @Override
    public void onPause() {
        if (br != null) {
            this.unregisterReceiver(br);
        }
        super.onPause();
    }


}