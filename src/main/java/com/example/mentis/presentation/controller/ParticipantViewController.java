package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Participant;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ParticipantViewController implements Controller {

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
    private final Logger log = LoggerFactory.getLogger(ViewManager.class);

    private NewExamOverlayController overlayController;

    @FXML
    public void initialize() {
        manager.currentParticipantProperty().addListener((observable, oldValue, newValue) -> {
            log.info("changed Participant");
            if (newValue != null) {
                updateView(newValue);
            }
        });

        if (manager.getCurrentParticipant() != null) {
            updateView(manager.getCurrentParticipant());
        }
    }

    private void updateView(Participant m) {
        titleLabel.setText("Participant: " + manager.getCurrentParticipant().getId());
        examinationsLabel.setText("Examinations: " + manager.getCurrentParticipant().getExaminations().size());
        manager.getCurrentParticipant().getExaminations().addListener((ListChangeListener<? super Examination>) o
                -> examinationsLabel.setText("Examinations: " + manager.getCurrentParticipant().getExaminations().size()));
        m.getExaminations().addListener((ListChangeListener<? super Examination>) o
                -> examinationsLabel.setText("Participant: " + m.getId()));

        examListView.visibleProperty().bind(Bindings.isNotEmpty(manager.getCurrentParticipant().getExaminations()));
        examListView.managedProperty().bind(examListView.visibleProperty());
        examListView.setItems(manager.getCurrentParticipant().getExaminations());
        examListView.setCellFactory(list -> new ExamListCell());
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
                log.error("Something went wrong while loading new participant overlay: " + e.getMessage());
            }
        } else {
            overlayController.refresh();
        }
        overlayController.showProperty().set(true);
    }

    @FXML
    public void onBack() {
        ViewManager.getInstance().changeView(View.PROJECT);
    }

    @Override
    public StackPane getRoot() {
        return root;
    }
}
