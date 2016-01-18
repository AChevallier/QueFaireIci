package com.example.steve.quefaireici;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

/*    @Override
    public void onRssItemSelected(String link) {
        System.out.println("&&&&&&&&&&&&&"+link);
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
    }*/
}



