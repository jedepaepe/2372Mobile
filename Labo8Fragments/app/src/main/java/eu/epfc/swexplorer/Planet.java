package eu.epfc.swexplorer;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * Created by Quentin on 25/02/2018.
 */

public class Planet implements Serializable {

    private String name;
    private  String climate;
    private  String terrain;
    private long population;
    private double diameter;
    private int orbitalPeriod;
    private int rotationPeriod;


    Planet(String name, String climate, String terrain, long population, double diameter, int orbitalPeriod, int rotationPeriod)
    {
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
        this.population = population;
        this.diameter = diameter;
        this.orbitalPeriod = orbitalPeriod;
        this.rotationPeriod = rotationPeriod;
    }

    public String getName() {
        return name;
    }

    public String getClimate() {
        return climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public long getPopulation() {
        return population;
    }

    public double getDiameter() {
        return diameter;
    }


    public int getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public int getRotationPeriod() {
        return rotationPeriod;
    }
}
