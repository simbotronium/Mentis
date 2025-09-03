package com.example.mentis.business.data;

import com.example.mentis.business.logic.ValidationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Voxel {

    private final SimpleObjectProperty<ValidationStatus> validationStatus = new SimpleObjectProperty<>(ValidationStatus.UNDECIDED);
    @JsonIgnore
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    private int row;
    private int col;
    private SimpleObjectProperty<Area> area = new SimpleObjectProperty<>();

    public Voxel() {}

    public Voxel(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus.get();
    }

    public SimpleObjectProperty<ValidationStatus> validationStatusProperty() {
        return validationStatus;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus.set(validationStatus);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Area getArea() {
        return area.get();
    }

    public void setArea(Area area) {
        this.area .set(area);
    }

    public SimpleObjectProperty<Area> areaProperty() {
        return area;
    }

    @Override
    public String toString() {
        return "(" + this.row + " | " + this.col + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Voxel other)) return false;
        return other.col == this.col && other.row == this.row;
    }

}
