package com.example.mentis.presentation;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.Side;
import com.example.mentis.business.logic.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainMenuViewController implements Controller {

    private final Manager manager = Manager.getInstance();

    @FXML
    private StackPane root;

    @FXML
    protected void onSomeProject() {
        Project testProject = new Project("normal", 256, 5, "MyProject");
        for (int i = 0; i < 20; i++) {
            testProject.addMember(new Member(12, Side.RIGHT));
        }
        manager.currentProjectProperty().set(testProject);
        manager.currentViewProperty().set(View.PROJECT);
    }

    @FXML
    protected void onNewProject() {
        manager.currentViewProperty().set(View.PROJECT_SETTINGS);
    }

    public Node getRoot() {
        return root;
    }
}
