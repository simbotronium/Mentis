package com.example.mentis.business.data;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Examination {

    private SimpleStringProperty exam = new SimpleStringProperty("X");
    private SimpleIntegerProperty slice = new SimpleIntegerProperty(0);
    private SimpleBooleanProperty valid = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty mapped = new SimpleBooleanProperty(false);

    public Examination() {
        this("X", 0);
    }

    public Examination(String exam, int slice) {
        this.exam.set(exam);
        this.slice.set(slice);
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
}
