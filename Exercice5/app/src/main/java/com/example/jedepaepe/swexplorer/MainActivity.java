package com.example.jedepaepe.swexplorer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Planet> planetDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPlanetDatas();
        SWPlanetsAdapter swPlanetsAdapter = new SWPlanetsAdapter();
        swPlanetsAdapter.setPlanetDatas(planetDatas);
        swPlanetsAdapter.notifyDataSetChanged();
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setAdapter(swPlanetsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            Context applicationContext = getApplicationContext();
            InputStream inputStream = applicationContext.getAssets().open("planets.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void loadPlanetDatas() {
        String json = loadJSONFromAsset();
        if(json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonPlanets = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonPlanets.length(); ++i) {
                    JSONObject jsonPlanet = jsonPlanets.getJSONObject(i);
                    long population = 0;
                    try {
                        population = jsonPlanet.getLong("population");
                    } catch (Exception ex) {
                        // cas normal
                    }
                    /*
                    double diameter = 0.0;
                    try {
                        diameter = jsonObject.getDouble("diameter");
                    } catch (Exception ex) {
                        //
                    }

                    planetDatas.add(new Planet(
                            jsonPlanet.getString("name"),
                            jsonPlanet.getString("climate"),
                            jsonPlanet.getString("terrain"),
                            population,
                            diameter,
                            jsonPlanet.getInt("orbital_period"),
                            jsonPlanet.getInt("rotation_period")
                    ));
                    */
                    Planet planet = new Planet();
                    try {
                        planet.setName(jsonPlanet.getString("name"));
                        planet.setPopulation(population);
                        planetDatas.add(planet);
                    } catch (Exception ex) {
                        // TODO
                    }
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
}
