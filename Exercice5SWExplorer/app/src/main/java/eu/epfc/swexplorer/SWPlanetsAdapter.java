package eu.epfc.swexplorer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Quentin on 20/02/2018.
 */

public class SWPlanetsAdapter extends RecyclerView.Adapter<SWPlanetsAdapter.PlanetViewHolder> {

    private List<Planet> planetData;


    public void setPlanetData(List<Planet> planetData) {

        this.planetData = planetData;

        //notify the adapter that the data have changed
        this.notifyDataSetChanged();
    }

    @Override
    public PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // get a layoutInflater from the context attached to the parent view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // inflate the layout item_card in a view
        View planetView = layoutInflater.inflate(R.layout.item_card,parent,false);

        // create a new ViewHolder with the view planetView
        return new PlanetViewHolder(planetView);
    }

    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {

        // get the corresponding planet data (based on the parameter position)
        Planet planet = planetData.get(position);

        // get the TextView of the item
        ViewGroup itemViewGroup = (ViewGroup)holder.itemView;
        TextView planetNameTextView = itemViewGroup.findViewById(R.id.text_planet_name);

        // set the text of the TextView
        planetNameTextView.setText(planet.getName());

        // set the color of the background
        if (planet.getPopulation() == 0)
        {
            itemViewGroup.setBackgroundResource(R.color.planetColor1);
        }

        else if (planet.getPopulation() <= 100000000)
        {
            itemViewGroup.setBackgroundResource(R.color.planetColor2);

        }
        else if (planet.getPopulation() <= 1000000000)
        {
            itemViewGroup.setBackgroundResource(R.color.planetColor3);

        }
        else
        {
            itemViewGroup.setBackgroundResource(R.color.planetColor4);
        }
    }

    @Override
    public int getItemCount() {
        if (planetData != null) {
            return planetData.size();
        }
        else
        {
            return 0;
        }
    }

    class PlanetViewHolder extends RecyclerView.ViewHolder{

        private PlanetViewHolder(View itemView) {

            super(itemView);
        }

    }

}
