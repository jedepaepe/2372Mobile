package com.example.jedepaepe.swexplorer;

/**
 * Created by jedepaepe on 12/03/2018.
 */

public class Planet {
    private String name;
    private String climate;
    private String terrain;
    private long population;
    private double diameter;
    private int orbitalPeriod;
    private int rotationPeriod;

    public Planet() {

    }

    public Planet(String name, String climate, String terrain, long population, double diameter, int orbitalPeriod, int rotationPeriod) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public int getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(int orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public int getRotationPeriod() {
        return rotationPeriod;
    }

    public void setRotationPeriod(int rotationPeriod) {
        this.rotationPeriod = rotationPeriod;
    }
}
