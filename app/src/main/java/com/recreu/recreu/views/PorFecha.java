package com.recreu.recreu.views;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import com.recreu.recreu.Modelos.Usuario;

import java.util.Calendar;

import cl.recreu.recreu.taller_android_bd.R;

public class PorFecha extends Fragment implements View.OnClickListener{
    private View vista;
    private TextView fechaInicio, fechaFin, horaInicio, horaFin;
    private Usuario usuario;
    public PorFecha(Usuario usuario){
        this.usuario=usuario;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.por_fecha, container, false);
        fechaInicio=(TextView)vista.findViewById(R.id.fechaInicio);
        horaInicio=(TextView)vista.findViewById(R.id.horaInicio);
        fechaFin=(TextView)vista.findViewById(R.id.fechaFin);
        horaFin=(TextView)vista.findViewById(R.id.horaFin);
        vista.findViewById(R.id.modificarFechaInicio).setOnClickListener(this);
        vista.findViewById(R.id.modificarFechaFin).setOnClickListener(this);
        vista.findViewById(R.id.modificarHoraInicio).setOnClickListener(this);
        vista.findViewById(R.id.modificarHoraFin).setOnClickListener(this);
        vista.findViewById(R.id.buscarPorFecha).setOnClickListener(this);
        return vista;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        //vistaActividad = inflater.inflate(R.layout.nueva_actividad, container, false);
        super.onViewStateRestored(savedInstanceState);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modificarFechaInicio:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String mes;
                        if (monthOfYear<9) mes="0"+(monthOfYear+1);
                        else mes= ""+(monthOfYear+1);
                        String dia;
                        if (dayOfMonth<10) dia="0"+(dayOfMonth);
                        else dia= ""+(dayOfMonth);

                        fechaInicio.setText( year + "-" + mes + "-" + dia);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;


            case R.id.modificarHoraInicio:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String hora, minutos;
                        hora = selectedHour + "";
                        minutos = selectedMinute + "";
                        if (selectedHour < 9)
                            hora = "0" + selectedHour;
                        if (selectedMinute < 9)
                            minutos = "0" + selectedMinute;

                        horaInicio.setText("" + hora + ":" + minutos + "");
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Selecciona hora Inicio");
                mTimePicker.show();
                break;

        case R.id.modificarFechaFin:
            final Calendar c2 = Calendar.getInstance();
            int mYear2 = c2.get(Calendar.YEAR);
            int mMonth2 = c2.get(Calendar.MONTH);
            int mDay2 = c2.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog2 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String mes;
                    if (monthOfYear<9) mes="0"+(monthOfYear+1);
                    else mes= ""+(monthOfYear+1);
                    String dia;
                    if (dayOfMonth<10) dia="0"+(dayOfMonth);
                    else dia= ""+(dayOfMonth);

                    fechaFin.setText( year + "-" + mes + "-" + dia);

                }
            }, mYear2, mMonth2, mDay2);
            datePickerDialog2.show();
            break;


        case R.id.modificarHoraFin:
            Calendar mcurrentTime2 = Calendar.getInstance();
            int hour2 = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
            int minute2 = mcurrentTime2.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker2;
            mTimePicker2 = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    String hora, minutos;
                    hora = selectedHour + "";
                    minutos = selectedMinute + "";
                    if (selectedHour < 9)
                        hora = "0" + selectedHour;
                    if (selectedMinute < 9)
                        minutos = "0" + selectedMinute;

                    fechaFin.setText("" + hora + ":" + minutos + "");
                }
            }, hour2, minute2, true);
            mTimePicker2.setTitle("Selecciona hora Inicio");
            mTimePicker2.show();
            break;
        case R.id.buscarPorFecha:
            String str="actividades/?tiempo_inicio="+fechaInicio.getText() +"-"+horaInicio.getText()+"&tiempo_fin="+fechaFin.getText()+"-"+horaFin.getText();
            FragmentTransaction transaccion = getFragmentManager().beginTransaction();
            transaccion.replace(R.id.fragment_container, new Explorar(usuario,str), "explorar");
            new Principal();
            transaccion.addToBackStack(null);
            transaccion.commit();
            break;

    }

    }
}