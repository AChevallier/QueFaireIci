package com.example.steve.quefaireici;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
        String[] values = new String[1];
        int i = 1;
        values[0] = "toto";

        ListView listView = (ListView)context.findViewById(R.id.listView);
        ArrayAdapter< String> adapter = new ArrayAdapter< String>(context,android.R.layout.simple_list_item_1, values);
        listView.setClickable(true);
        listView.setAdapter(adapter);

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
}
