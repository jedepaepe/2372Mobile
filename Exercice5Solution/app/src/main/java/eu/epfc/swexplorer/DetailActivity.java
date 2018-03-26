package eu.epfc.swexplorer;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get the intent used to create this Activity
        Intent intent = getIntent();
        // extract a Planet object from it
        Planet selectedPlanet = (Planet) intent.getSerializableExtra("planetObject");

        // set orbital period TextView
        TextView orbitalPeriodTextView = findViewById(R.id.text_orbital_period_value);
        orbitalPeriodTextView.setText(Integer.toString(selectedPlanet.getOrbitalPeriod()));

        // set rotation period TextView
        TextView rotationPeriodTextView = findViewById(R.id.text_rotation_period_value);
        rotationPeriodTextView.setText(Integer.toString(selectedPlanet.getRotationPeriod()));

        // set diameter TextView
        TextView diameterTextView = findViewById(R.id.text_diameter_value);
        diameterTextView.setText(Double.toString(selectedPlanet.getDiameter()));

        // set climate TextView
        TextView climateTextView = findViewById(R.id.text_climate_value);
        climateTextView.setText(selectedPlanet.getClimate());

        // set planet name TextView
        TextView planetTextView = findViewById(R.id.text_planet_value);
        planetTextView.setText(selectedPlanet.getName());

        // the file names are always lower case
        String planetImageName = selectedPlanet.getName().toLowerCase();
        Resources resources = getResources();
        // get the resource ID of the image file from its name
        int resourceId = resources.getIdentifier(planetImageName, "drawable",  getPackageName());

        // if there is no image for that planet
        if (resourceId == 0) {
            // get the resource from the generic_planet image
            resourceId = resources.getIdentifier("generic_planet", "drawable",  getPackageName());
        }
        ImageView imageView = findViewById(R.id.image_planet);
        imageView.setImageResource(resourceId);

    }
}
