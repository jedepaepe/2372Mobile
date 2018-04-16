package eu.epfc.swexplorer;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import eu.epfc.swexplorer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements SWPlanetsAdapter.ListItemClickListener {

    private RecyclerView planetsRecyclerView;
    private List<Planet> planets;

    static final String TAG = "MainActivity";


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Planet selectedPlanet = planets.get(clickedItemIndex);

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // version for tablet only
        //if(orientation == Configuration.ORIENTATION_LANDSCAPE &&
        //        (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)) {
            DetailFragment detailFragment = (DetailFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
            detailFragment.setPlanet(selectedPlanet);
        } else {
            // ftagments have no context, and send it to the DetailActivity
            Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
            detailIntent.putExtra("planetObject", selectedPlanet);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        planets = new ArrayList<>();
        planetsRecyclerView = getView().findViewById(R.id.recyclerview_planets);

        // set the adapter of the RecyclerView
        SWPlanetsAdapter swPlanetsAdapter = new SWPlanetsAdapter(this);
        planetsRecyclerView.setAdapter(swPlanetsAdapter);

        // set the layoutManager of the recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        planetsRecyclerView.setLayoutManager(layoutManager);

        loadPlanetData();

        swPlanetsAdapter.setPlanetDatas(planets);
    }

    /**
     * Read the file planets.json , create planets objects and store them in the list this.planets
     */
    private void loadPlanetData()
    {
        String jsonString = loadJSONFromAsset();
        if (jsonString != null)
        {
            populatePlanetModelsFromJSONString(jsonString);
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            // Fragments have no context
            Context applicationContext = getActivity().getApplicationContext();
            InputStream is = applicationContext.getAssets().open("planets.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    /**
     * This method create Planet objects from a json String and store them in the field List<Planet> planets
     * @param jsonString the json String containing planet informations
     */
    private void populatePlanetModelsFromJSONString(String jsonString)
    {
        planets.clear();

        try {

            // get the root json element
            JSONObject jsonObject = new JSONObject(jsonString);

            // get the "results" array from the root JSON element
            JSONArray jsonPlanets = jsonObject.getJSONArray("results");

            // for all the planets listed in results
            for (int i = 0; i<jsonPlanets.length(); ++i)
            {
                // get the JSON corresponding to the planet
                JSONObject jsonPlanet = jsonPlanets.getJSONObject(i);

                String name = jsonPlanet.getString("name");
                String climate = jsonPlanet.getString("climate");
                String terrain = jsonPlanet.getString("terrain");

                long population = extractLongValueFromJsonPlanet(jsonPlanet,"population");
                long rotationPeriod = extractLongValueFromJsonPlanet(jsonPlanet,"rotation_period");
                long orbitalPeriod = extractLongValueFromJsonPlanet(jsonPlanet,"orbital_period");
                long diameter = extractLongValueFromJsonPlanet(jsonPlanet,"diameter");

                // create a new planet object
                Planet newPlanet = new Planet(name,climate,terrain,population,diameter, (int)orbitalPeriod, (int)rotationPeriod);

                // store it in our planets List field
                this.planets.add(newPlanet);
            }

        } catch (JSONException e) {
            Log.e(TAG,"can't parse json string correctly");
            e.printStackTrace();
        }
    }

    private long extractLongValueFromJsonPlanet(JSONObject jsonPlanet, String name)
    {
        try {
            return jsonPlanet.getLong(name);
        }

        catch(JSONException e)
        {
            return 0;
        }
    }

}
