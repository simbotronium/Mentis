package com.example.mentis.business.logic;

import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Manager {

    private static final Manager instance = new Manager();
    private SimpleObjectProperty<View> currentView = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Project> currentProject = new SimpleObjectProperty<>();
    private SimpleIntegerProperty changed = new SimpleIntegerProperty(0);

    private Manager() {}

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

    public void addMember(Member m) {
        if (currentProject == null) {
            return;
        }
        currentProject.get().addMember(m);
        changed.set((changed.get() + 1) % 2);
    }

    public SimpleIntegerProperty changedProperty() {
        return changed;
    }
}
