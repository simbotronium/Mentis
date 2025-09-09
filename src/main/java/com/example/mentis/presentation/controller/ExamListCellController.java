package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.transitions.DeleteButtonFadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ExamListCellController implements Controller {

    @FXML
    private GridPane root;
    @FXML
    private Label examLabel;
    @FXML
    private Label sliceLabel;
    @FXML
    private Circle valid;
    @FXML
    private Circle mapped;
    @FXML
    private Button delete;

    private Examination exam;
    private DeleteButtonFadeTransition deleteButtonFadeTransition;

    private final Color red = new Color(1.0, 0.0, 0.0, 1.0);
    private final Color green = new Color(0.0, 1.0, 0.0, 1.0);
    @FXML
    public void initialize() {
        delete.setText("\u2716");
        delete.getStyleClass().add("delete-button");
        delete.setOpacity(0);
        delete.setOnAction(e -> Manager.getInstance().getCurrentParticipant().getExaminations().remove(exam));
        deleteButtonFadeTransition = new DeleteButtonFadeTransition(delete);

        root.setCursor(Cursor.HAND);
        root.setOnMouseClicked(e -> onEdit());
        root.hoverProperty().addListener(((observable, was, is) -> animateDelete(is)));
    }

    public void setExamination(Examination exam) {
        this.exam = exam;
        examLabel.setText(exam.getExam().toUpperCase());
        sliceLabel.setText(Integer.toString(exam.getSlice()));
        valid.setFill(exam.isValid() ? green : red);
        mapped.setFill(exam.isMapped() ? green : red);
    }

    private void animateDelete(boolean show) {
        deleteButtonFadeTransition.play(show);
    }

    @FXML
    public void onEdit() {
        Manager manager = Manager.getInstance();
        if (exam != null) {
            manager.currentExaminationProperty().set(exam);
            ViewManager.getInstance().changeView(View.EXAM);
        }
    }

    @Override
    public GridPane getRoot() {
        return root;
    }
}
