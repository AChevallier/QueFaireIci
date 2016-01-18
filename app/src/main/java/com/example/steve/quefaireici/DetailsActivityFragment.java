package com.example.steve.quefaireici;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Steve on 05/01/2016.
 */
public class DetailsActivityFragment extends Fragment {

    private TextView details;
    private TextView title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details,
                container, false);
        return view;
    }

    public void setText(Activite activite) {
        details = (TextView) getView().findViewById(R.id.detailsText);
        details.setText(activite.getDetails());

        title = (TextView) getView().findViewById(R.id.textViewTitle);
        title.setText(activite.getTitre());
    }

}
