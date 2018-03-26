package eu.epfc.swexplorer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Quentin on 20/02/2018.
 */

public class SWPlanetsAdapter extends RecyclerView.Adapter<SWPlanetsAdapter.PlanetViewHolder> {

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    private List<Planet> planetDatas;

    final private ListItemClickListener listItemClickListener;

    public SWPlanetsAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public void setPlanetDatas(List<Planet> planetDatas) {

        this.planetDatas = planetDatas;

        //notify the adapter that the data have changed
        this.notifyDataSetChanged();
    }

    @Override
    public PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // get a layoutInflater from the context attached to the parent view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // inflate the layout item_planet in a view
        View planetView = layoutInflater.inflate(R.layout.item_planet,parent,false);

        // create a new ViewHolder with the view planetView
        return new PlanetViewHolder(planetView);
    }

    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {

        // get the corresponding planet data (based on the parameter position)
        Planet planet = planetDatas.get(position);

        // get the planet name TextView of the item
        ViewGroup itemViewGroup = (ViewGroup)holder.itemView;
        TextView planetNameTextView = itemViewGroup.findViewById(R.id.text_planet_name);

        // set the text of the TextView
        planetNameTextView.setText(planet.getName());

        // get the diameter TextView of the item
        TextView diameterTextView = itemViewGroup.findViewById(R.id.text_item_diameter);
        String textToDisplay = "Diameter: " + Double.toString(planet.getDiameter());
        diameterTextView.setText(textToDisplay);

        TextView terrainTextView = itemViewGroup.findViewById(R.id.text_item_terrain);
        textToDisplay = "Terrain: " + planet.getTerrain();
        terrainTextView.setText(textToDisplay);


        // get imageViews for population
        ImageView populationImageView1 = itemViewGroup.findViewById(R.id.image_population1);
        ImageView populationImageView2 = itemViewGroup.findViewById(R.id.image_population2);
        ImageView populationImageView3 = itemViewGroup.findViewById(R.id.image_population3);

        // set the population icons
        if (planet.getPopulation() == 0)
        {
            populationImageView1.setVisibility(View.INVISIBLE);
            populationImageView2.setVisibility(View.INVISIBLE);
            populationImageView3.setVisibility(View.INVISIBLE);
        }

        else if (planet.getPopulation() <= 100000000)
        {
            populationImageView1.setVisibility(View.VISIBLE);
            populationImageView2.setVisibility(View.INVISIBLE);
            populationImageView3.setVisibility(View.INVISIBLE);

        }
        else if (planet.getPopulation() <= 1000000000)
        {
            populationImageView1.setVisibility(View.VISIBLE);
            populationImageView2.setVisibility(View.VISIBLE);
            populationImageView3.setVisibility(View.INVISIBLE);

        }
        else
        {
            populationImageView1.setVisibility(View.VISIBLE);
            populationImageView2.setVisibility(View.VISIBLE);
            populationImageView3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (planetDatas != null) {
            return planetDatas.size();
        }
        else
        {
            return 0;
        }
    }

    class PlanetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private PlanetViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            SWPlanetsAdapter.this.listItemClickListener.onListItemClick(clickedPosition);
        }

    }

}
