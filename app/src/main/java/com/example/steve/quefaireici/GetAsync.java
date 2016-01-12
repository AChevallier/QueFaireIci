package com.example.steve.quefaireici;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Steve on 05/01/2016.
 */
public class GetAsync extends AsyncTask<String, String, JSONObject> {
    JSONParser jsonParser = new JSONParser();

    private ProgressDialog pDialog;

    private static final String LOGIN_URL = "http://stevevandycke.free.fr/QueFaireIci/activites.json";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private Activity context;

    public GetAsync(Activity context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Mise à jour...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {

        try {

            HashMap<String, String> params = new HashMap<>();

            Log.d("request", "starting");

            JSONObject json = jsonParser.makeHttpRequest(
                    LOGIN_URL, "GET", params);

            if (json != null) {
                Log.d("JSON result", json.toString());

                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(JSONObject json) {
        try{
            final List listActivite = parse(json);
            String[] values = new String[listActivite.size()];
            for (int i = 0; i < listActivite.size(); i++){
                Activite a = (Activite)listActivite.get(i);
                values[i] = a.getTitre();
            }
            final ListView listView = (ListView)context.findViewById(R.id.listView);
            ArrayAdapter< String> adapter = new ArrayAdapter< String>(context,android.R.layout.simple_list_item_1, values);
            listView.setClickable(true);
            listView.setAdapter(adapter);
            final ListView finalList = listView;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                    Intent detailsIntent = new Intent(context, DetailsActivity.class);
                    detailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Activite ac =(Activite) listActivite.get(position);
                    detailsIntent.putExtra("url", ac.getDetails() );
                    context.startActivity(detailsIntent);

                }
            });
        }catch(Exception e){
            System.out.println(e);
        }


<<<<<<< Updated upstream
=======
        ListView listView = (ListView)context.findViewById(R.id.listView);
        //ArrayAdapter< String> adapter = new ArrayAdapter< String>(context,android.R.layout.simple_list_item_1, values);
        listView.setClickable(true);
        //listView.setAdapter(adapter);
>>>>>>> Stashed changes

        int success = 0;
        String message = "";

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        if (json != null) {
            //Toast.makeText(context, json.toString(),Toast.LENGTH_LONG).show();

            try {
                success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (success == 1) {
            Log.d("Success!", message);
        }else{
            Log.d("Failure", message);
        }
    }

    public static List parse(JSONObject json) throws JSONException{
        Iterator<String> iter = json.keys();
        List<Activite> al = new ArrayList<>();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                JSONObject value = (JSONObject)json.get(key);
                Iterator<String> iterValue = value.keys();
                Activite activite = new Activite();
                try{
                    while (iterValue.hasNext()) {
                        String keyV = iterValue.next();
                        String valueV = (String) value.get(keyV);
                        switch (keyV) {
                            case "id":
                                activite.setId(Integer.parseInt(valueV));
                                break;
                            case "longitude":
                                activite.setLongtitude(Double.parseDouble(valueV));
                                break;
                            case "latitude":
                                activite.setLatitude(Double.parseDouble(valueV));
                                break;
                            case "titre":
                                activite.setTitre(valueV);
                                break;
                            case "details":
                                activite.setDetails(valueV);
                                break;
                        }
                    }
                }
                catch (JSONException e) {

                }
                al.add(activite);

            } catch (JSONException e) {
                // Something went wrong!
            }
        }
        return al;
    }
}
