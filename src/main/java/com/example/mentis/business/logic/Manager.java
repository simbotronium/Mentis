package com.example.mentis.business.logic;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import javafx.beans.property.SimpleObjectProperty;

public class Manager {

    private static final Manager instance = new Manager();
    private SimpleObjectProperty<View> currentView = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Project> currentProject = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Member> currentMember = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Examination> currentExamination = new SimpleObjectProperty<>();

    private Manager() {

    }

    public static Manager getInstance() {
        return instance;
    }

    public SimpleObjectProperty<View> currentViewProperty() {
        return currentView;
    }

    public Project getCurrentProject() {
        return currentProject.get();
    }

    public SimpleObjectProperty<Project> currentProjectProperty() {
        return currentProject;
    }

    public Member getCurrentMember() {
        return currentMember.get();
    }

    public SimpleObjectProperty<Member> currentMemberProperty() {
        return currentMember;
    }

    public void addMember(Member m) {
        if (currentProject.isNull().get()) {
            return;
        }
        currentProject.get().addMember(m);
    }

    public void addExamination(Examination e) {
        if (currentMember.isNull().get()) {
            return;
        }
        currentMember.get().addExamination(e);
    }

    public Examination getCurrentExamination() {
        return currentExamination.get();
    }

    public SimpleObjectProperty<Examination> currentExaminationProperty() {
        return currentExamination;
    }

    public void setCurrentExamination(Examination currentExamination) {
        this.currentExamination.set(currentExamination);
    }
}
