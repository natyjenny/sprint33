package com.recreu.recreu.views;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpPost;
import com.recreu.recreu.utilities.SystemUtilities;

import cl.recreu.recreu.taller_android_bd.R;

import org.json.JSONException;
import org.json.JSONObject;


public class IniciarSesion extends AppCompatActivity {
    private AutoCompleteTextView correo;
    private EditText password;
    private View mLoginFormView;
    private View mProgressView;
    private String URL_GET = "http://10.0.2.2:8080/javaee/usuarios/login";
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        correo = (AutoCompleteTextView) findViewById(R.id.correo);
        password = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void okIniciarSesion(View vista) {
        correo.setError(null);
        password.setError(null);
        boolean cancelar = false;
        View foco = null;

        String passStr = password.getText().toString();
        String correoStr = correo.getText().toString();

        if (TextUtils.isEmpty(passStr) || !esPasswordValido(passStr)) {
            password.setError("mínimo 6 caracteres");
            foco = password;
            cancelar = true;
        }

        if (TextUtils.isEmpty(correoStr)) {
            correo.setError("debe ingresar un email");
            foco = correo;
            cancelar = true;
        }

        if (cancelar) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            foco.requestFocus();

        } else {




            String usuStr = "{\"correo\":\""+correo.getText().toString()+
                    "\",\"password\":\""+password.getText().toString()+"\"}";
            System.out.println(usuStr);



            try {
                SystemUtilities su = new SystemUtilities(this.getApplicationContext());

                if (su.isNetworkAvailable()) {
                    try {
                        AsyncTask resp = new HttpPost(this.getApplicationContext()).execute(usuStr,URL_GET);

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }

            } catch (Exception e) {
                System.out.println("Hola hola: error: "+e.toString());
            }




        }


    }

    public boolean esPasswordValido(String pass){
        return pass.length()>5;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        this.registerReceiver(receiver, new IntentFilter("httpPost"));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.unregisterReceiver(receiver);
    }
    /**
     * Our Broadcast Receiver. We get notified that the data is ready this way.
     */
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            System.out.println("Antes de intentar xd");
            String response = intent.getStringExtra("jsonRespuesta");

                try {



                    System.out.println("Respuesta json = "+response);
                    JSONObject aux =new JSONObject(response);
                    usuario = new Usuario(aux.getString("apellidoMaterno"),aux.getString("apellidoPaterno"),aux.getString("primerNombre"),aux.getString("segundoNombre"),aux.getString("correo"),aux.getString("password"),aux.getString("fechaNacimiento"), aux.getBoolean("sexo"), aux.getBoolean("esAdministrador"));
                    usuario.agregarDatos(aux.getString("lastUpdate"), aux.getInt("usuarioId"),aux.getString("createdAt"),aux.getBoolean("disponibilidad"),aux.getBoolean("esActivo"));

                    showProgress(false);
                    termino();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Usuario y/o contraseña incorrectas", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                }
        }
    };
    private void termino(){
        System.out.println("BASJOASJO");
        Intent i = new Intent(this,Principal.class);
        i.putExtra("usuario",usuario);
        startActivity(i);
        finish();
    }
}
