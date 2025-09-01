package com.example.mentis.business.logic;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import com.example.mentis.presentation.ViewManager;
import javafx.beans.property.SimpleObjectProperty;

public class Manager {

    private static final Manager instance = new Manager();
    private final SimpleObjectProperty<Project> currentProject = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Member> currentMember = new SimpleObjectProperty<>();
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

    public Member getCurrentMember() {
        return currentMember.get();
    }

    public SimpleObjectProperty<Member> currentMemberProperty() {
        return currentMember;
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

    public void confirmProject() {
        DataManager.confirmProject(currentProject.get());
        ViewManager.getInstance().changeView(View.PROJECT);
    }

}
