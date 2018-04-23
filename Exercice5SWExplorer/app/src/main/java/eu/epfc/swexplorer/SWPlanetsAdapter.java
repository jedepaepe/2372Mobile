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

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    private List<Planet> planetData;
    final private ListItemClickListener listItemClickListener;

    public SWPlanetsAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

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
        TextView diameterTextView = itemViewGroup.findViewById(R.id.text_diameter);
        TextView terrainTextView = itemViewGroup.findViewById(R.id.text_terrain);
        ImageView[] populationImageViewList = {
                itemViewGroup.findViewById(R.id.image_population_1),
                itemViewGroup.findViewById(R.id.image_population_2),
                itemViewGroup.findViewById(R.id.image_population_3)};

        // set the text of the TextView
        planetNameTextView.setText(planet.getName());

        // set the diameter
        diameterTextView.setText("Diameter: " + planet.getDiameter());

        // set the terrain
        terrainTextView.setText("Terrain: " + planet.getTerrain());

        // show images
        int nrImages = 0;
        if (planet.getPopulation() == 0) nrImages = 0;
        else if (planet.getPopulation() <= 100000000) nrImages = 1;
        else if (planet.getPopulation() <= 1000000000) nrImages = 2;
        else nrImages = 3;
        for(int i=0; i<populationImageViewList.length; i++) populationImageViewList[i].setVisibility((i<nrImages) ? View.VISIBLE : View.INVISIBLE);
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

    class PlanetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private PlanetViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // récupère la référence sur le parent
            SWPlanetsAdapter.this.listItemClickListener.onListItemClick(getAdapterPosition());
        }

    }

}
