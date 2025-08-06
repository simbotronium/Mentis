package com.example.mentis.presentation;

import com.example.mentis.business.data.Examination;
import javafx.fxml.FXML;
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

    private final Color red = new Color(1.0, 0.0, 0.0, 1.0);
    private final Color green = new Color(0.0, 1.0, 0.0, 1.0);

    public void setExamination(Examination exam) {
        examLabel.setText(exam.getExam().toUpperCase());
        sliceLabel.setText(Integer.toString(exam.getSlice()));
        valid.setFill(exam.isValid() ? green : red);
        mapped.setFill(exam.isMapped() ? green : red);
    }

    @Override
    public GridPane getRoot() {
        return root;
    }
}
