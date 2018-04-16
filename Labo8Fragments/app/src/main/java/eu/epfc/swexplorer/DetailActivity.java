package eu.epfc.swexplorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get the intent used to create this Activity
        Intent intent = getIntent();

        // get a reference to the fragment
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);

        // extract a Planet object from it
        Planet selectedPlanet = (Planet) intent.getSerializableExtra("planetObject");

        // inject the planet in the fragment
        detailFragment.setPlanet(selectedPlanet);
    }
}
