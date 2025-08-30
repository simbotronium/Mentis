package com.example.mentis.business.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private String typeOfSpectroscopy;
    private int voxelDimensionSize = 0;
    private int maxDeviation;
    private String name;

    private ObservableList<Area> areas = FXCollections.observableArrayList();
    private ObservableList<Member> members = FXCollections.observableArrayList();

    public Project() {
        this("normal", 256, 5, "project" + LocalDateTime.now());
    }

    public Project(String typeOfSpectroscopy, int numVoxels, int maxDeviation, String name) {
        this.typeOfSpectroscopy = typeOfSpectroscopy;
        this.voxelDimensionSize = numVoxels;
        this.maxDeviation = maxDeviation;
        this.name = name;
    }

    public void setTypeOfSpectroscopy(String typeOfSpectroscopy) {
        this.typeOfSpectroscopy = typeOfSpectroscopy;
    }

    public void setVoxelDimensionSize(int voxelDimensionSize) {
        this.voxelDimensionSize = voxelDimensionSize;
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

    public ObservableList<Member> getMembers() {
        return members;
    }

    public void addMember(Member member) {
        System.out.println("New Member with id: " + member.getId());
        this.members.add(member);
    }

    public void deleteArea(long id) {

    }

    public ObservableList<Area> getAreas() {
        return areas;
    }
}
