package com.recreu.recreu.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.recreu.recreu.Modelos.Tipo;

import cl.recreu.recreu.taller_android_bd.R;

public class ExplorarAdaptador extends BaseAdapter {

    private String[] stringTitulos;
    private String[] stringFechaYHora;
    private Tipo[] arrayTipos;
    private Context context;

    public ExplorarAdaptador(Context context, String[] titulos, String[] fechas,Tipo[] tipos) {
        super();
        this.stringTitulos = titulos;
        this.stringFechaYHora = fechas;
        this.arrayTipos=tipos;
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
        View rowView = LayoutInflater.from(context).inflate(R.layout.three_line_icon, parent, false);

        TextView text1 = (TextView) rowView.findViewById(R.id.Textotitulo);
        TextView text2 = (TextView) rowView.findViewById(R.id.TextoTipo);
        TextView text3 = (TextView) rowView.findViewById(R.id.TextoFecha);

        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

        text1.setText(stringTitulos[posicion]);
        text3.setText(stringFechaYHora[posicion]);
        text2.setText(arrayTipos[posicion].getNombreTipo());

       // desde aca se selecciona la imagen asociada al tipo
  /*
        switch(arrayTipos[posicion].getTipoId()){
            case 1:
                icon.setImageResource(R.drawable.t1);
                break;
            case 2:
                icon.setImageResource(R.drawable.t2);
                break;
            case 3:
                icon.setImageResource(R.drawable.t3);
                break;
            case 4:
                icon.setImageResource(R.drawable.t4);
                break;
            case 5:
                icon.setImageResource(R.drawable.t5);
                break;
            case 6:
                icon.setImageResource(R.drawable.t6);
                break;
            case 7:
                icon.setImageResource(R.drawable.t7);
                break;
            case 8:
                icon.setImageResource(R.drawable.t8);
                break;
            case 9:
                icon.setImageResource(R.drawable.t9);
                break;
            case 10:
                icon.setImageResource(R.drawable.t10);
                break;
            case 11:
                icon.setImageResource(R.drawable.t11);
                break;
            case 12:
                icon.setImageResource(R.drawable.t12);
                break;
            case 13:
                icon.setImageResource(R.drawable.t13);
                break;
            case 14:
                icon.setImageResource(R.drawable.t14);
                break;
            case 16:
                icon.setImageResource(R.drawable.t16);
                break;
            case 17:
                icon.setImageResource(R.drawable.t17);
                break;
            case 18:
                icon.setImageResource(R.drawable.t18);
                break;
            case 19:
                icon.setImageResource(R.drawable.t19);
                break;
            case 20:
                icon.setImageResource(R.drawable.t20);
                break;
            case 21:
                icon.setImageResource(R.drawable.t21);
                break;
            case 22:
                icon.setImageResource(R.drawable.t22);
                break;
            case 23:
                icon.setImageResource(R.drawable.t23);
                break;
            case 24:
                icon.setImageResource(R.drawable.t24);
                break;
            case 25:
                icon.setImageResource(R.drawable.t25);
                break;
            case 26:
                icon.setImageResource(R.drawable.t26);
                break;
            case 27:
                icon.setImageResource(R.drawable.t27);
                break;
            case 28:
                icon.setImageResource(R.drawable.t28);
                break;
            case 30:
                icon.setImageResource(R.drawable.t30);
                break;
            case 31:
                icon.setImageResource(R.drawable.t31);
                break;
            case 32:
                icon.setImageResource(R.drawable.t32);
                break;
            case 33:
                icon.setImageResource(R.drawable.t33);
                break;
            case 34:
                icon.setImageResource(R.drawable.t34);
                break;
            case 35:
                icon.setImageResource(R.drawable.t35);
                break;
            case 36:
                icon.setImageResource(R.drawable.t36);
                break;
            case 37:
                icon.setImageResource(R.drawable.t37);
                break;
            case 38:
                icon.setImageResource(R.drawable.t38);
                break;
            case 39:
                icon.setImageResource(R.drawable.t39);
                break;
            case 40:
                icon.setImageResource(R.drawable.t40);
                break;
            case 41:
                icon.setImageResource(R.drawable.t41);
                break;
            case 42:
                icon.setImageResource(R.drawable.t42);
                break;
            case 43:
                icon.setImageResource(R.drawable.t43);
                break;
            case 44:
                icon.setImageResource(R.drawable.t44);
                break;
            case 45:
                icon.setImageResource(R.drawable.t45);
                break;
            case 46:
                icon.setImageResource(R.drawable.t46);
                break;
        }
        */                 icon.setImageResource(R.drawable.t1);

        return rowView;
    }

}