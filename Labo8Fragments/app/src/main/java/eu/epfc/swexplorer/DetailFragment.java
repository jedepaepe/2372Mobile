package eu.epfc.swexplorer;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private Planet planet;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        updatePlanet();
    }

    private void updatePlanet() {
        if(planet != null) {
            // set orbital period TextView
            TextView orbitalPeriodTextView = getView().findViewById(R.id.text_orbital_period_value);
            orbitalPeriodTextView.setText(Integer.toString(planet.getOrbitalPeriod()));

            // set rotation period TextView
            TextView rotationPeriodTextView = getView().findViewById(R.id.text_rotation_period_value);
            rotationPeriodTextView.setText(Integer.toString(planet.getRotationPeriod()));

            // set diameter TextView
            TextView diameterTextView = getView().findViewById(R.id.text_diameter_value);
            diameterTextView.setText(Double.toString(planet.getDiameter()));

            // set climate TextView
            TextView climateTextView = getView().findViewById(R.id.text_climate_value);
            climateTextView.setText(planet.getClimate());

            // set planet name TextView
            TextView planetTextView = getView().findViewById(R.id.text_planet_value);
            planetTextView.setText(planet.getName());

            // the file names are always lower case
            String planetImageName = planet.getName().toLowerCase();
            Resources resources = getResources();
            // get the resource ID of the image file from its name
            int resourceId = resources.getIdentifier(planetImageName, "drawable", getActivity().getPackageName());

            // if there is no image for that planet
            if (resourceId == 0) {
                // get the resource from the generic_planet image
                resourceId = resources.getIdentifier("generic_planet", "drawable", getActivity().getPackageName());
            }
            ImageView imageView = getView().findViewById(R.id.image_planet);
            imageView.setImageResource(resourceId);
        }
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
        updatePlanet();
    }
}
