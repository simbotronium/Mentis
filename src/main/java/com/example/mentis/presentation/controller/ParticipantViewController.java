package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Participant;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.components.ExamListCell;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
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
    private ListChangeListener<Examination> examinationListChangeListener;

    @FXML
    public void initialize() {
        examListView.setFocusModel(null);
        setupListener();
        updateView(Manager.getInstance().getCurrentParticipant());
    }

    private void setupListener() {
        examinationListChangeListener = o -> updateLabels();
        ChangeListener<Participant> participantListChangeListener = ((observable, oldValue, newValue) -> {
            if (oldValue != null) oldValue.getExaminations().removeListener(examinationListChangeListener);
            newValue.getExaminations().addListener(examinationListChangeListener);
            updateView(newValue);
        });
        manager.currentParticipantProperty().addListener(participantListChangeListener);
        manager.getCurrentParticipant().getExaminations().addListener(examinationListChangeListener);
    }

    private void updateLabels() {
        titleLabel.setText("Participant: " + manager.getCurrentParticipant().getId());
        examinationsLabel.setText("Examinations: " + manager.getCurrentParticipant().getExaminations().size());
    }

    private void updateView(Participant p) {
        if (p == null) return;
        updateLabels();

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
                log.error("Something went wrong while loading new exam overlay: " + e.getMessage());
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
