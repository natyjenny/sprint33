package com.recreu.recreu.views;

import android.content.Context;
import android.widget.BaseAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import cl.recreu.recreu.taller_android_bd.R;

/**
 * Created by ginebra on 10-06-16.
 */
public class adaptadorCheckbox extends BaseAdapter {


        String[] datosLista;
        boolean[] itemChecked;
        private Context context;



    public adaptadorCheckbox(Context context, String[] datosLista) {
            super();
            this.context = context;
            this.datosLista = datosLista;
            itemChecked = new boolean[datosLista.length];
        }


        public String getNombre(int position) {
                return datosLista[position];

         }


        public int getCount() {
            return datosLista.length;
        }

        public Object getItem(int position) {
            return itemChecked[position];
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int posicion, View ConvertView, ViewGroup parent) {
                View view = LayoutInflater.from(context).inflate(R.layout.buscarportipoocategoria, parent, false);

            TextView datoCasilla = (TextView) view.findViewById(R.id.textView1);
            final CheckBox casillacheck = (CheckBox) view.findViewById(R.id.checkBox1);

            datoCasilla.setText(datosLista[posicion]);


            if (itemChecked[posicion])
                casillacheck.setChecked(true);
            else
                casillacheck.setChecked(false);

               casillacheck.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (casillacheck.isChecked())
                        itemChecked[posicion] = true;
                    else
                        itemChecked[posicion] = false;
                }
            });

            return view;

        }



}
