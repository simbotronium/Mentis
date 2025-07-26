package com.example.mentis.business.data;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String typeOfSpectroscopy;
    private int numVoxels = 0;
    private int maxDeviation;
    private String name;

    private List<Area> areas = new ArrayList<>();

    public Project(String typeOfSpectroscopy, int numVoxels, int maxDeviation, String name) {
        this.typeOfSpectroscopy = typeOfSpectroscopy;
        this.numVoxels = numVoxels;
        this.maxDeviation = maxDeviation;
        this.name = name;
    }

    public void setTypeOfSpectroscopy(String typeOfSpectroscopy) {
        this.typeOfSpectroscopy = typeOfSpectroscopy;
    }

    public void setNumVoxels(int numVoxels) {
        this.numVoxels = numVoxels;
    }

    public void setMaxDeviation(int maxDeviation) {
        this.maxDeviation = maxDeviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArea(Area a) {
        areas.add(a);
    }

}
