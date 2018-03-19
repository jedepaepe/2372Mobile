package eu.epfc.swEplorer;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import eu.epfc.swexplorer.Planet;
import eu.epfc.swexplorer.R;

public class DetailActivity extends AppCompatActivity {

    ImageView vImagePlanet;
    TextView vRotationPeriod;
    TextView vOrbitalPeriod;
    TextView vDiameter;
    TextView vClimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        vImagePlanet = findViewById(R.id.image_planet);
        vRotationPeriod = findViewById(R.id.value_rotation_period);
        vOrbitalPeriod = findViewById(R.id.value_orbital_period);
        vDiameter = findViewById(R.id.value_diameter);
        vClimate = findViewById(R.id.value_climate);

        Intent intent = getIntent();
        Planet planet = (Planet) intent.getSerializableExtra("planet");

        vRotationPeriod.setText("" + planet.getRotationPeriod());
        vOrbitalPeriod.setText("" + planet.getOrbitalPeriod());
        vDiameter.setText("" + planet.getDiameter());
        vClimate.setText(planet.getClimate());

        String planetImageName = planet.getName().toLowerCase();
        Resources resources = getResources();
        int resourceId = resources.getIdentifier(planetImageName, "drawable", getPackageName());
        if(resourceId == 0) {
            resourceId = resources.getIdentifier("generic_planet", "drawable", getPackageName());
        }
        vImagePlanet.setImageResource(resourceId);

    }
}
