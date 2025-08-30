package com.example.mentis.business.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Project {

    private final SimpleStringProperty typeOfSpectroscopy = new SimpleStringProperty("");
    private final SimpleIntegerProperty voxelDimensionSize = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty maxDeviation = new SimpleIntegerProperty(5);
    private final SimpleStringProperty name = new SimpleStringProperty("");

    private final ObservableList<Area> areas = FXCollections.observableArrayList();
    private final ObservableList<Member> members = FXCollections.observableArrayList();

    public Project() {
        this("normal", 16, 5, "project" + LocalDateTime.now());
    }

    public Project(String typeOfSpectroscopy, int voxelDimensionSize, int maxDeviation, String name) {
        this.typeOfSpectroscopy.set(typeOfSpectroscopy);
        this.voxelDimensionSize.set(voxelDimensionSize);
        this.maxDeviation.set(maxDeviation);
        this.name.set(name);
    }

    public String getTypeOfSpectroscopy() {
        return typeOfSpectroscopy.get();
    }

    public SimpleStringProperty typeOfSpectroscopyProperty() {
        return typeOfSpectroscopy;
    }

    public void setTypeOfSpectroscopy(String typeOfSpectroscopy) {
        this.typeOfSpectroscopy.set(typeOfSpectroscopy);
    }

    public int getVoxelDimensionSize() {
        return voxelDimensionSize.get();
    }

    public SimpleIntegerProperty voxelDimensionSizeProperty() {
        return voxelDimensionSize;
    }

    public void setVoxelDimensionSize(int voxelDimensionSize) {
        this.voxelDimensionSize.set(voxelDimensionSize);
    }

    public int getMaxDeviation() {
        return maxDeviation.get();
    }

    public SimpleIntegerProperty maxDeviationProperty() {
        return maxDeviation;
    }

    public void setMaxDeviation(int maxDeviation) {
        this.maxDeviation.set(maxDeviation);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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
