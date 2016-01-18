package com.example.steve.quefaireici;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandre on 18/01/16.
 */
public class ActiviteAdapter extends ArrayAdapter<Activite> {
    Context context;
    List<Activite> arrActivite;
    private LayoutInflater inflater;

        public ActiviteAdapter(Context context ,
                           List<Activite> objects) {
            super(context, R.layout.single_item, objects);
            this.context = context;
            this.arrActivite = objects;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Activite obj = getItem(position);

            convertView = inflater.inflate(R.layout.single_item, null);
            TextView titre= (TextView)convertView.findViewById(R.id.title_single);
            titre.setText(obj.getTitre());

            return convertView;
        }

    }
