package com.example.mentis.presentation;

import com.example.mentis.business.data.Examination;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ExamListCellController implements Controller {

    @FXML
    private GridPane root;

    public void setExamination(Examination exam) {

    }

    @Override
    public GridPane getRoot() {
        return root;
    }
}
