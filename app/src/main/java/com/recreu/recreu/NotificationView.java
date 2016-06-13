package com.recreu.recreu;

import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.views.Explorar;
import com.recreu.recreu.views.PerfilUsuario;
import com.recreu.recreu.views.Principal;
import com.recreu.recreu.views.detalleActividad;

import cl.recreu.recreu.taller_android_bd.R;

public class NotificationView extends AppCompatActivity implements View.OnClickListener{
    private Usuario usuario;
    private Actividad actividad;
    private Button botonVerDetalle;
    private String actividadID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        String titulo = getIntent().getExtras().getString("actividadN");
                                      System.out.println(" llego titulo final : "+titulo);
        actividadID = getIntent().getExtras().getString("actividadID");
                                       System.out.println("LA ID DE ACTIVIDAD QUE LLEGO AL APRETAR : "+actividadID);

        usuario= (Usuario) getIntent().getExtras().getSerializable("usuarioN");
       // actividad= (Actividad) getIntent().getExtras().getSerializable("actividadCompleta");
                                             //  System.out.println("ID de Actividad completa:: "+actividad.getActividadId());


        if (usuario!=null) System.out.println("Estoy en notificaciones con el usuario: "+ usuario.getPrimerNombre());
                                       else System.out.println("Estoy en notificaciones sin el usuario :C");

                        //System.out.println("numero?: "+getIntent().getExtras().getInt("numero"));
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        TextView nombre= (TextView)this.findViewById(R.id.nombreActividad);
        nombre.setText(titulo);


        botonVerDetalle = (Button)this.findViewById(R.id.verDetalles);
        botonVerDetalle.setOnClickListener(this);
        // Cancelamos la Notificacion que hemos comenzado
        nm.cancel(getIntent().getExtras().getInt("notificationID"));
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new Explorar(usuario,true),"explorandoCercanas");
        new Principal();
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
