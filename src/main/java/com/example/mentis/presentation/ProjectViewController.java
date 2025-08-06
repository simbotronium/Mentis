package com.example.mentis.presentation;

import com.example.mentis.business.data.Member;
import com.example.mentis.business.logic.Manager;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ProjectViewController implements Controller {

    @FXML
    private StackPane root;

    @FXML
    private Label memberLabel;

    @FXML
    private ListView<Member> memberListView;

    private NewMemberOverlayController overlayController;

    @FXML
    private AnchorPane projectPane;

    private final Manager manager = Manager.getInstance();

    @FXML
    public void initialize() {
        manager.currentProjectProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("changed project");
            if (newValue != null) {
                memberLabel.setText("Members: " + newValue.getMembers().size());

                newValue.getMembers().addListener((ListChangeListener<? super Member>) o -> {
                    memberLabel.setText("Members: " + manager.getCurrentProject().getMembers().size());
                });
            }
        });

        memberListView.visibleProperty().bind(Bindings.isNotEmpty(manager.getCurrentProject().getMembers()));
        memberListView.managedProperty().bind(memberListView.visibleProperty());

        memberListView.setItems(manager.getCurrentProject().getMembers());
        memberListView.setCellFactory(list -> new MemberListCell());

        if (manager.getCurrentProject() != null) {
            memberLabel.setText("Members: " + manager.getCurrentProject().getMembers().size());
            manager.getCurrentProject().getMembers().addListener((ListChangeListener<? super Member>) o -> {
                memberLabel.setText("Members: " + manager.getCurrentProject().getMembers().size());
            });
        }

    }

    public Node getRoot() {
        return root;
    }

    public void onAddNewMemberButton() {
        if (overlayController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/overlays/new-member-overlay.fxml"));
                Parent overlayRoot = loader.load();
                overlayController = loader.getController();
                overlayController.showProperty().addListener((observable, oldValue, newValue) -> {
                    overlayController.getRoot().setVisible(newValue);
                    projectPane.setEffect(newValue ? new GaussianBlur(20) : null);
                });
                root.getChildren().add(overlayRoot);
                projectPane.setEffect(new GaussianBlur(20));
            } catch (IOException e) {
                System.out.println("Something went wrong while loading new member overlay:");
                e.printStackTrace();
            }
        } else {
            overlayController.refresh();
        }
        overlayController.showProperty().set(true);
    }

}
