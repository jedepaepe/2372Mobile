package eu.epfc.swexplorer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView planetsRecyclerView;
    private List<Planet> planets;

    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planets = new ArrayList<>();
        planetsRecyclerView = findViewById(R.id.recyclerview_cards);

        // set the adapter of the RecyclerView
        SWPlanetsAdapter swPlanetsAdapter = new SWPlanetsAdapter();
        planetsRecyclerView.setAdapter(swPlanetsAdapter);

        // set the layoutManager of the recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        planetsRecyclerView.setLayoutManager(layoutManager);

        loadPlanetData();

        swPlanetsAdapter.setPlanetData(planets);
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
            Context applicationContext = getApplicationContext();
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
