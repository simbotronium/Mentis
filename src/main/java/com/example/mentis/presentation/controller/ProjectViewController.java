package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Area;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.components.MemberListCell;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ProjectViewController implements Controller {

    @FXML
    private StackPane root;

    @FXML
    private Label memberLabel;

    @FXML
    private ListView<Member> memberListView;
    @FXML
    private Label nameLabel;

    private NewMemberOverlayController overlayController;

    @FXML
    private AnchorPane projectPane;
    @FXML
    private VBox areasBox;
    @FXML
    private Label deviationLabel;

    private final Manager manager = Manager.getInstance();

    @FXML
    public void initialize() {
        updateView(manager.getCurrentProject());

        manager.currentProjectProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("changed project");
            if (newValue != null) {
                updateView(newValue);

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

    private void updateView(Project newProject) {
        memberLabel.setText("Members: " + newProject.getMembers().size());
        nameLabel.setText(newProject.getName());
        deviationLabel.setText("max. Deviation: " + newProject.getMaxDeviation() + "%");
        createAreaComponents(newProject.getAreas());
    }

    private void createAreaComponents(ObservableList<Area> areas) {
        for (Area area: areas) {
            HBox hBox = new HBox();
            Circle circle = new Circle(10);
            circle.setFill(area.color());
            Label label = new Label(area.name());
            label.getStyleClass().add("standard-text");
            hBox.setPadding(new Insets(2));
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getChildren().addAll(circle, label);
            areasBox.getChildren().add(hBox);
        }
    }

    public Node getRoot() {
        return root;
    }

    public void onAddNewMember() {
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

    public void onBack() {
        ViewManager.getInstance().changeView(View.MAIN_MENU);
    }

}
