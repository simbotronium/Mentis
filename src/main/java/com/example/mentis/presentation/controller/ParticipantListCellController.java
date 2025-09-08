package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Participant;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.transitions.DeleteButtonFadeTransition;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

public class ParticipantListCellController implements Controller {

    @FXML
    private GridPane root;

    @FXML
    private Label idLabel;

    @FXML
    private Label examinationsLabel;
    @FXML
    private Button delete;
    private DeleteButtonFadeTransition deleteButtonFadeTransition;

    private Participant participant;
    private final Manager manager = Manager.getInstance();

    public void setParticipant(Participant m) {
        participant = m;
        idLabel.setText(Long.toString(m.getId()));
        examinationsLabel.setText(createExaminationsString(m.getExaminations()));
    }

    @FXML
    public void initialize() {
        delete.setText("\u2716");
        delete.getStyleClass().add("delete-button");
        delete.setOpacity(0);
        delete.setOnAction(e -> Manager.getInstance().getCurrentProject().getParticipants().remove(participant));
        deleteButtonFadeTransition = new DeleteButtonFadeTransition(delete);

        root.setCursor(Cursor.HAND);
        root.setOnMouseClicked(e -> onEdit());
        root.hoverProperty().addListener(((observable, was, is) -> animateDelete(is)));
    }

    private void animateDelete(boolean show) {
        deleteButtonFadeTransition.play(show);
    }

    @FXML
    protected void onEdit() {
        if (participant != null) {
            manager.currentParticipantProperty().set(participant);
            ViewManager.getInstance().changeView(View.PARTICIPANT);
        }
    }

    private String createExaminationsString(List<Examination> examinations) {
        return examinations.stream()
                .map(examination -> examination.getExam() + Integer.toString(examination.getSlice()))
                .collect(Collectors.joining(","));
    }

    @Override
    public GridPane getRoot() {
        return root;
    }
}
