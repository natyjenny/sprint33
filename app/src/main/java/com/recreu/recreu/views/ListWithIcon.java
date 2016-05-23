package com.recreu.recreu.views;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import cl.recreu.recreu.taller_android_bd.R;

public class ListWithIcon extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        String[] dados = {"Item 1", "Item 2", "Item 3",
                "Item 4", "Item 5", "Item 6", "Item 7"};

        String[] dados2 = {"desc 1", "desc 2", "desc 3",
                "desc 4", "desc 5", "desc 6", "desc 7"};

        MyAdapter myAdapter = new MyAdapter(this, dados, dados2);

        setListAdapter(myAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, l.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }
}