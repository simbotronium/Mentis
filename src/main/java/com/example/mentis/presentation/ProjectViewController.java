package com.example.mentis.presentation;

import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.logic.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ProjectViewController implements Controller {

    @FXML
    private Node root;

    @FXML
    private Label memberLabel;

    @FXML
    private VBox memberBox;

    private final Manager manager = Manager.getInstance();

    @FXML
    public void initialize() {
        manager.currentProjectProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                refreshView(newValue);
            }
        });

        if (manager.getCurrentProject() != null) {
            refreshView(manager.getCurrentProject());
        }
    }

    public Node getRoot() {
        return root;
    }

    private void refreshView(Project project) {
        memberLabel.setText("Teilnehmer: " + project.getMembers().size());

        memberBox.getChildren().clear();
        createMemberComponents(project.getMembers());
    }

    private void createMemberComponents(List<Member> members) {
        try {
            for (Member member : members) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/Components/MemberListElement.fxml"));
                Parent root = loader.load();

                MemberListElementController controller = loader.getController();
                controller.setMember(member);
                memberBox.getChildren().add(root);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while loading Memberlist:");
            e.printStackTrace();
        }
    }

}
