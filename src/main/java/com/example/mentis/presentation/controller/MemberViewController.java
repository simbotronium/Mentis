package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.presentation.components.ExamListCell;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MemberViewController implements Controller {

    private final Manager manager = Manager.getInstance();

    @FXML
    private StackPane root;
    @FXML
    private AnchorPane projectPane;
    @FXML
    private ListView<Examination> examListView;
    @FXML
    private Label examinationsLabel;
    @FXML
    private Label titleLabel;

    private NewExamOverlayController overlayController;

    @FXML
    public void initialize() {
        manager.currentMemberProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("changed Member");
            if (newValue != null) {
                titleLabel.setText("Member: " + newValue.getId());
                examinationsLabel.setText("Examinations: " + newValue.getExaminations().size());

                newValue.getExaminations().addListener((ListChangeListener<? super Examination>) o -> {
                    examinationsLabel.setText("Member: " + newValue.getId());
                });
            }
        });

        examListView.visibleProperty().bind(Bindings.isNotEmpty(manager.getCurrentMember().getExaminations()));
        examListView.managedProperty().bind(examListView.visibleProperty());

        examListView.setItems(manager.getCurrentMember().getExaminations());
        examListView.setCellFactory(list -> new ExamListCell());

        if (manager.getCurrentMember() != null) {
            titleLabel.setText("Member: " + manager.getCurrentMember().getId());
            examinationsLabel.setText("Examinations: " + manager.getCurrentMember().getExaminations().size());
            manager.getCurrentMember().getExaminations().addListener((ListChangeListener<? super Examination>) o -> {
                examinationsLabel.setText("Examinations: " + manager.getCurrentMember().getExaminations().size());
            });
        }
    }

    public void onAddNewExamButton() {
        if (overlayController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/overlays/new-exam-overlay.fxml"));
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

    @Override
    public StackPane getRoot() {
        return root;
    }
}
