package com.example.mentis.business.data;

import com.example.mentis.business.logic.ValidationStatus;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Voxel {

    private SimpleObjectProperty<ValidationStatus> validationStatus = new SimpleObjectProperty<>(ValidationStatus.UNDECIDED);
    private SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    private final int row;
    private final int col;

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

}
