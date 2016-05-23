package com.recreu.recreu.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cl.recreu.recreu.taller_android_bd.R;

public class MyAdapter extends BaseAdapter {

    private String[] data;
    private String[] data2;
    private Context context;

    public MyAdapter(Context context, String[] data1, String[] data2) {
        super();
        this.data = data1;
        this.data2 = data2;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).
                inflate(R.layout.two_line_icon, parent, false);

        TextView text1 = (TextView) rowView.findViewById(R.id.text1);
        TextView text2 = (TextView) rowView.findViewById(R.id.text2);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

        text1.setText(data[position]);
        text2.setText(data2[position]);
        icon.setImageResource(R.drawable.brand);

        return rowView;
    }

}