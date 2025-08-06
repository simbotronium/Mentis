package com.example.mentis.business.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Examination {

    private SimpleStringProperty exam = new SimpleStringProperty("");
    private SimpleIntegerProperty slice = new SimpleIntegerProperty(0);

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
}
