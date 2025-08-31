package com.example.mentis.business.data;

import com.example.mentis.business.logic.ValidationStatus;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Voxel {

    private final SimpleObjectProperty<ValidationStatus> validationStatus = new SimpleObjectProperty<>(ValidationStatus.UNDECIDED);
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    private int row;
    private int col;

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

    @Override
    public String toString() {
        return "(" + this.row + " | " + this.col + ")";
    }

}
