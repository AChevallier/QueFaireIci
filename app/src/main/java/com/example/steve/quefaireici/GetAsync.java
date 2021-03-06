package com.example.steve.quefaireici;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

    private static final String LOGIN_URL = "http://svandycke.fr/QueFaireIci/activites.json";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();

    private Activity context;

    // Search EditText
    EditText inputSearch;


    public GetAsync(Activity context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        /*pDialog = new ProgressDialog(context);
        pDialog.setMessage("Mise à jour...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();*/
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

        swipeRefreshLayout = (SwipeRefreshLayout) context.findViewById(R.id.swiperefresh);
        //stop loading
        swipeRefreshLayout.setRefreshing(false);
        //################
        //Parsing json + making object
        //################

        try{
            final List listActivite = parse(json);
            List<Activite> values = new ArrayList<Activite>();
            for (int i = 0; i < listActivite.size(); i++){
                Activite a = (Activite)listActivite.get(i);
                values.add(a);
            }
            final ListView listView = (ListView)context.findViewById(R.id.listView);
            final ArrayAdapter< Activite> adapter = new ActiviteAdapter(context, values);
            listView.setClickable(true);
            listView.setAdapter(adapter);
            final List<Activite> activities = values;


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                    System.out.println(adapter.getItem(position));
                    Activite ac = adapter.getItem(position);
                    DetailsActivityFragment fragment = (DetailsActivityFragment) context.getFragmentManager()
                            .findFragmentById(R.id.detailFragment);

                    if (fragment != null && fragment.isInLayout()) {
                        fragment.setText(ac);
                    } else {
                        Intent detailsIntent = new Intent(context, DetailsActivity.class);
                        detailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        detailsIntent.putExtra("activite", ac);
                        context.startActivity(detailsIntent);
                    }

                }
            });

            //################
            //  Search Input
            //################
            inputSearch = (EditText) context.findViewById(R.id.inputSearch);
            inputSearch.setText("");
            inputSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    //adapterFinal.getFilter().filter(cs);
                    ArrayList<Activite> temp = new ArrayList<Activite>();
                    int textlength = inputSearch.getText().length();
                    temp.clear();
                    for (int i = 0; i < activities.size(); i++) {
                        if (textlength <= activities.get(i).getTitre().length()) {
                            if (activities.get(i).getTitre().toLowerCase().contains(inputSearch.getText()) || activities.get(i).getVille().toLowerCase().contains(inputSearch.getText())) {
                                temp.add(activities.get(i));
                            }
                        }
                    }
                    ActiviteAdapter adapterModif = new ActiviteAdapter(context, temp);
                    listView.setAdapter(adapterModif);
                    final ActiviteAdapter finalAdapaterModif = adapterModif;
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                            Activite ac = finalAdapaterModif.getItem(position);
                            DetailsActivityFragment fragment = (DetailsActivityFragment) context.getFragmentManager()
                                    .findFragmentById(R.id.detailFragment);

                            if (fragment != null && fragment.isInLayout()) {
                                fragment.setText(ac);
                            } else {
                                Intent detailsIntent = new Intent(context, DetailsActivity.class);
                                detailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                detailsIntent.putExtra("activite", ac);
                                context.startActivity(detailsIntent);
                            }

                        }
                    });

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }
            });

            //--------------------------------------
            // Swipe refresh
            //--------------------------------------


            // the refresh listner. this would be called when the layout is pulled down
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {

                    GetAsync async = new GetAsync(context);
                    async.execute();

                }
            });
            // sets the colors used in the refresh animation
            swipeRefreshLayout.setColorSchemeResources(R.color.blue_bright, R.color.green_light,
                    R.color.orange_light, R.color.red_light);

        }catch(Exception e){
            System.out.println(e);
        }

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
                            case "ville":
                                activite.setVille(valueV);
                                break;
                            case "horaires":
                                activite.setHoraires(valueV);
                                break;
                            case "tel":
                                activite.setTel(valueV);
                                break;
                            case "adresse":
                                activite.setAdresse(valueV);
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
