package com.example.steve.quefaireici;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ListeActivityFragment.OnItemSelectedListener {

    String[] arr = { "Paris, France", "Rambouillet, France","Versailles, France", "Lyon, France", "Chevreuse, France"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView autocomplete = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, arr);

        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);
    }

    @Override
    public void onRssItemSelected(String link) {
        DetailsActivityFragment fragment = (DetailsActivityFragment) getFragmentManager()
                .findFragmentById(R.id.detailFragment);
        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(link);
        } else {
            Intent intent = new Intent(getApplicationContext(),
                    DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_URL, link);
            startActivity(intent);

        }
    }
}



