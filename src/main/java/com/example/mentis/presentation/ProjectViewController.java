package com.example.mentis.presentation;

import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.logic.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ProjectViewController implements Controller {

    @FXML
    private StackPane root;

    @FXML
    private Label memberLabel;

    @FXML
    private VBox memberBox;

    private NewMemberOverlayController overlayController;

    @FXML
    private AnchorPane projectPane;

    private final Manager manager = Manager.getInstance();

    @FXML
    public void initialize() {
        manager.currentProjectProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("changed project");
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

    public void onAddNewMemberButton() {
        if (overlayController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/overlays/new-member-overlay.fxml"));
                Parent overlayRoot = loader.load();
                overlayController = loader.getController();
                overlayController.showProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        overlayController.getRoot().setVisible(false);
                        projectPane.setEffect(null);
                    }
                });
                root.getChildren().add(overlayRoot);
            } catch (IOException e) {
                System.out.println("Something went wrong while loading new member overlay:");
                e.printStackTrace();
            }
        } else {
            overlayController.refresh();
        }
        overlayController.getRoot().setVisible(true);
        projectPane.setEffect(new GaussianBlur(20));
    }

}
