package com.example.steve.quefaireici;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Steve on 05/01/2016.
 */
public class ListeActivityFragment extends Fragment {

    private String json = "[{\"id\": \"Star wars\", \"label\": \"Star Wars (à l'origine nommée sous son titre français, La Guerre des étoiles) est un univers de science-fiction créé par George Lucas en 1977. D'abord conçue comme une trilogie cinématographique sortie entre 1977 et 1983, la saga s'est ensuite élargie de trois nouveaux films sortis entre 1999 et 2005 racontant des événements antérieurs aux premiers. \"},{\"id\": \"Hot Shots! 2\", \"label\": \"Hot Shots! 2 ou Des pilotes en l'air 2 au Québec (Hot Shots! Part Deux) est un film parodique de Jim Abrahams réalisé en 1993.\"},{\"id\": \"Le Labyrinthe de Pan\", \"label\": \"Le Labyrinthe de Pan (El laberinto del fauno) est un film fantastique hispano-mexicain de Guillermo del Toro, sorti en 2006.\"},{\"id\": \"Le Guide du voyageur galactique\", \"label\": \"Le Guide du voyageur galactique (titre original The Hitchhiker's Guide to the Galaxy, H2G2) est une œuvre de science-fiction humoristique multidisciplinaire imaginée par l’écrivain britannique Douglas Adams. Son nom provient de l’objet symbolique de la série : un livre électronique nommé Le Guide du voyageur galactique. Née en 1978 sous forme de feuilleton radiophonique, la série a depuis été déclinée dans de nombreux médias au cours de différentes adaptations : romans, série télévisée, pièces de théâtre, long métrage pour le cinéma.\"}]";
    private OnItemSelectedListener listener;
    private JSONArray jsonArray;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        GetAsync async = new GetAsync(getActivity());
        async.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_liste,
                container, false);
        try{
            jsonArray = new JSONArray(json);
            String[] values = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                values[i] = jsonArray.getJSONObject(i).optString("id").toString();
            }
            ListView listView = (ListView) view.findViewById(R.id.listView);
            ArrayAdapter< String> adapter = new ArrayAdapter< String>(this.getActivity(),android.R.layout.simple_list_item_1, values);
            listView.setClickable(true);
            listView.setAdapter(adapter);
            final ListView finalList = listView;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                    updateDetail(position);
                }
            });

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return view;
    }

    public interface OnItemSelectedListener {
        public void onRssItemSelected(String link);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implemenet MyListFragment.OnItemSelectedListener");
        }
    }


    // May also be triggered from the Activity
    public void updateDetail(int i) {
        try{

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            listener.onRssItemSelected(jsonObject.optString("label").toString());
        }
        catch (JSONException e){

        }
        // Send data to Activity

    }

}
