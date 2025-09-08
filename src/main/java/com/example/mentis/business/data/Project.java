package com.example.mentis.business.data;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.logic.UID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private final SimpleStringProperty typeOfSpectroscopy = new SimpleStringProperty("");
    private final SimpleIntegerProperty voxelDimensionSize = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty maxDeviation = new SimpleIntegerProperty(5);
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final ObservableList<Area> areas = FXCollections.observableArrayList();
    private final ObservableList<Participant> participants = FXCollections.observableArrayList();
    private final ObservableList<String> infos = FXCollections.observableArrayList();
    private long ID;
    @JsonIgnore
    private final Logger log = LoggerFactory.getLogger(Project.class);

    public Project() {
        this("normal", 16, 5, "project" + LocalDateTime.now());
    }

    public Project(String typeOfSpectroscopy, int voxelDimensionSize, int maxDeviation, String name) {
        this.typeOfSpectroscopy.set(typeOfSpectroscopy);
        this.voxelDimensionSize.set(voxelDimensionSize);
        this.maxDeviation.set(maxDeviation);
        this.name.set(name);
        this.ID = UID.next();
        while (DataManager.getProjectById(this.getID()) != null) {
            this.ID = UID.next();
        }
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

    public ObservableList<Participant> getParticipants() {
        return participants;
    }

    public void addParticipant(Participant participant) {
        log.info("New Participant with id: " + participant.getId());
        this.participants.add(participant);
    }

    public void deleteArea(long id) {

    }

    public ObservableList<Area> getAreas() {
        return areas;
    }

    public ObservableList<String> getInfos() {
        return infos;
    }

    public void addInfo(String info) {
        this.infos.add(info);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Project)) return false;
        return this.ID == (((Project) o).ID);
    }

    @Override
    public String toString() {
        return "Project(" + this.ID + ": " + this.name.get()  +")";
    }
}
