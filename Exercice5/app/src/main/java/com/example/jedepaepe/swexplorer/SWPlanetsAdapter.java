package com.example.jedepaepe.swexplorer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jedepaepe on 12/03/2018.
 */

public class SWPlanetsAdapter extends RecyclerView.Adapter<SWPlanetsAdapter.PlanetViewHolder> {

    private List<Planet> planetDatas;

    public void setPlanetDatas(List<Planet> planetDatas) {
        this.planetDatas = planetDatas;
    }

    @Override
    public PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View planetView = layoutInflater.inflate(R.layout.item_planet, parent, false);
        return new PlanetViewHolder(planetView);
    }

    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {
        Planet planet = planetDatas.get(position);
        holder.itemView.setBackgroundColor(getColor(planet.getPopulation()));
        //holder.itemView.setBackgroundColor(Color.GREEN);
        ViewGroup itemViewGroup = (ViewGroup) holder.itemView;
        TextView planetNameTextView = itemViewGroup.findViewById(R.id.planet_text_view);
        planetNameTextView.setText(planet.getName());
    }

    private int getColor(long population) {
        if(population <= 0) return Color.parseColor("#ffffff");
        else if(population <= 100_000_000) return Color.parseColor("#fffcca");
        else if(population <= 1_000_000_000) return Color.parseColor("#ffab00");
        return Color.parseColor("#ff6f00");
    }

    @Override
    public int getItemCount() {
        return planetDatas.size();
    }

    class PlanetViewHolder extends RecyclerView.ViewHolder {
        private PlanetViewHolder(View itemView) {
            super(itemView);
        }
    }
}
