package com.recreu.recreu.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cl.recreu.recreu.taller_android_bd.R;

public class ExplorarAdaptador extends BaseAdapter {

    private String[] stringTitulos;
    private String[] stringFechaYHora;
    private String[] stringTipos;
    private Context context;

    public ExplorarAdaptador(Context context, String[] titulos, String[] fechas,String[] tipos) {
        super();
        this.stringTitulos = titulos;
        this.stringFechaYHora = fechas;
        this.stringTipos=tipos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stringTitulos.length;
    }

    @Override
    public Object getItem(int position) {
        return stringTitulos[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).
                inflate(R.layout.two_line_icon, parent, false);

        TextView text1 = (TextView) rowView.findViewById(R.id.text1);
        TextView text2 = (TextView) rowView.findViewById(R.id.text2);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

        text1.setText(stringTitulos[posicion]);
        text2.setText(stringFechaYHora[posicion]);

       // desde aca se selecciona la imagen asociada al tipo
        switch(stringTipos[posicion]){

            case "1":
                icon.setImageResource(R.drawable.tipos.1);
            case "2":
                icon.setImageResource(R.drawable.brand);
            case "3":
                icon.setImageResource(R.drawable.brand);
            case "4":
                icon.setImageResource(R.drawable.brand);
            case "5":
                icon.setImageResource(R.drawable.brand);
            case "6":
                icon.setImageResource(R.drawable.brand);
            case "7":
                icon.setImageResource(R.drawable.brand);
            case "8":
                icon.setImageResource(R.drawable.brand);
            case "9":
                icon.setImageResource(R.drawable.brand);
            case "10":
                icon.setImageResource(R.drawable.brand);
            case "11":
                icon.setImageResource(R.drawable.brand);
            case "12":
                icon.setImageResource(R.drawable.brand);
            case "13":
                icon.setImageResource(R.drawable.brand);
            case "14":
                icon.setImageResource(R.drawable.brand);
            case "15":
                icon.setImageResource(R.drawable.brand);

        }


        return rowView;
    }

}