package com.example.mentis.presentation;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.data.ProjectListEntry;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.Side;
import com.example.mentis.business.logic.View;
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
            testProject.addMember(new Member(12, Side.RIGHT));
        }
        manager.currentProjectProperty().set(testProject);
        manager.currentViewProperty().set(View.PROJECT);
    }

    @FXML
    protected void onNewProject() {
        manager.currentProjectProperty().set(new Project());
        manager.currentViewProperty().set(View.PROJECT_SETTINGS);
    }

    @FXML
    protected void onEdit() {

    }

    public Node getRoot() {
        return root;
    }
}
