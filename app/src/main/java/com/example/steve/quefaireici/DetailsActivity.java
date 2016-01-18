package com.example.steve.quefaireici;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Steve on 05/01/2016.
 */
public class DetailsActivity extends Activity {
    public static final String EXTRA_URL = "activite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.activity_details);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            Activite s =(Activite) extras.getSerializable(EXTRA_URL);
            DetailsActivityFragment fragment = (DetailsActivityFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);

            fragment.setText(s);
        }
    }

}
