package com.example.mentis.business.logic;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Participant;
import com.example.mentis.business.data.Project;
import com.example.mentis.presentation.ViewManager;
import javafx.beans.property.SimpleObjectProperty;

public class Manager {

    private static final Manager instance = new Manager();
    private final SimpleObjectProperty<Project> currentProject = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Participant> currentParticipant = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Examination> currentExamination = new SimpleObjectProperty<>();

    private Manager() {}

    public static Manager getInstance() {
        return instance;
    }

    public Project getCurrentProject() {
        return currentProject.get();
    }

    public SimpleObjectProperty<Project> currentProjectProperty() {
        return currentProject;
    }

    public Participant getCurrentParticipant() {
        return currentParticipant.get();
    }

    public SimpleObjectProperty<Participant> currentParticipantProperty() {
        return currentParticipant;
    }

    public void addExamination(Examination e) {
        if (currentParticipant.isNull().get()) {
            return;
        }
        currentParticipant.get().addExamination(e);
    }

    public Examination getCurrentExamination() {
        return currentExamination.get();
    }

    public SimpleObjectProperty<Examination> currentExaminationProperty() {
        return currentExamination;
    }

    public void confirmProject() {
        DataManager.confirmProject(currentProject.get());
        ViewManager.getInstance().changeView(View.PROJECT);
    }

}
