package com.example.mentis.presentation;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.logic.Manager;
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
        manager.currentViewProperty().set(View.PROJECT);
        Project testProject = new Project("normal", 256, 5, "MyProject");
        Member testMember = new Member(14);
        testMember.addExamination(new Examination("A", 1));
        testMember.addExamination(new Examination("A", 3));
        testMember.addExamination(new Examination("B", 1));
        testMember.addExamination(new Examination("B", 3));
        testMember.addExamination(new Examination("C", 1));
        testMember.addExamination(new Examination("C", 3));
        Member testMember2 = new Member(15);
        testMember2.addExamination(new Examination("A", 1));
        testMember2.addExamination(new Examination("A", 3));
        testMember2.addExamination(new Examination("B", 1));
        testMember2.addExamination(new Examination("B", 3));
        testMember2.addExamination(new Examination("C", 1));
        testMember2.addExamination(new Examination("C", 3));
        testProject.addMember(testMember);
        testProject.addMember(testMember2);
        manager.currentProjectProperty().set(testProject);
    }

    @FXML
    protected void onNewProject() {
        manager.currentViewProperty().set(View.PROJECT_SETTINGS);
    }

    public Node getRoot() {
        return root;
    }
}
