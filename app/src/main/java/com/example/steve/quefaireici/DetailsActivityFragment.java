package com.example.steve.quefaireici;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Steve on 05/01/2016.
 */
public class DetailsActivityFragment extends Fragment {

    private TextView details;
    private TextView title;
    private TextView adresseTW;
    private ImageView image;
    private TextView horairesTW;
    private TextView telTW;
    private Button buttonMaps;
    Bitmap bitmap;
    ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details,
                container, false);
        return view;
    }

    public void setText(final Activite activite) {
        details = (TextView) getView().findViewById(R.id.detailsText);
        details.setText(activite.getDetails());

        title = (TextView) getView().findViewById(R.id.textViewTitle);
        title.setText(activite.getTitre());

        adresseTW = (TextView) getView().findViewById(R.id.adresseTextView);
        adresseTW.setText(activite.getAdresse());

        horairesTW = (TextView) getView().findViewById(R.id.horaiesTextview);
        horairesTW.setText(activite.getHoraires());

        telTW = (TextView) getView().findViewById(R.id.telTextViews);
        telTW.setText(activite.getTel());

        buttonMaps = (Button) getView().findViewById(R.id.buttonMaps);
        buttonMaps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q=loc:"+activite.getLatitude()+","+ activite.getLongtitude()));
                startActivity(intent);
            }
        });
        image = (ImageView) getView().findViewById(R.id.image);

        try{
            System.out.println(activite.getStringId());
            new LoadImage().execute("http://svandycke.fr/QueFaireIci/images/"+activite.getStringId()+".jpg");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap img) {

            if(image != null){
                image.setImageBitmap(img);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(getActivity(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
