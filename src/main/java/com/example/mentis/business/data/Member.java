package com.example.mentis.business.data;

import com.example.mentis.business.logic.Side;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private SimpleLongProperty id = new SimpleLongProperty(0);
    private SimpleIntegerProperty age = new SimpleIntegerProperty(0);
    private SimpleBooleanProperty download = new SimpleBooleanProperty(false);
    private List<Examination> examinations = new ArrayList<>();

    private SimpleObjectProperty<Side> side = new SimpleObjectProperty<>(Side.LEFT);

    public Member() {

    }

    public Member(long id, Side side) {
        this(id, side, new ArrayList<>());
    }

    public Member(long id, Side side, List<Examination> examinations) {
        this.id.set(id);
        this.side.set(side);
        this.examinations = examinations;
    }

    public List<Examination> getExaminations() {
        return examinations;
    }

    public void addExamination(Examination e) {
        examinations.add(e);
    }

    public void setExaminations(List<Examination> examinations) {
        this.examinations = examinations;
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public boolean isDownload() {
        return download.get();
    }

    public SimpleBooleanProperty downloadProperty() {
        return download;
    }

    public Side getSide() {
        return side.get();
    }

    public SimpleObjectProperty<Side> sideProperty() {
        return side;
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }
}
