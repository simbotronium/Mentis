package com.example.mentis.business.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;

public class Examination {

    private SimpleStringProperty exam = new SimpleStringProperty("X");
    private SimpleIntegerProperty slice = new SimpleIntegerProperty(0);
    private SimpleBooleanProperty valid = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty mapped = new SimpleBooleanProperty(false);
    private Voxel[] voxels;
    private File overlayFile;
    private final int voxelDimensionSize;

    public Examination() {
        this("X", 0, 16);
    }

    public Examination(String exam, int slice, int voxelDimensionSize) {
        this.exam.set(exam);
        this.slice.set(slice);
        this.voxelDimensionSize = voxelDimensionSize;
        createVoxels();
    }

    private void createVoxels() {
        this.voxels = new Voxel[this.voxelDimensionSize * this.voxelDimensionSize];
        for (int row = 0; row < this.voxelDimensionSize; row++) {
            for (int col = 0; col < this.voxelDimensionSize; col++) {
                voxels[col + row * this.voxelDimensionSize] = new Voxel(row, col);
            }
        }
    }

    public String getExam() {
        return exam.get();
    }

    public SimpleStringProperty examProperty() {
        return exam;
    }

    public int getSlice() {
        return slice.get();
    }

    public SimpleIntegerProperty sliceProperty() {
        return slice;
    }

    public boolean isValid() {
        return valid.get();
    }

    public SimpleBooleanProperty validProperty() {
        return valid;
    }

    public boolean isMapped() {
        return mapped.get();
    }

    public SimpleBooleanProperty mappedProperty() {
        return mapped;
    }

    public File getOverlayFile() {
        return overlayFile;
    }

    public void setOverlayFile(File overlayFile) {
        this.overlayFile = overlayFile;
    }

    public Voxel[] getVoxels() {
        return voxels;
    }

    public int getVoxelDimensionSize() {
        return voxelDimensionSize;
    }

    public void setExam(String exam) {
        this.exam.set(exam);
    }

    public void setSlice(int slice) {
        this.slice.set(slice);
    }

    public void setValid(boolean valid) {
        this.valid.set(valid);
    }

    public void setMapped(boolean mapped) {
        this.mapped.set(mapped);
    }

    public void setVoxels(Voxel[] voxels) {
        this.voxels = voxels;
    }
}
