package com.recreu.recreu.views;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import cl.recreu.recreu.taller_android_bd.R;

import com.recreu.recreu.MainActivity;
import com.recreu.recreu.Modelos.Usuario;
import com.recreu.recreu.controllers.HttpPost;
import com.recreu.recreu.utilities.SystemUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class CrearUsuario extends AppCompatActivity implements View.OnClickListener{
    private EditText primerNombre;
    private EditText segundoNombre;
    private EditText apellidoPaterno;
    private EditText apellidoMaterno;
    private EditText correo;
    private RadioButton masculino;
    private DatePicker calendario;
    private Button botonOk, botonFecha;
    private int mYear, mMonth, mDay;
    private TextView fecha;
    private String URL_GET = "http://10.0.2.2:8080/javaee/usuarios";
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        primerNombre = (EditText) findViewById(R.id.primerNombre);
        segundoNombre = (EditText) findViewById(R.id.segundoNombre);
        apellidoPaterno = (EditText) findViewById(R.id.apellidoPaterno);
        apellidoMaterno = (EditText) findViewById(R.id.apellidoMaterno);
        correo = (EditText) findViewById(R.id.correo);
        masculino = (RadioButton) findViewById(R.id.masculino);
        botonOk = (Button) findViewById(R.id.Ok);
        botonFecha = (Button) findViewById(R.id.botonFechaNacimiento);
        fecha = (TextView)findViewById(R.id.fechaNacimiento);
        password=(EditText)findViewById(R.id.password);
        botonFecha.setOnClickListener(this);

    }

    public void okCrearUsuario(View vista){

        setErrores();
        boolean cancelar = false;
        View foco = null;

        boolean sexo  = masculino.isChecked();
        String apM= apellidoMaterno.getText().toString();
        String apP=apellidoPaterno.getText().toString();
        String pN = primerNombre.getText().toString();
        String sN= segundoNombre.getText().toString();
        String corr = correo.getText().toString();
        String ps = password.getText().toString();
        String fN = fecha.getText().toString();

        if (apM.isEmpty()){
            apellidoMaterno.setError("debe ingresar su apellido");
            foco = apellidoMaterno;
            cancelar = true;
        }
        if (apP.isEmpty()){
            apellidoPaterno.setError("debe ingresar su apellido");
            foco = apellidoPaterno;
            cancelar = true;
        }
        if (pN.isEmpty()){
            primerNombre.setError("debe ingresar su nombre");
            foco = primerNombre;
            cancelar = true;
        }
        if (sN.isEmpty()){
            segundoNombre.setError("no debe ser vacío");
            foco = segundoNombre;
            cancelar = true;
        }
        if (corr.isEmpty()||corr.contains("@usach.cl")){
            correo.setError("debe ingresar el correo sin @usach.cl");
            foco = correo;
            cancelar = true;
        }
        if (ps.isEmpty()||ps.length()<6){
            password.setError("mínimo 6 caracteres");
            foco = password;
            cancelar = true;
        }
        if (fN.isEmpty()||fN.length()!=10){
            fecha.setError("debe ingresar una fecha valida");
            foco = fecha;
            cancelar = true;
        }
        if (cancelar){
            foco.requestFocus();
        }

        else {


            //  String usuStr= "{\"primerNombre\":\"" + primerNombre.getText().toString()+ "\",\"segundoNombre\":\"" + segundoNombre.getText().toString()+ "\",\"correo\":\"" + segundoNombre.getText().toString()+ ""}";//DEBE MEJORARSE ESTA PARRTE
            String usuStr = "{\"apellidoMaterno\":\"" + apM +
                    "\",\"apellidoPaterno\":\"" + apP +
                    "\",\"primerNombre\":\"" + pN +
                    "\",\"segundoNombre\":\"" + sN +
                    "\",\"correo\":\"" + corr +
                    "\",\"password\":\"" + ps +
                    "\",\"fechaNacimiento\":\"" + fN +
                    "\", \"sexo\":" + sexo +
                    ",\"esAdministrador\": false}";
            System.out.println(usuStr);
            try {
                SystemUtilities su = new SystemUtilities(this.getApplicationContext());

                if (su.isNetworkAvailable()) {
                    try {
                        AsyncTask resp = new HttpPost(this.getApplicationContext()).execute(usuStr, URL_GET);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                System.out.println("Hola hola: error: " + e.toString());
            }

        }

    }

    public void setErrores(){

        primerNombre.setError(null);
        segundoNombre.setError(null);
        apellidoPaterno.setError(null);
        apellidoMaterno.setError(null);
        correo.setError(null);
        fecha.setError(null);
        password.setError(null);

    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String mes;
                        if (monthOfYear<9) mes="0"+(monthOfYear+1);
                        else mes= ""+(monthOfYear+1);
                        String dia;
                        if (dayOfMonth<10) dia="0"+(dayOfMonth);
                        else dia= ""+(dayOfMonth);

                        fecha.setText( year + "-" + mes + "-" + dia);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

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

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {


            String response = intent.getStringExtra("jsonRespuesta");

            try {
                JSONObject resp = new JSONObject(response);
                Toast.makeText(context,resp.getString("mensaje"), Toast.LENGTH_SHORT).show();
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
