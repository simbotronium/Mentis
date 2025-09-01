package com.example.mentis.business.data;

import com.example.mentis.business.logic.Side;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private final SimpleLongProperty id = new SimpleLongProperty(0);
    private final SimpleIntegerProperty age = new SimpleIntegerProperty(0);
    private final SimpleBooleanProperty download = new SimpleBooleanProperty(false);
    private ObservableList<Examination> examinations = FXCollections.observableArrayList();

    private final SimpleObjectProperty<Side> side = new SimpleObjectProperty<>(Side.LEFT);

    public Member() {

    }

    public Member(long id, Side side) {
        this(id, side, new ArrayList<>());
    }

    public Member(long id, Side side, List<Examination> examinations) {
        this.id.set(id);
        this.side.set(side);
        this.examinations.setAll(examinations);
    }

    public ObservableList<Examination> getExaminations() {
        return examinations;
    }

    public void addExamination(Examination e) {
        System.out.println("New Examination with exam " + e.getExam());
        examinations.add(e);
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

    public void setId(long id) {
        this.id.set(id);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public void setDownload(boolean download) {
        this.download.set(download);
    }

    public void setExaminations(ObservableList<Examination> examinations) {
        this.examinations = examinations;
    }

    public void setSide(Side side) {
        this.side.set(side);
    }
}
