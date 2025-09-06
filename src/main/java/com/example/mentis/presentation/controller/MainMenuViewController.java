package com.example.mentis.presentation.controller;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.data.Participant;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.data.ProjectListEntry;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.Side;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.components.ProjectListCell;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class MainMenuViewController implements Controller {

    private final Manager manager = Manager.getInstance();

    @FXML
    private StackPane root;
    @FXML
    private ListView<ProjectListEntry> projectsListView;


    @FXML
    public void initialize() {
        projectsListView.setFixedCellSize(40);
        projectsListView.setFocusTraversable(false);
        projectsListView.setItems(DataManager.getEntries());
        projectsListView.setCellFactory(list -> new ProjectListCell());

        projectsListView.prefHeightProperty().bind(
                Bindings.min(
                        Bindings.size(projectsListView.getItems()).multiply(projectsListView.getFixedCellSize()),
                        500
                )
        );
    }

    @FXML
    protected void onSomeProject() {
        Project testProject = new Project("normal", 256, 5, "MyProject");
        for (int i = 0; i < 5; i++) {
            testProject.addParticipant(new Participant(12, Side.RIGHT));
        }
        manager.currentProjectProperty().set(testProject);
        ViewManager.getInstance().changeView(View.PROJECT);
    }

    @FXML
    protected void onNewProject() {
        manager.currentProjectProperty().set(new Project());
        ViewManager.getInstance().changeView(View.PROJECT_SETTINGS);
    }

    @FXML
    protected void onEdit() {

    }

    public Node getRoot() {
        return root;
    }
}
